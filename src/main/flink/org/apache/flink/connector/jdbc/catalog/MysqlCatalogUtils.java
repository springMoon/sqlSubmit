/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.connector.jdbc.catalog;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.jdbc.dialect.JdbcDialect;
import org.apache.flink.connector.jdbc.dialect.JdbcDialectLoader;
import org.apache.flink.connector.jdbc.dialect.mysql.MySqlDialect;
import org.apache.flink.connector.jdbc.dialect.psql.PostgresDialect;
import org.apache.flink.sql.parser.hive.ddl.SqlCreateHiveTable;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.catalog.CatalogBaseTable;
import org.apache.flink.table.catalog.CatalogTable;
import org.apache.flink.table.catalog.CatalogView;
import org.apache.flink.table.catalog.ObjectPath;
import org.apache.flink.table.catalog.hive.HiveCatalog;
import org.apache.flink.table.catalog.hive.HiveCatalogConfig;
import org.apache.flink.table.catalog.hive.util.HiveTableUtil;
import org.apache.flink.table.descriptors.DescriptorProperties;
import org.apache.flink.table.descriptors.Schema;
import org.apache.flink.table.factories.ManagedTableFactory;
import org.apache.flink.table.types.DataType;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.TableType;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.StorageDescriptor;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.ql.io.RCFileStorageFormatDescriptor;
import org.apache.hadoop.hive.ql.io.StorageFormatDescriptor;
import org.apache.hadoop.hive.ql.io.StorageFormatFactory;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.apache.hadoop.hive.serde2.typeinfo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.flink.sql.parser.hive.ddl.SqlCreateHiveTable.HiveTableRowFormat.SERDE_INFO_PROP_PREFIX;
import static org.apache.flink.sql.parser.hive.ddl.SqlCreateHiveTable.HiveTableRowFormat.SERDE_LIB_CLASS_NAME;
import static org.apache.flink.sql.parser.hive.ddl.SqlCreateHiveTable.HiveTableStoredAs.*;
import static org.apache.flink.sql.parser.hive.ddl.SqlCreateHiveTable.TABLE_IS_EXTERNAL;
import static org.apache.flink.sql.parser.hive.ddl.SqlCreateHiveTable.TABLE_LOCATION_URI;
import static org.apache.flink.table.catalog.CatalogPropertiesUtil.FLINK_PROPERTY_PREFIX;
import static org.apache.flink.table.catalog.CatalogPropertiesUtil.IS_GENERIC;
import static org.apache.flink.table.descriptors.ConnectorDescriptorValidator.CONNECTOR_TYPE;
import static org.apache.flink.table.factories.FactoryUtil.CONNECTOR;
import static org.apache.flink.util.Preconditions.checkArgument;
import static org.apache.flink.util.Preconditions.checkNotNull;

/**
 * Utils for {@link JdbcCatalog}.
 */
public class MysqlCatalogUtils {

    private static final StorageFormatFactory storageFormatFactory = new StorageFormatFactory();

    /**
     * URL has to be without database, like "jdbc:postgresql://localhost:5432/" or
     * "jdbc:postgresql://localhost:5432" rather than "jdbc:postgresql://localhost:5432/db".
     */
    public static void validateJdbcUrl(String url) {
        String[] parts = url.trim().split("\\/+");

        checkArgument(parts.length == 2);
    }

    /**
     * Create catalog instance from given information.
     */
    public static AbstractJdbcCatalog createCatalog(
            ClassLoader userClassLoader,
            String catalogName,
            String defaultDatabase,
            String username,
            String pwd,
            String baseUrl) {
        JdbcDialect dialect = JdbcDialectLoader.load(baseUrl, userClassLoader);

        if (dialect instanceof PostgresDialect) {
            return new PostgresCatalog(
                    userClassLoader, catalogName, defaultDatabase, username, pwd, baseUrl);
        } else if (dialect instanceof MySqlDialect) {
            return new MySqlCatalog(
                    userClassLoader, catalogName, defaultDatabase, username, pwd, baseUrl);
        } else {
            throw new UnsupportedOperationException(
                    String.format("Catalog for '%s' is not supported yet.", dialect));
        }
    }

    public static Table instantiateHiveTable(
            ObjectPath tablePath, CatalogBaseTable table, HiveConf hiveConf, boolean managedTable) {
        final boolean isView = table instanceof CatalogView;
        // let Hive set default parameters for us, e.g. serialization.format
        Table hiveTable =
                org.apache.hadoop.hive.ql.metadata.Table.getEmptyTable(
                        tablePath.getDatabaseName(), tablePath.getObjectName());
        hiveTable.setCreateTime((int) (System.currentTimeMillis() / 1000));

        Map<String, String> properties = new HashMap<>(table.getOptions());
        if (managedTable) {
            properties.put(CONNECTOR.key(), ManagedTableFactory.DEFAULT_IDENTIFIER);
        }
        // Table comment
        if (table.getComment() != null) {
            properties.put(HiveCatalogConfig.COMMENT, table.getComment());
        }

        boolean isHiveTable = HiveCatalog.isHiveTable(properties);

        // Hive table's StorageDescriptor
        StorageDescriptor sd = hiveTable.getSd();
        HiveTableUtil.setDefaultStorageFormat(sd, hiveConf);

        // We always store schema as properties for view, because view schema may not be mapped to
        // hive schema. This also means views created by flink cannot be used in hive, which is fine
        // because hive cannot understand the expanded query anyway
        if (isHiveTable && !isView) {
            initiateTableFromProperties(hiveTable, properties, hiveConf);
            List<FieldSchema> allColumns = HiveTableUtil.createHiveColumns(table.getSchema());
            // Table columns and partition keys
            if (table instanceof CatalogTable) {
                CatalogTable catalogTable = (CatalogTable) table;

                if (catalogTable.isPartitioned()) {
                    int partitionKeySize = catalogTable.getPartitionKeys().size();
                    List<FieldSchema> regularColumns =
                            allColumns.subList(0, allColumns.size() - partitionKeySize);
                    List<FieldSchema> partitionColumns =
                            allColumns.subList(
                                    allColumns.size() - partitionKeySize, allColumns.size());

                    sd.setCols(regularColumns);
                    hiveTable.setPartitionKeys(partitionColumns);
                } else {
                    sd.setCols(allColumns);
                    hiveTable.setPartitionKeys(new ArrayList<>());
                }
            } else {
                sd.setCols(allColumns);
            }
            // Table properties
            hiveTable.getParameters().putAll(properties);
        } else {
            DescriptorProperties tableSchemaProps = new DescriptorProperties(true);
            tableSchemaProps.putTableSchema(Schema.SCHEMA, table.getSchema());

            if (table instanceof CatalogTable) {
                tableSchemaProps.putPartitionKeys(((CatalogTable) table).getPartitionKeys());
            }

            properties.putAll(tableSchemaProps.asMap());
            properties = maskFlinkProperties(properties);
            // we may need to explicitly set is_generic flag in the following cases:
            // 1. user doesn't specify 'connector' or 'connector.type' when creating a table, w/o
            // 'is_generic', such a table will be considered as a hive table upon retrieval
            // 2. when creating views which don't have connector properties
            if (isView
                    || (!properties.containsKey(FLINK_PROPERTY_PREFIX + CONNECTOR.key())
                    && !properties.containsKey(FLINK_PROPERTY_PREFIX + CONNECTOR_TYPE))) {
                properties.put(IS_GENERIC, "true");
            }
            hiveTable.setParameters(properties);
        }

        if (isView) {
            // TODO: [FLINK-12398] Support partitioned view in catalog API
            hiveTable.setPartitionKeys(new ArrayList<>());

            CatalogView view = (CatalogView) table;
            hiveTable.setViewOriginalText(view.getOriginalQuery());
            hiveTable.setViewExpandedText(view.getExpandedQuery());
            hiveTable.setTableType(TableType.VIRTUAL_VIEW.name());
        }

        return hiveTable;
    }

    private static Map<String, String> maskFlinkProperties(Map<String, String> properties) {
        return properties.entrySet().stream()
                .filter(e -> e.getKey() != null && e.getValue() != null)
                .map(e -> new Tuple2<>(FLINK_PROPERTY_PREFIX + e.getKey(), e.getValue()))
                .collect(Collectors.toMap(t -> t.f0, t -> t.f1));
    }

    private static void initiateTableFromProperties(
            Table hiveTable, Map<String, String> properties, HiveConf hiveConf) {
        extractExternal(hiveTable, properties);
        extractRowFormat(hiveTable.getSd(), properties);
        extractStoredAs(hiveTable.getSd(), properties, hiveConf);
        extractLocation(hiveTable.getSd(), properties);
    }

    private static void extractExternal(Table hiveTable, Map<String, String> properties) {
        boolean external = Boolean.parseBoolean(properties.remove(TABLE_IS_EXTERNAL));
        if (external) {
            hiveTable.setTableType(TableType.EXTERNAL_TABLE.toString());
            // follow Hive to set this property
            hiveTable.getParameters().put("EXTERNAL", "TRUE");
        }
    }

    public static void extractLocation(StorageDescriptor sd, Map<String, String> properties) {
        String location = properties.remove(TABLE_LOCATION_URI);
        if (location != null) {
            sd.setLocation(location);
        }
    }

    public static void extractRowFormat(StorageDescriptor sd, Map<String, String> properties) {
        String serdeLib = properties.remove(SERDE_LIB_CLASS_NAME);
        if (serdeLib != null) {
            sd.getSerdeInfo().setSerializationLib(serdeLib);
        }
        List<String> serdeProps =
                properties.keySet().stream()
                        .filter(p -> p.startsWith(SERDE_INFO_PROP_PREFIX))
                        .collect(Collectors.toList());
        for (String prop : serdeProps) {
            String value = properties.remove(prop);
            // there was a typo of this property in hive, and was fixed in 3.0.0 --
            // https://issues.apache.org/jira/browse/HIVE-16922
            String key =
                    prop.equals(SqlCreateHiveTable.HiveTableRowFormat.COLLECTION_DELIM)
                            ? serdeConstants.COLLECTION_DELIM
                            : prop.substring(SERDE_INFO_PROP_PREFIX.length());
            sd.getSerdeInfo().getParameters().put(key, value);
            // make sure FIELD_DELIM and SERIALIZATION_FORMAT are consistent
            if (key.equals(serdeConstants.FIELD_DELIM)) {
                sd.getSerdeInfo().getParameters().put(serdeConstants.SERIALIZATION_FORMAT, value);
            }
        }
    }

    public static void extractStoredAs(
            StorageDescriptor sd, Map<String, String> properties, HiveConf hiveConf) {
        String storageFormat = properties.remove(STORED_AS_FILE_FORMAT);
        String inputFormat = properties.remove(STORED_AS_INPUT_FORMAT);
        String outputFormat = properties.remove(STORED_AS_OUTPUT_FORMAT);
        if (storageFormat == null && inputFormat == null) {
            return;
        }
        if (storageFormat != null) {
            setStorageFormat(sd, storageFormat, hiveConf);
        } else {
            sd.setInputFormat(inputFormat);
            sd.setOutputFormat(outputFormat);
        }
    }

    public static void setStorageFormat(StorageDescriptor sd, String format, HiveConf hiveConf) {
        StorageFormatDescriptor storageFormatDescriptor = storageFormatFactory.get(format);
        checkArgument(storageFormatDescriptor != null, "Unknown storage format " + format);
        sd.setInputFormat(storageFormatDescriptor.getInputFormat());
        sd.setOutputFormat(storageFormatDescriptor.getOutputFormat());
        String serdeLib = storageFormatDescriptor.getSerde();
        if (serdeLib == null && storageFormatDescriptor instanceof RCFileStorageFormatDescriptor) {
            serdeLib = hiveConf.getVar(HiveConf.ConfVars.HIVEDEFAULTRCFILESERDE);
        }
        if (serdeLib != null) {
            sd.getSerdeInfo().setSerializationLib(serdeLib);
        }
    }


    public static DataType toFlinkType(String type) {
        checkNotNull(type, "type cannot be null");
        String prex = type.split("\\(")[0];

        int star = type.indexOf("(") + 1;
        int end = type.indexOf(")");
        int quote = type.indexOf(",") + 1;
        switch (prex) {
            case "CHAR":
                return DataTypes.CHAR(Integer.parseInt(type.substring(star, end)));
            case "VARCHAR":
                return DataTypes.VARCHAR(Integer.parseInt(type.substring(star, end)));
            case "STRING":
                return DataTypes.STRING();
            case "BOOLEAN":
                return DataTypes.BOOLEAN();
            case "BYTE":
                return DataTypes.TINYINT();
            case "SHORT":
                return DataTypes.SMALLINT();
            case "INT":
                return DataTypes.INT();
            case "LONG":
                return DataTypes.BIGINT();
            case "FLOAT":
                return DataTypes.FLOAT();
            case "DOUBLE":
                return DataTypes.DOUBLE();
            case "DATE":
                return DataTypes.DATE();
            case "TIMESTAMP":
                return DataTypes.TIMESTAMP(9);
            case "BINARY":
                return DataTypes.BYTES();
            case "DECIMAL":
                int precision = Integer.parseInt(type.substring(star, quote));
                int scale = Integer.parseInt(type.substring(quote, end));
                return DataTypes.DECIMAL(precision, scale);
            case "INTERVAL_YEAR_MONTH":
                return DataTypes.INTERVAL(DataTypes.MONTH());
            case "INTERVAL_DAY_TIME":
                return DataTypes.INTERVAL(DataTypes.SECOND(3));
            default:
                throw new UnsupportedOperationException(
                        String.format(
                                "Flink doesn't support type %s yet", type));
        }
    }
}
