package com.rookie.submit.cust.source.mysql;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.factories.DeserializationFormatFactory;
import org.apache.flink.table.factories.DynamicTableSourceFactory;
import org.apache.flink.table.factories.FactoryUtil;
import org.apache.flink.table.types.DataType;

import java.util.HashSet;
import java.util.Set;

public class MysqlDynamicTableFactory implements DynamicTableSourceFactory {

    public static final ConfigOption<String> URL = ConfigOptions.key("mysql.url")
            .stringType()
            .noDefaultValue();

    public static final ConfigOption<String> USERNAME = ConfigOptions.key("mysql.username")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<String> PASSWORD = ConfigOptions.key("mysql.password")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<String> DATABASE = ConfigOptions.key("mysql.database")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<String> TABLE = ConfigOptions.key("mysql.table")
            .stringType()
            .noDefaultValue();

    @Override
    public String factoryIdentifier() {
        return "cust-mysql"; // used for matching to `connector = '...'`
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        options.add(URL);
        options.add(USERNAME);
        options.add(PASSWORD);
        options.add(DATABASE);
        options.add(TABLE);
        options.add(FactoryUtil.FORMAT); // use pre-defined option for format
        return options;
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        // no optional option
//        options.add(BYTE_DELIMITER);
        return options;
    }

    @Override
    public DynamicTableSource createDynamicTableSource(Context context) {
        // either implement your custom validation logic here ...
        // or use the provided helper utility
        final FactoryUtil.TableFactoryHelper helper = FactoryUtil.createTableFactoryHelper(this, context);

        // discover a suitable decoding format
        final DecodingFormat<DeserializationSchema<RowData>> decodingFormat = helper.discoverDecodingFormat(
                DeserializationFormatFactory.class,
                FactoryUtil.FORMAT);

        // validate all options
        helper.validate();

        // get the validated options
        final ReadableConfig options = helper.getOptions();
        final String url = options.get(URL);
        final String username = options.get(USERNAME);
        final String password = options.get(PASSWORD);
        final String database = options.get(DATABASE);
        final String table = options.get(TABLE);

        // derive the produced data type (excluding computed columns) from the catalog table
        final DataType producedDataType =
                context.getCatalogTable().getResolvedSchema().toPhysicalRowDataType();

        // create and return dynamic table source
        return new MysqlDynamicTableSource(url, username, password, database, table, decodingFormat, producedDataType);
    }
}