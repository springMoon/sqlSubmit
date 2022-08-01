package com.rookie.submit.cust.connector.mysql;

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
        // used for matching to `connector = '...'`
        return "cust-mysql";
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        options.add(MysqlOption.URL);
        options.add(MysqlOption.USERNAME);
        options.add(MysqlOption.PASSWORD);
        options.add(MysqlOption.DATABASE);
        options.add(MysqlOption.TABLE);
//        options.add(FactoryUtil.FORMAT); // use pre-defined option for format

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
        options.add(MysqlOption.KEY);
        options.add(MysqlOption.BATCH_SIZE);
        return options;
    }

    @Override
    public DynamicTableSource createDynamicTableSource(Context context) {
        // either implement your custom validation logic here ...
        // or use the provided helper utility
        final FactoryUtil.TableFactoryHelper helper = FactoryUtil.createTableFactoryHelper(this, context);

        // validate all options
        helper.validate();

        // get the validated options
        final ReadableConfig config = helper.getOptions();
        MysqlOption option = new MysqlOption.Builder()
                .setUrl(config.get(MysqlOption.URL))
                .setDatabase(config.get(MysqlOption.DATABASE))
                .setTable(config.get(MysqlOption.TABLE))
                .setKey(config.get(MysqlOption.KEY))
                .setUsername(config.get(MysqlOption.USERNAME))
                .setPassword(config.get(MysqlOption.PASSWORD))
                .setCacheMaxSize(config.get(MysqlOption.CACHE_MAX_SIZE))
                .setCacheExpireMs(config.get(MysqlOption.CACHE_EXPIRE_MS))
                .setMaxRetryTimes(config.get(MysqlOption.MAX_RETRY_TIMES))
                .setTimeOut(config.get(MysqlOption.TIME_OUT))
                .setBatchSize(config.get(MysqlOption.BATCH_SIZE))
                .build();

        // derive the produced data type (excluding computed columns) from the catalog table
        final DataType producedDataType =
                context.getCatalogTable().getResolvedSchema().toPhysicalRowDataType();

        TableSchema physicalSchema =
                TableSchemaUtils.getPhysicalSchema(context.getCatalogTable().getSchema());

        // create and return dynamic table source
        return new MysqlDynamicTableSource(producedDataType, option, physicalSchema);
    }
}