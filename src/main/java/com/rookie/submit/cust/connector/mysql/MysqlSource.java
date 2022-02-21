package com.rookie.submit.cust.connector.mysql;

import com.rookie.submit.cust.source.base.RowDataConverterBase;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.SimpleCounter;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.types.RowKind;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * mysql table source
 * query mysql data
 */
public class MysqlSource extends RichSourceFunction<RowData> {

    private volatile boolean isRunning = true;
    private transient Counter counter;
    private transient Connection conn;
    private final String[] fieldNames;
    private final int fieldCount;
    private final RowType rowType;
    private final MysqlOption options;

    public MysqlSource(DataType producedDataType, MysqlOption options) {
        rowType = (RowType) producedDataType.getLogicalType();

        fieldCount = rowType.getFieldCount();
        List<String> nameList = rowType.getFieldNames();
        fieldNames = nameList.toArray(new String[fieldCount]);
        this.options = options;
    }

    @Override
    public void open(Configuration parameters) throws Exception {

        counter = new SimpleCounter();
        this.counter = getRuntimeContext()
                .getMetricGroup()
                .counter("myCounter");
        // jdbc connection
        conn = DriverManager.getConnection(this.options.getUrl(), this.options.getUsername(), this.options.getPassword());

    }

    @Override
    public void run(SourceContext<RowData> ctx) throws Exception {

        // splicing query sql
        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        for (int i = 0; i < fieldCount; i++) {
            if (i == fieldCount - 1) {
                builder.append(fieldNames[i]).append(" ");
            } else {
                builder.append(fieldNames[i]).append(",");
            }
        }
        builder.append("from ");
        builder.append(this.options.getDatabase()).append(".");
        builder.append(this.options.getTable());
        String sql = builder.toString();

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        // loop result set
        while (isRunning && resultSet.next()) {

            GenericRowData result = new GenericRowData(fieldCount);
            result.setRowKind(RowKind.INSERT);
            for (int i = 0; i < fieldCount; i++) {
                LogicalType type = rowType.getTypeAt(i);
                String value = resultSet.getString(i + 1);
                Object fieldValue = RowDataConverterBase.createConverter(type, value);

                result.setField(i, fieldValue);
                // out result
            }
            ctx.collect(result);
            this.counter.inc();
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }

}
