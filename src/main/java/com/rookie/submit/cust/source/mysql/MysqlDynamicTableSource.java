package com.rookie.submit.cust.source.mysql;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.connector.source.ScanTableSource;
import org.apache.flink.table.connector.source.SourceFunctionProvider;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;

public class MysqlDynamicTableSource implements ScanTableSource {

    private String url;
    private String username;
    private String password;
    private String database;
    private String table;
    //    private final DecodingFormat<DeserializationSchema<RowData>> decodingFormat;
    private final DataType producedDataType;

    public MysqlDynamicTableSource(
            String url,
            String username,
            String password,
            String database,
            String table,
//            DecodingFormat<DeserializationSchema<RowData>> decodingFormat,
            DataType producedDataType) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.database = database;
        this.table = table;
//        this.decodingFormat = decodingFormat;
        this.producedDataType = producedDataType;
    }

    @Override
    public ChangelogMode getChangelogMode() {
        // in our example the format decides about the changelog mode
        // but it could also be the source itself
        return ChangelogMode.insertOnly();
    }

    @Override
    public ScanRuntimeProvider getScanRuntimeProvider(ScanContext runtimeProviderContext) {

        // create runtime classes that are shipped to the cluster
//        final DeserializationSchema<RowData> deserializer = decodingFormat.createRuntimeDecoder(
//                runtimeProviderContext,
//                producedDataType);


        final SourceFunction<RowData> sourceFunction
                = new MysqlSource(url, username, password, database, table, producedDataType);

        return SourceFunctionProvider.of(sourceFunction, false);
    }

    @Override
    public DynamicTableSource copy() {
        return new MysqlDynamicTableSource(url, username, password, database, table, producedDataType);
    }

    @Override
    public String asSummaryString() {
        return "Http Table Source";
    }
}