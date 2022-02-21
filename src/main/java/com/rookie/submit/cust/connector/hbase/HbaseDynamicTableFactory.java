package com.rookie.submit.cust.connector.hbase;

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

public class HbaseDynamicTableFactory implements DynamicTableSourceFactory {

    @Override
    // connector 标识
    public String factoryIdentifier() {
        // used for matching to `connector = '...'`
        return "cust-hbase";
    }

    @Override
    // 必填参数
    public Set<ConfigOption<?>> requiredOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        options.add(HbaseOption.ZOOKEEPER_QUORUM);
        options.add(HbaseOption.ZOOKEEPER_ZNODE_PARENT);
        options.add(HbaseOption.NULL_STRING_LITERAL);
        options.add(HbaseOption.TABLE);
        options.add(HbaseOption.LOOKUP_KEY);
//        options.add(FactoryUtil.FORMAT); // use pre-defined option for format

        return options;
    }

    @Override
    // 选填参数
    public Set<ConfigOption<?>> optionalOptions() {
        final Set<ConfigOption<?>> options = new HashSet<>();
        // no optional option
        options.add(HbaseOption.CACHE_MAX_SIZE);
        options.add(HbaseOption.CACHE_EXPIRE_MS);
        options.add(HbaseOption.MAX_RETRY_TIMES);
        options.add(HbaseOption.TIME_OUT);
        return options;
    }

    @Override
    // 从执行上下文获取参数, 创建 HbaseDynamicTableSource
    public DynamicTableSource createDynamicTableSource(Context context) {
        // either implement your custom validation logic here ...
        // or use the provided helper utility
        final FactoryUtil.TableFactoryHelper helper = FactoryUtil.createTableFactoryHelper(this, context);

        // validate all options
        helper.validate();

        // get the validated options
        final ReadableConfig config = helper.getOptions();
        HbaseOption option = new HbaseOption.Builder()
                .setZookeeperQuorum(config.get(HbaseOption.ZOOKEEPER_QUORUM))
                .setZookeeperZnodeParent(config.get(HbaseOption.ZOOKEEPER_ZNODE_PARENT))
                .setNullStringLiteral(config.get(HbaseOption.NULL_STRING_LITERAL))
                .setTable(config.get(HbaseOption.TABLE))
                .setLookupKey(config.get(HbaseOption.LOOKUP_KEY))
                .setCacheMaxSize(config.get(HbaseOption.CACHE_MAX_SIZE))
                .setCacheExpireMs(config.get(HbaseOption.CACHE_EXPIRE_MS))
                .setMaxRetryTimes(config.get(HbaseOption.MAX_RETRY_TIMES))
                .setTimeOut(config.get(HbaseOption.TIME_OUT))
                .build();

        // derive the produced data type (excluding computed columns) from the catalog table
        final DataType producedDataType =
                context.getCatalogTable().getResolvedSchema().toPhysicalRowDataType();

        TableSchema physicalSchema =
                TableSchemaUtils.getPhysicalSchema(context.getCatalogTable().getSchema());

        // create and return dynamic table source
        return new HbaseDynamicTableSource(producedDataType, option, physicalSchema);
    }
}