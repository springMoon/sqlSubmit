package com.rookie.submit.cust.sink.socket;

import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.format.EncodingFormat;
import org.apache.flink.table.connector.sink.DynamicTableSink;
import org.apache.flink.table.connector.sink.SinkFunctionProvider;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;


public class SocketDynamicTableSink implements DynamicTableSink {

    private final String hostname;
    private final int port;
    private final int maxRetry;
    private final long retryInterval;
    private final EncodingFormat<SerializationSchema<RowData>> encodingFormat;
    private final byte byteDelimiter;
    private final DataType producedDataType;

    public SocketDynamicTableSink(
            String hostname,
            int port,
            int maxReTryTime,
            long retryInterval,
            EncodingFormat<SerializationSchema<RowData>> encodingFormat,
            byte byteDelimiter,
            DataType producedDataType) {
        this.hostname = hostname;
        this.port = port;
        this.maxRetry = maxReTryTime;
        this.retryInterval = retryInterval;
        this.encodingFormat = encodingFormat;
        this.byteDelimiter = byteDelimiter;
        this.producedDataType = producedDataType;
    }


    @Override
    public ChangelogMode getChangelogMode(ChangelogMode requestedMode) {
        return ChangelogMode.insertOnly();
    }

    @Override
    public SinkRuntimeProvider getSinkRuntimeProvider(Context context) {


        final SerializationSchema<RowData> serializer = encodingFormat.createRuntimeEncoder(context, producedDataType);

        SocketSinkFunction<RowData> sink = new SocketSinkFunction(hostname, port, serializer, maxRetry, retryInterval);
        return SinkFunctionProvider.of(sink);
    }


    @Override
    public DynamicTableSink copy() {
        return new SocketDynamicTableSink(hostname, port, maxRetry, retryInterval, encodingFormat, byteDelimiter, producedDataType);
    }

    @Override
    public String asSummaryString() {
        return "Socket Table Sink";
    }


}