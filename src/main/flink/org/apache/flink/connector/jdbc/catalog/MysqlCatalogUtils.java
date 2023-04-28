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

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.types.DataType;
import org.apache.hadoop.hive.ql.io.StorageFormatFactory;

import static org.apache.flink.util.Preconditions.checkNotNull;

/**
 * Utils for {@link JdbcCatalog}.
 */
public class MysqlCatalogUtils {


    public static final ConfigOption<String> CONNECTOR =
            ConfigOptions.key("connector")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("The JDBC database URL.");


    /**
     * transfer string type to flink Data type
     * @param type
     * @return
     */
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
