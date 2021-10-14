package com.rookie.submit.cust.source.mysql;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.factories.DynamicTableSourceFactory;
import org.apache.flink.table.factories.FactoryUtil;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.utils.TableSchemaUtils;

import java.util.HashSet;
import java.util.Set;

public class MysqlDynamicTableFactory implements DynamicTableSourceFactory {


    @Override
    public String factoryIdentifier() {
        return "cust-mysql"; // used for matching to `connector = '...'`
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        options.add(MysqlOption.URL);
        options.add(MysqlOption.USERNAME);
        options.add(MysqlOption.PASSWORD);
        options.add(MysqlOption.DATABASE);
        options.add(MysqlOption.TABLE);
        options.add(FactoryUtil.FORMAT); // use pre-defined option for format

        return options;
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        // no optional option
        options.add(MysqlOption.CACHE_MAX_SIZE);
        options.add(MysqlOption.CACHE_EXPIRE_MS);
        options.add(MysqlOption.MAX_RETRY_TIMES);
        options.add(MysqlOption.TIME_OUT);
        return options;
    }

    @Override
    public DynamicTableSource createDynamicTableSource(Context context) {
        // either implement your custom validation logic here ...
        // or use the provided helper utility
        final FactoryUtil.TableFactoryHelper helper = FactoryUtil.createTableFactoryHelper(this, context);

        // discover a suitable decoding format
//        final DecodingFormat<DeserializationSchema<RowData>> decodingFormat = helper.discoverDecodingFormat(
//                DeserializationFormatFactory.class,
//                FactoryUtil.FORMAT);

        // validate all options
        helper.validate();

        // get the validated options
        final ReadableConfig options = helper.getOptions();
        final String url = options.get(MysqlOption.URL);
        final String username = options.get(MysqlOption.USERNAME);
        final String password = options.get(MysqlOption.PASSWORD);
        final String database = options.get(MysqlOption.DATABASE);
        final String table = options.get(MysqlOption.TABLE);

        // derive the produced data type (excluding computed columns) from the catalog table
        final DataType producedDataType =
                context.getCatalogTable().getResolvedSchema().toPhysicalRowDataType();

        TableSchema physicalSchema =
                TableSchemaUtils.getPhysicalSchema(context.getCatalogTable().getSchema());

        // create and return dynamic table source
        return new MysqlDynamicTableSource(url, username, password, database, table, producedDataType, options, physicalSchema);
    }
}