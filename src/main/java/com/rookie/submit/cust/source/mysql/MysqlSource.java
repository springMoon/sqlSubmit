package com.rookie.submit.cust.source.mysql;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.SimpleCounter;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.binary.BinaryRowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.types.Row;
import org.apache.flink.types.RowKind;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * mysql table source
 */
public class MysqlSource extends RichSourceFunction<RowData> {

    private volatile boolean isRunning = true;
    private String url;
    private String username;
    private String password;
    private String database;
    private String table;
    private DeserializationSchema<RowData> deserializer;
    private transient Counter counter;
    private transient Connection conn;
    private DataType producedDataType;

    public MysqlSource(String url, String username, String password, String database, String table, DeserializationSchema<RowData> deserializer, DataType producedDataType) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.database = database;
        this.table = table;
        this.deserializer = deserializer;
        this.producedDataType = producedDataType;
    }

    @Override
    public void open(Configuration parameters) throws Exception {

        counter = new SimpleCounter();
        this.counter = getRuntimeContext()
                .getMetricGroup()
                .counter("myCounter");

        conn = DriverManager.getConnection(this.url, this.username, this.password);


    }

    @Override
    public void run(SourceContext<RowData> ctx) throws Exception {

        RowType rowType = (RowType) producedDataType.getLogicalType();

        int fieldCount = rowType.getFieldCount();
        List<String> fieldNames = rowType.getFieldNames();

        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        for (int i = 0; i < fieldCount; i++) {
            if (i == fieldCount - 1) {
                builder.append(fieldNames.get(i)).append(" ");
            } else {
                builder.append(fieldNames.get(i)).append(",");
            }
        }
        builder.append("from ");
        builder.append(database).append(".");
        builder.append(table);
        String sql = builder.toString();

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        StringBuilder builder1;
        while (resultSet.next()) {
            builder1 = new StringBuilder();
            // start at 1
            for (int i = 0; i < fieldCount; i++) {
                String value = resultSet.getString(i + 1);
//                result.setField(i, value);
                if (i == fieldCount - 1) {
                    builder1.append(value);
                } else {
                    builder1.append(value).append(",");
                }
            }
            // java.lang.String cannot be cast to org.apache.flink.table.data.StringData
            RowData result = deserializer.deserialize(builder1.toString().getBytes());
            result.setRowKind(RowKind.INSERT);

            ctx.collect(result);
            this.counter.inc();
        }

    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
