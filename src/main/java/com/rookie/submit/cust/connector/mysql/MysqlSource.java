package com.rookie.submit.cust.connector.mysql;

import com.rookie.submit.cust.source.base.RowDataConverterBase;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.SimpleCounter;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.types.RowKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
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
    // 列名数组
    private final String[] fieldNames;
    // 列数
    private final int fieldCount;
    private final RowType rowType;
    // 配置项
    private final MysqlOption options;
    // 是否有主键
    private boolean hasKey = true;
    // 表中主键的最大值，最小值
    private Long min = -1l;
    private Long max = -1l;
    // 每批次读取的数据量
    private long batchSize;


    public MysqlSource(DataType producedDataType, MysqlOption options) {
        rowType = (RowType) producedDataType.getLogicalType();
        // 获取列
        fieldCount = rowType.getFieldCount();
        List<String> nameList = rowType.getFieldNames();
        fieldNames = nameList.toArray(new String[fieldCount]);
        // 获取配置
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
        // 获取主键
        String key = this.options.getKey();
        // 如果没有配置主键
        if (key != null) {
            // find min/max
            String sql = "select min(" + key + ") as min, max(" + key + ") as max from " + this.options.getTable();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            // todo cdc data split algorithm ChunkSplitter.splitTableIntoChunks
            // flink cdc key only support number type
            min = resultSet.getLong(1);
            max = resultSet.getLong(2);
        } else {
            hasKey = false;
        }
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
        // cal current subtask data range, if not config key, data ranger (-1,0)
        long size = (max - min);
        long avg = (long) Math.floor(size / total);
        if (this.options.getBatchSize() > size) {
            this.batchSize = avg;
        }
        // subtask start index : min + index * 平均数量
        long indexStart = min + index * avg;
        long indexEnd = min + (index + 1) * avg;
        // if index 是最大那个，把结尾哪些一块包含进去
        if (index == total - 1) {
            // include max id
            indexEnd = max + 1;
        }

        LOG.info("subtask index : {}, data range : {} to {}， -1 means no split", index, indexStart, indexEnd);
        // query data in batch
        for (; indexStart < indexEnd; indexStart += batchSize) {
            long currentEnd = indexStart + this.options.getBatchSize() > indexEnd ? indexEnd : indexStart + this.options.getBatchSize();
            LOG.info("subtask index {} start process : {} to {}", index, indexStart, currentEnd);
            if (hasKey) {
                ps.setLong(1, indexStart);
                ps.setLong(2, currentEnd);
            }
            ResultSet resultSet = ps.executeQuery();
            // loop result set
            while (isRunning && resultSet.next()) {
                queryAndEmitData(ctx, resultSet);
            }
            // if no key, only exec once
            if (!hasKey) {
                break;
            }
        }
        LOG.info("subtask {} finish scan", index);
    }

    // 查询 数据库，发出返回结果
    private void queryAndEmitData(SourceContext<RowData> ctx, ResultSet resultSet) throws SQLException {
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

    /**
     * make up query sql
     *
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
        if (hasKey) {
            builder.append(" where " + this.options.getKey() + " >= ?");
            builder.append(" and " + this.options.getKey() + " < ?");
        }

        return builder.toString();
    }

    @Override
    public void cancel() {
        isRunning = false;

    }

}
