package com.rookie.submit.cust.source.mysql;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.source.*;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.RowType;

import static org.apache.flink.table.types.utils.TypeConversions.fromDataTypeToLegacyInfo;

public class MysqlDynamicTableSource implements ScanTableSource, LookupTableSource {

    private final String url;
    private final String username;
    private final String password;
    private final String database;
    private final String table;
    private final DataType producedDataType;
    private final ReadableConfig options;
    private transient MysqlLookupOption lookupOption;
    private final TableSchema physicalSchema;

    public MysqlDynamicTableSource(
            String url,
            String username,
            String password,
            String database,
            String table,
            DataType producedDataType,
            ReadableConfig options,
            TableSchema physicalSchema) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.database = database;
        this.table = table;
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
                = new MysqlSource(url, username, password, database, table, producedDataType, options);

        return SourceFunctionProvider.of(sourceFunction, false);
    }

    @Override
    public DynamicTableSource copy() {
        return new MysqlDynamicTableSource(url, username, password, database, table, producedDataType, options, physicalSchema);
    }

    @Override
    public String asSummaryString() {
        return "Http Table Source";
    }

    @Override
    public LookupRuntimeProvider getLookupRuntimeProvider(LookupContext context) {
        if (lookupOption == null) {
            lookupOption = new MysqlLookupOption.Builder()
                    .setCacheMaxSize(options.get(MysqlOption.CACHE_MAX_SIZE))
                    .setCacheExpireMs(options.get(MysqlOption.CACHE_EXPIRE_MS))
                    .setMaxRetryTimes(options.get(MysqlOption.MAX_RETRY_TIMES))
                    .build();
        }

        final RowTypeInfo rowTypeInfo = (RowTypeInfo) fromDataTypeToLegacyInfo(producedDataType);

        String[] fieldNames = rowTypeInfo.getFieldNames();
        TypeInformation[] fieldTypes = rowTypeInfo.getFieldTypes();

        int[] lookupKeysIndex = context.getKeys()[0];
        int keyCount = lookupKeysIndex.length;
        String[] keyNames = new String[keyCount];
        for (int i = 0; i < keyCount; i++) {
            keyNames[i] = fieldNames[lookupKeysIndex[i]];
        }
        final RowType rowType = (RowType) physicalSchema.toRowDataType().getLogicalType();

        MysqlRowDataLookUpFunction lookUpFunction
                = new MysqlRowDataLookUpFunction(url, username, password, table, fieldNames, keyNames, fieldTypes, lookupOption, rowType);

        return TableFunctionProvider.of(lookUpFunction);
    }
}