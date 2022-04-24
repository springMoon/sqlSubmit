package com.rookie.submit.cust.connector.redis;

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

public class RedisDynamicTableFactory implements DynamicTableSourceFactory {

    @Override
    public String factoryIdentifier() {
        // used for matching to `connector = '...'`
        return "cust-mysql";
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        options.add(RedisOption.REDIS_URL);
        options.add(RedisOption.TYPE);
//        options.add(FactoryUtil.FORMAT); // use pre-defined option for format

        return options;
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        // no optional option
        options.add(RedisOption.PASSWORD);
        options.add(RedisOption.CACHE_MAX_SIZE);
        options.add(RedisOption.CACHE_EXPIRE_MS);
        options.add(RedisOption.MAX_RETRY_TIMES);
        options.add(RedisOption.TIME_OUT);
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
        RedisOption.Builder builder = new RedisOption.Builder()
                .setUrl(config.get(RedisOption.REDIS_URL))
                .setType(config.get(RedisOption.TYPE))
                .setPassword(config.get(RedisOption.PASSWORD))
                .setCacheMaxSize(config.get(RedisOption.CACHE_MAX_SIZE))
                .setCacheExpireMs(config.get(RedisOption.CACHE_EXPIRE_MS))
                .setMaxRetryTimes(config.get(RedisOption.MAX_RETRY_TIMES))
                .setTimeOut(config.get(RedisOption.TIME_OUT));


        RedisOption option = builder.build();

        // derive the produced data type (excluding computed columns) from the catalog table
        final DataType producedDataType =
                context.getCatalogTable().getResolvedSchema().toPhysicalRowDataType();

        TableSchema physicalSchema =
                TableSchemaUtils.getPhysicalSchema(context.getCatalogTable().getSchema());

        // create and return dynamic table source
        return new RedisDynamicTableSource(producedDataType, option, physicalSchema);
    }
}