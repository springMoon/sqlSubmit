package com.rookie.submit.cust.source.mysql;

import com.google.gson.JsonObject;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.formats.csv.CsvRowDataDeserializationSchema;
import org.apache.flink.formats.json.JsonRowDataDeserializationSchema;
import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.SimpleCounter;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.connector.source.LookupTableSource;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.StringData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.types.RowKind;

import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * mysql table source
 */
public class MysqlSource extends RichSourceFunction<RowData> implements LookupTableSource {

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

        // jdbc connection
        conn = DriverManager.getConnection(this.url, this.username, this.password);
    }

    @Override
    public void run(SourceContext<RowData> ctx) throws Exception {

        RowType rowType = (RowType) producedDataType.getLogicalType();

        int fieldCount = rowType.getFieldCount();
        List<String> fieldNames = rowType.getFieldNames();

        // splicing query sql
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

        // loop result set
        while (resultSet.next()) {

            GenericRowData result = new GenericRowData(fieldCount);
            result.setRowKind(RowKind.INSERT);
            for (int i = 0; i < fieldCount; i++) {
                String value = resultSet.getString(i + 1);
                result.setField(i, StringData.fromString(value));

                // parse result to RowData
//            if (deserializer instanceof CsvRowDataDeserializationSchema) {
//                // csv
//                result = getCSVRowData(fieldCount, resultSet);
//            } else if (deserializer instanceof JsonRowDataDeserializationSchema) {
//                // json
//                result = getJsonRowData(fieldCount, fieldNames, resultSet);
//            }

            }
            // out result
            if (result != null) {
                ctx.collect(result);
                this.counter.inc();
            }
        }

    }

    /**
     * parse result to csv, then deserializer to RowData
     *
     * @param fieldCount result column count
     * @param resultSet  result
     * @return RowData result
     */
    private RowData getJsonRowData(int fieldCount, List<String> fieldNames, ResultSet resultSet) throws
            SQLException, IOException {
        JsonObject jsonObject = new JsonObject();
        for (int i = 0; i < fieldCount; i++) {
            String value = resultSet.getString(i + 1);
            jsonObject.addProperty(fieldNames.get(i), value);
        }

        RowData result = deserializer.deserialize(jsonObject.toString().getBytes());
        result.setRowKind(RowKind.INSERT);
        return result;
    }

    /**
     * parse result to csv, then deserializer to RowData
     *
     * @param fieldCount result column count
     * @param resultSet  result
     * @return RowData result
     */
    private RowData getCSVRowData(int fieldCount, ResultSet resultSet) throws SQLException, java.io.IOException {
        StringBuilder builder = new StringBuilder();
        GenericRowData result = new GenericRowData(fieldCount);
        result.setRowKind(RowKind.INSERT);
        for (int i = 0; i < fieldCount; i++) {
            String value = resultSet.getString(i + 1);
            if (i == fieldCount - 1) {
                builder.append(value);
            } else {
                builder.append(value).append(",");
            }
            result.setField(i, StringData.fromString(value));
        }
        // java.lang.String cannot be cast to org.apache.flink.table.data.StringData
//        RowData result = deserializer.deserialize(builder.toString().getBytes());
//        result.setRowKind(RowKind.INSERT);
        return result;
    }

    @Override
    public void cancel() {
        isRunning = false;
    }

    @Override
    public LookupRuntimeProvider getLookupRuntimeProvider(LookupContext context) {
        return null;
    }

    @Override
    public DynamicTableSource copy() {
        return null;
    }

    @Override
    public String asSummaryString() {
        return "customize mysql source, support lookup function";
    }
}
