package com.rookie.submit.cust.connector.mysql;

import com.rookie.submit.cust.source.base.RowDataConverterBase;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.SimpleCounter;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.types.RowKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * mysql table source
 * query mysql data
 */
public class MysqlSource extends RichParallelSourceFunction<RowData> {

    private final static Logger LOG = LoggerFactory.getLogger(MysqlSource.class);
    private volatile boolean isRunning = true;
    private transient Counter counter;
    private transient Connection conn;
    private final String[] fieldNames;
    private final int fieldCount;
    private final RowType rowType;
    private final MysqlOption options;
    private Long min;
    private Long max;
    private  long batchSize;

    public MysqlSource(DataType producedDataType, MysqlOption options) {
        rowType = (RowType) producedDataType.getLogicalType();

        fieldCount = rowType.getFieldCount();
        List<String> nameList = rowType.getFieldNames();
        fieldNames = nameList.toArray(new String[fieldCount]);
        this.options = options;
        batchSize = options.getBatchSize();
    }

    @Override
    public void open(Configuration parameters) throws Exception {

        counter = new SimpleCounter();
        this.counter = getRuntimeContext()
                .getMetricGroup()
                .counter("myCounter");
        // jdbc connection
        conn = DriverManager.getConnection(this.options.getUrl(), this.options.getUsername(), this.options.getPassword());
        String key = this.options.getKey();
        // find min/max
        String sql = "select min(" + key + ") as min, max(" + key + ") as max from " + this.options.getTable();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        resultSet.next();

        // todo cdc data split algorithm ChunkSplitter.splitTableIntoChunks
        min = resultSet.getLong(1);
        max = resultSet.getLong(2);
        LOG.info("load table min {}, max {}", min, max);

    }

    @Override
    public void run(SourceContext<RowData> ctx) throws Exception {
        // splicing query sql
        String sql = makeupSql();
        PreparedStatement ps = conn.prepareStatement(sql);
        // Split by primary key for subtask
        int index = getRuntimeContext().getIndexOfThisSubtask();
        int total = getRuntimeContext().getNumberOfParallelSubtasks();

        long size = (max - min);
        if(this.options.getBatchSize() > size){
            this.batchSize = (long)Math.floor(size / total);
        }
        // subtask start index : min + index * 平均数量
        long indexStart = min + index * batchSize;
        long indexEnd = min + (index + 1)* batchSize;
        // if index 是最大那个，把结尾哪些一块包含进去
        if(index == total - 1){
            indexEnd = max;
        }
        
        LOG.info("subtask index : {}, data range : {} to {}", index, indexStart, indexEnd);

        for(; indexStart< indexEnd ; indexStart += batchSize ) {
            long currentEnd = indexStart + this.options.getBatchSize() > indexEnd ? indexEnd : indexStart + this.options.getBatchSize();
            LOG.info("subtask index {} start process : {} to {}", index, indexStart, currentEnd);
            ps.setLong(1, indexStart);
            ps.setLong(2, currentEnd);
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
        LOG.info("subtask {} finish scan", index);
    }

    /**
     * make up query sql
     * @return
     */
    private String makeupSql() {
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
        builder.append(" where " + this.options.getKey() + " >= ?");
        builder.append(" and " + this.options.getKey() + " < ?");

        return builder.toString();
    }

    @Override
    public void cancel() {
        isRunning = false;

    }

    @Override
    public void close() throws Exception {
       conn.close();
    }
}
