package com.rookie.submit.udf;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;
import org.apache.flink.types.RowKind;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * udtf join hbase with non rowkey: no cache, connect hbase every event
 * hbase 表: 10000 条数据，非主键关联测试，关联键值10000条不重复
 * 笔记本idea测试 TPS： 40+
 * 笔记本 on yarn 测试 TPS： 80+
 */
public class JoinHbaseNonRowkeyNoCache extends TableFunction<Row> {

    private static final Logger LOG = LoggerFactory.getLogger(JoinHbaseNonRowkeyNoCache.class);

    private final String familyString;
    private final String qualifierString;

    private Connection connection;
    private Table table;
    private byte[] family;
    private List<byte[]> qualifier;

    public JoinHbaseNonRowkeyNoCache(String familyString, String qualifierString) {
        this.familyString = familyString;
        this.qualifierString = qualifierString;
    }

    @Override
    public void open(FunctionContext context) throws Exception {
        if (StringUtils.isEmpty(familyString)) {
            LOG.error("hbase udtf family is empty");
            System.exit(-1);
        }
        if (StringUtils.isEmpty(qualifierString)) {
            LOG.error("hbase udtf qualifier is empty");
            System.exit(-1);
        }
        family = familyString.getBytes(StandardCharsets.UTF_8);
        String[] arr = qualifierString.split(",");
        qualifier = new ArrayList<>();
        for (String item : arr) {
            qualifier.add(item.getBytes(StandardCharsets.UTF_8));
        }

        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "thinkpad:12181");
        conf.set("hbase.htable.threads.keepalivetime", "20");
        conf.set("zookeeper.znode.parent", "/hbase");

        connection = ConnectionFactory.createConnection(conf);
        table = connection.getTable(TableName.valueOf("user_info"));
        LOG.info("hbase no-cache udtf opened, table: user_info, family: {}, qualifier: {}", familyString, qualifierString);
    }

    @FunctionHint(output = @DataTypeHint("ROW<arr ARRAY<STRING>>"))
    public void eval(String key) throws Exception {
        if (key == null || key.length() == 0) {
            return;
        }
        Scan scan = new Scan();
        for (byte[] item : qualifier) {
            scan.addColumn(family, item);
        }

        SingleColumnValueFilter filter = new SingleColumnValueFilter(family, qualifier.get(0),
                CompareOperator.EQUAL, key.getBytes(StandardCharsets.UTF_8));
        scan.setFilter(filter);

        // ResultScanner must be closed for long-running table functions.
        try (ResultScanner resultScanner = table.getScanner(scan)) {
            java.util.Iterator<Result> it = resultScanner.iterator();
            while (it.hasNext()) {
                Result result = it.next();
                Row row = new Row(RowKind.INSERT, 1);
                row.setField(0, buildOutputArray(result));
                collect(row);
            }
        }
    }

    private String[] buildOutputArray(Result result) {
        String[] arr = new String[qualifier.size() + 1];
        int index = 0;
        String rowkey = new String(result.getRow(), StandardCharsets.UTF_8);
        arr[index] = rowkey;
        for (byte[] item : qualifier) {
            byte[] value = result.getValue(family, item);
            if (value != null) {
                index += 1;
                arr[index] = new String(value, StandardCharsets.UTF_8);
            }
        }
        return arr;
    }

    @Override
    public void close() throws Exception {
        if (table != null) {
            table.close();
            table = null;
        }
        if (connection != null) {
            connection.close();
            connection = null;
        }
        LOG.info("hbase no-cache udtf closed");
    }

    public static void main(String[] args) throws Exception {
        JoinHbaseNonRowkeyNoCache joinHbase = new JoinHbaseNonRowkeyNoCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10");
        joinHbase.open(null);
        joinHbase.eval("002");
        joinHbase.close();
    }
}
