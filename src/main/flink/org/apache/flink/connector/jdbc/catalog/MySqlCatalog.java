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

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.annotation.Internal;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.jdbc.dialect.JdbcDialectTypeMapper;
import org.apache.flink.connector.jdbc.dialect.mysql.MySqlTypeMapper;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.catalog.*;
import org.apache.flink.table.catalog.exceptions.CatalogException;
import org.apache.flink.table.catalog.exceptions.DatabaseNotExistException;
import org.apache.flink.table.catalog.exceptions.TableAlreadyExistException;
import org.apache.flink.table.catalog.exceptions.TableNotExistException;
import org.apache.flink.table.catalog.hive.util.HiveTableUtil;
import org.apache.flink.table.catalog.hive.util.HiveTypeUtil;
import org.apache.flink.table.catalog.stats.CatalogTableStatistics;
import org.apache.flink.table.types.DataType;
import org.apache.flink.util.Preconditions;
import org.apache.flink.util.TemporaryClassLoaderContext;
import org.apache.hadoop.hive.common.StatsSetupConst;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.flink.connector.jdbc.table.JdbcConnectorOptions.*;
import static org.apache.flink.connector.jdbc.table.JdbcConnectorOptions.TABLE_NAME;
import static org.apache.flink.connector.jdbc.table.JdbcDynamicTableFactory.IDENTIFIER;
import static org.apache.flink.table.factories.FactoryUtil.CONNECTOR;
import static org.apache.flink.util.Preconditions.checkNotNull;

/**
 * Catalog for MySQL.
 */
@Internal
public class MySqlCatalog extends AbstractJdbcCatalog {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlCatalog.class);

    private final JdbcDialectTypeMapper dialectTypeMapper;

    private static final int DEFAULT_UNKNOWN_STATS_VALUE = -1;

    private static final Set<String> builtinDatabases =
            new HashSet<String>() {
                {
                    add("information_schema");
                    add("mysql");
                    add("performance_schema");
                    add("sys");
                }
            };

    public MySqlCatalog(
            ClassLoader userClassLoader,
            String catalogName,
            String defaultDatabase,
            String username,
            String pwd,
            String baseUrl) {
        super(userClassLoader, catalogName, defaultDatabase, username, pwd, baseUrl);

        String driverVersion =
                Preconditions.checkNotNull(getDriverVersion(), "Driver version must not be null.");
        String databaseVersion =
                Preconditions.checkNotNull(
                        getDatabaseVersion(), "Database version must not be null.");
        LOG.info("Driver version: {}, database version: {}", driverVersion, databaseVersion);
        this.dialectTypeMapper = new MySqlTypeMapper(databaseVersion, driverVersion);
    }

    @Override
    public List<String> listDatabases() throws CatalogException {
        return extractColumnValuesBySQL(
                defaultUrl,
                "SELECT `SCHEMA_NAME` FROM `INFORMATION_SCHEMA`.`SCHEMATA`;",
                1,
                dbName -> !builtinDatabases.contains(dbName));
    }

    // ------ tables ------

    @Override
    public List<String> listTables(String databaseName)
            throws DatabaseNotExistException, CatalogException {
        Preconditions.checkState(
                StringUtils.isNotBlank(databaseName), "Database name must not be blank.");
        // not need
//        if (!databaseExists(databaseName)) {
//            throw new DatabaseNotExistException(getName(), databaseName);
//        }
        //
        List<String> tableList = extractColumnValuesBySQL(
                baseUrl + databaseName,
                "select tbl_name from tbls",
                1,
                null,
                null);

        return tableList;
    }

    @Override
    public boolean tableExists(ObjectPath tablePath) throws CatalogException {
        return !extractColumnValuesBySQL(
                baseUrl + tablePath.getDatabaseName(),
                "SELECT tbl_name FROM tbls "
                        + "WHERE tbl_name=?",
                1,
                null,
                tablePath.getObjectName())
                .isEmpty();
    }

    private String getDatabaseVersion() {
        try (TemporaryClassLoaderContext ignored =
                     TemporaryClassLoaderContext.of(userClassLoader)) {
            try (Connection conn = DriverManager.getConnection(defaultUrl, username, pwd)) {
                return conn.getMetaData().getDatabaseProductVersion();
            } catch (Exception e) {
                throw new CatalogException(
                        String.format("Failed in getting MySQL version by %s.", defaultUrl), e);
            }
        }
    }

    private String getDriverVersion() {
        try (TemporaryClassLoaderContext ignored =
                     TemporaryClassLoaderContext.of(userClassLoader)) {
            try (Connection conn = DriverManager.getConnection(defaultUrl, username, pwd)) {
                String driverVersion = conn.getMetaData().getDriverVersion();
                Pattern regexp = Pattern.compile("\\d+?\\.\\d+?\\.\\d+");
                Matcher matcher = regexp.matcher(driverVersion);
                return matcher.find() ? matcher.group(0) : null;
            } catch (Exception e) {
                throw new CatalogException(
                        String.format("Failed in getting MySQL driver version by %s.", defaultUrl),
                        e);
            }
        }
    }

    /**
     * Converts MySQL type to Flink {@link DataType}.
     */
    @Override
    protected DataType fromJDBCType(ObjectPath tablePath, ResultSetMetaData metadata, int colIndex)
            throws SQLException {
        return dialectTypeMapper.mapping(tablePath, metadata, colIndex);
    }

    @Override
    protected String getTableName(ObjectPath tablePath) {
        return tablePath.getObjectName();
    }

    @Override
    protected String getSchemaName(ObjectPath tablePath) {
        return tablePath.getDatabaseName();
    }

    @Override
    protected String getSchemaTableName(ObjectPath tablePath) {
        return tablePath.getObjectName();
    }

    @Override
    public void createTable(ObjectPath tablePath, CatalogBaseTable table, boolean ignoreIfExists)
            throws TableAlreadyExistException, DatabaseNotExistException, CatalogException {
        LOG.debug("create table in mysql catalog");

        checkNotNull(tablePath, "tablePath cannot be null");
        checkNotNull(table, "table cannot be null");

        String databaseName = tablePath.getDatabaseName();
        String dbUrl = baseUrl + databaseName;

        if (!this.databaseExists(tablePath.getDatabaseName())) {
            throw new DatabaseNotExistException(this.getName(), tablePath.getDatabaseName());
        } else {
            boolean managedTable = ManagedTableListener.isManagedTable(this, table);
            Table mysqlTable = MysqlCatalogUtils.instantiateHiveTable(tablePath, table, null, managedTable);

            try (Connection conn = DriverManager.getConnection(dbUrl, username, pwd)) {

                // insert table
                PreparedStatement ps = conn.prepareStatement("insert into TBLS(TBL_NAME, CREATE_TIME) values(?, NOW())");
                ps.setString(1, mysqlTable.getTableName());
                boolean bool = ps.execute();
                if (!bool) {
                    throw new CatalogException(
                            String.format("Failed create table %s", tablePath.getFullName()));
                }

                // select table id
                ps = conn.prepareStatement("select id from TBLS where TBL_NAME = ?");
                ps.setString(1, mysqlTable.getTableName());
                ResultSet resultSet = ps.executeQuery();
                int id = -1;
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
                if (id == -1) {
                    throw new CatalogException(
                            String.format("Find table %s id error", tablePath.getFullName()));
                }

                // insert TABLE_PARAMS
                ps = conn.prepareStatement("insert into COL(TBL_id, PARAM_KEY, PARAM_VALUE, CREATE_TIME) values(?,?,?, now())");

                for (Map.Entry<String, String> entry : table.getOptions().entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    ps.setInt(1, id);
                    ps.setString(2, key);
                    ps.setString(3, value);
                    ps.addBatch();
                }
                // todo check insert stable
                ps.executeBatch();


            } catch (SQLException e) {
                //todo
                throw new CatalogException(
                        String.format("Failed create table %s", tablePath.getFullName()), e);
            }


        }


    }

    @Override
    public CatalogBaseTable getTable(ObjectPath tablePath) throws TableNotExistException, CatalogException {
        // check table exists
        if (!tableExists(tablePath)) {
            throw new TableNotExistException(getName(), tablePath);
        }

        try (Connection conn = DriverManager.getConnection(baseUrl + tablePath.getDatabaseName(), username, pwd)) {
//            DatabaseMetaData metaData = conn.getMetaData();
//            Optional<UniqueConstraint> primaryKey =
//                    getPrimaryKey(
//                            metaData,
//                            tablePath.getDatabaseName(),
//                            getSchemaName(tablePath),
//                            getTableName(tablePath));

            PreparedStatement ps =
                    conn.prepareStatement(
                            String.format("select PARAM_KEY, PARAM_VALUE from col where tbl_id in (select id from tbls where TBL_NAME = ?);", getSchemaTableName(tablePath)));
            ps.setString(1, tablePath.getObjectName());

            ResultSet resultSet = ps.executeQuery();


            // for column
            Map<String, String> colMap = new HashMap<>();
            // for properties
            Map<String, String> props = new HashMap<>();
            List<Col> propList = new ArrayList<>();
            while (resultSet.next()) {
                String key = resultSet.getString(1);
                String value = resultSet.getString(2);
                Col col = new Col(key, value);

                if (key.startsWith("flink.schema")) {
                    colMap.put(key, value);
                } else {
                    props.put(key.substring(6), value);

                }
            }
            /////////////// remove key
            String primaryKeyColumns = props.remove("flink.schema.primary-key.columns");
            String primaryKeyName = props.remove("flink.schema.primary-key.name");

            /////// find column size
            int columnSize = -1;
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            for (String key : colMap.keySet()) {
                Matcher m = p.matcher(key);
                String num = m.replaceAll("").trim();
                if (num.length() > 0) {
                    columnSize = Integer.parseInt(num) > columnSize ? Integer.parseInt(num) : columnSize;
                }
            }
            ++columnSize;

            ///////////////
            String[] colNames = new String[columnSize];
            DataType[] colTypes = new DataType[columnSize];
            for (int i = 0; i < columnSize; i++) {
                String name = colMap.get("flink.schema." + i + ".name");
                String dateType = colMap.get("flink.schema." + i + ".data-type");

                if (name == null) {
                    break;
                }

                colNames[i] = (name);
                colTypes[i] = MysqlCatalogUtils.toFlinkType(dateType);
            }
            TableSchema.Builder builder = TableSchema.builder().fields(colNames, colTypes);
            // todo
//            if (primaryKey != null) {
//                builder.primaryKey(
//                        primaryKey.getName(), primaryKey.getColumns().toArray(new String[0]));
//            }
            TableSchema tableSchema = builder.build();
            String comment = props.remove("comment");
            props.remove("ent_lastDdlTime");


            return new CatalogTableImpl(tableSchema, new ArrayList<>(), props, null);
//            return CatalogTable.of(tableSchema, null, Lists.newArrayList(), props);
        } catch (Exception e) {
            throw new CatalogException(
                    String.format("Failed getting table %s", tablePath.getFullName()), e);
        }
    }

    @Override
    public CatalogTableStatistics getTableStatistics(ObjectPath tablePath) throws TableNotExistException, CatalogException {

        CatalogBaseTable table = getTable(tablePath);
        Map parameters = table.getOptions();

        return new CatalogTableStatistics(
                parsePositiveLongStat(parameters, StatsSetupConst.ROW_COUNT),
                parsePositiveIntStat(parameters, StatsSetupConst.NUM_FILES),
                parsePositiveLongStat(parameters, StatsSetupConst.TOTAL_SIZE),
                parsePositiveLongStat(parameters, StatsSetupConst.RAW_DATA_SIZE));

    }

    public static int parsePositiveIntStat(Map<String, String> parameters, String key) {
        String value = parameters.get(key);
        if (value == null) {
            return DEFAULT_UNKNOWN_STATS_VALUE;
        } else {
            int v = Integer.parseInt(value);
            return v > 0 ? v : DEFAULT_UNKNOWN_STATS_VALUE;
        }
    }

    public static long parsePositiveLongStat(Map<String, String> parameters, String key) {
        String value = parameters.get(key);
        if (value == null) {
            return DEFAULT_UNKNOWN_STATS_VALUE;
        } else {
            long v = Long.parseLong(value);
            return v > 0 ? v : DEFAULT_UNKNOWN_STATS_VALUE;
        }
    }
}
