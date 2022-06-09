package com.rookie.submit.cust.connector.starrocks;

import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.source.*;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.RowType;

import static org.apache.flink.table.types.utils.TypeConversions.fromDataTypeToLegacyInfo;

public class StarrocksDynamicTableSource implements ScanTableSource, LookupTableSource {


    private final DataType producedDataType;
    private final StarrocksOption options;
    private final TableSchema physicalSchema;

    public StarrocksDynamicTableSource(

            DataType producedDataType,
            StarrocksOption options,
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

        final SourceFunction<RowData> sourceFunction
                = new StarrocksSource(producedDataType, options);

        return SourceFunctionProvider.of(sourceFunction, false);
    }

    @Override
    public DynamicTableSource copy() {
        return new StarrocksDynamicTableSource(producedDataType, options, physicalSchema);
    }

    @Override
    public String asSummaryString() {
        return "Mysql Table Source, support Lookup function";
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
        final RowType rowType = (RowType) physicalSchema.toRowDataType().getLogicalType();
        // new MysqlRowDataLookUpFunction
        StarrocksRowDataLookUpFunction lookUpFunction
                = new StarrocksRowDataLookUpFunction(fieldNames, keyNames, producedDataType, options, rowType);

        // return MysqlRowDataLookUpFunction
        return TableFunctionProvider.of(lookUpFunction);
    }
}