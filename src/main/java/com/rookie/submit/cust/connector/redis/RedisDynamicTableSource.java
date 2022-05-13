package com.rookie.submit.cust.connector.redis;

import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.source.*;
import org.apache.flink.table.types.DataType;

import static org.apache.flink.table.types.utils.TypeConversions.fromDataTypeToLegacyInfo;

public class RedisDynamicTableSource implements ScanTableSource, LookupTableSource {


    private final DataType producedDataType;
    private final RedisOption options;
    private final TableSchema physicalSchema;

    public RedisDynamicTableSource(

            DataType producedDataType,
            RedisOption options,
            TableSchema physicalSchema) {

        this.producedDataType = producedDataType;
        this.options = options;
        this.physicalSchema = physicalSchema;
    }

    @Override
    public ChangelogMode getChangelogMode() {
        // in our example the format decides about the changelog mode
        // but it could also be the source itself
        return ChangelogMode.insertOnly();
    }

    @Override
    public ScanRuntimeProvider getScanRuntimeProvider(ScanContext runtimeProviderContext) {



        return null;
    }

    @Override
    public DynamicTableSource copy() {
        return new RedisDynamicTableSource(producedDataType, options, physicalSchema);
    }

    @Override
    public String asSummaryString() {
        return "Redis Table Source, support Lookup function";
    }

    @Override
    public LookupRuntimeProvider getLookupRuntimeProvider(LookupContext context) {

        final RowTypeInfo rowTypeInfo = (RowTypeInfo) fromDataTypeToLegacyInfo(producedDataType);

        String[] fieldNames = rowTypeInfo.getFieldNames();

        int[] lookupKeysIndex = context.getKeys()[0];
        int keyCount = lookupKeysIndex.length;
        String[] keyNames = new String[keyCount];
        for (int i = 0; i < keyCount; i++) {
            keyNames[i] = fieldNames[lookupKeysIndex[i]];
        }
        // new RedisRowDataLookUpFunction
        RedisRowDataLookUpFunction lookUpFunction
                = new RedisRowDataLookUpFunction(options);

        // return MysqlRowDataLookUpFunction
        return TableFunctionProvider.of(lookUpFunction);
    }
}