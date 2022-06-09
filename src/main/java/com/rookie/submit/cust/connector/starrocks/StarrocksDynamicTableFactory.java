package com.rookie.submit.cust.connector.starrocks;

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

public class StarrocksDynamicTableFactory implements DynamicTableSourceFactory {

    @Override
    public String factoryIdentifier() {
        // used for matching to `connector = '...'`
        return "cust-starrocks";
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        options.add(StarrocksOption.URL);
        options.add(StarrocksOption.USERNAME);
        options.add(StarrocksOption.PASSWORD);
        options.add(StarrocksOption.SQL);
//        options.add(FactoryUtil.FORMAT); // use pre-defined option for format

        return options;
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        // no optional option
        options.add(StarrocksOption.CACHE_MAX_SIZE);
        options.add(StarrocksOption.CACHE_EXPIRE_MS);
        options.add(StarrocksOption.MAX_RETRY_TIMES);
        options.add(StarrocksOption.TIME_OUT);
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
        StarrocksOption option = new StarrocksOption.Builder()
                .setUrl(config.get(StarrocksOption.URL))
                .setSql(config.get(StarrocksOption.SQL))
                .setUsername(config.get(StarrocksOption.USERNAME))
                .setPassword(config.get(StarrocksOption.PASSWORD))
                .setCacheMaxSize(config.get(StarrocksOption.CACHE_MAX_SIZE))
                .setCacheExpireMs(config.get(StarrocksOption.CACHE_EXPIRE_MS))
                .setMaxRetryTimes(config.get(StarrocksOption.MAX_RETRY_TIMES))
                .setTimeOut(config.get(StarrocksOption.TIME_OUT))
                .build();

        // derive the produced data type (excluding computed columns) from the catalog table
        final DataType producedDataType =
                context.getCatalogTable().getResolvedSchema().toPhysicalRowDataType();

        TableSchema physicalSchema =
                TableSchemaUtils.getPhysicalSchema(context.getCatalogTable().getSchema());

        // create and return dynamic table source
        return new StarrocksDynamicTableSource(producedDataType, option, physicalSchema);
    }
}