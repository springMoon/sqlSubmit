package com.rookie.submit.udf;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
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
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * udtf join hbase with non rowkey: guava cache, connect hbase only get null
 * hbase 表: 10000 条数据，非主键关联测试，关联键值10000条不重复，缓存时间 10 min
 * 笔记本idea测试 TPS： 350+
 * 笔记本 on yarn 测试 TPS： 900+ (基本可用，服务器环境应该会好很多，不够还可以加并行度)
 */
public class JoinHbaseNonRowkeyCache extends TableFunction<Row> {

    private static final Logger LOG = LoggerFactory.getLogger(JoinHbaseNonRowkeyCache.class);

    private final String familyString;
    private final String qualifierString;
    private final long timeOut;
    private final long cacheSize;

    private Table table;
    private byte[] family;
    private List<byte[]> qualifier;
    private Cache<String, List<String[]>> cache;

    public JoinHbaseNonRowkeyCache(String familyString, String qualifierString, long timeOut, long cacheSize) {
        this.familyString = familyString;
        this.qualifierString = qualifierString;
        this.timeOut = timeOut;
        this.cacheSize = cacheSize;
    }

    @Override
    public void open(FunctionContext context) throws Exception {
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "thinkpad:12181");
        conf.set("hbase.htable.threads.keepalivetime", "20");
        conf.set("zookeeper.znode.parent", "/hbase");

        Connection connection = ConnectionFactory.createConnection(conf);
        table = connection.getTable(TableName.valueOf("user_info"));

        if (StringUtils.isEmpty(familyString)) {
            LOG.error("hbase udtf family is empty");
            System.exit(-1);
        }
        if (StringUtils.isEmpty(qualifierString)) {
            LOG.error("hbase udtf qualifier is empty");
            System.exit(-1);
        }
        family = familyString.getBytes("UTF8");
        String[] arr = qualifierString.split(",");
        qualifier = new ArrayList<>();
        for (String item : arr) {
            qualifier.add(item.getBytes("UTF8"));
        }

        LOG.info("hbase udtf join family: " + familyString + ", qualifier: " + qualifierString);

        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(timeOut, TimeUnit.SECONDS)
                .maximumSize(cacheSize)
                .build();
    }

    @FunctionHint(output = @DataTypeHint("ROW<arr ARRAY<STRING>>"))
    public void eval(String key) throws Exception {
        if (key == null || key.length() == 0) {
            return;
        }
        RowKind rowKind = RowKind.fromByteValue((byte) 0);
        Row row = new Row(rowKind, 1);

        List<String[]> list = cache.getIfPresent(key);
        if (list != null) {
            for (String[] arr : list) {
                row.setField(0, arr);
                collect(row);
            }
            return;
        }

        list = queryHbase(key);
        if (list.size() == 0) {
            return;
        }
        cache.put(key, list);
        for (String[] arr : list) {
            row.setField(0, arr);
            collect(row);
        }
    }

    /**
     * query hbase
     *
     * @param key join key
     * @return query result row
     */
    private List<String[]> queryHbase(String key) throws Exception {
        Scan scan = new Scan();
        for (byte[] item : qualifier) {
            scan.addColumn(family, item);
        }

        SingleColumnValueFilter filter = new SingleColumnValueFilter(family, qualifier.get(0), CompareOperator.EQUAL, key.getBytes("UTF8"));
        scan.setFilter(filter);

        org.apache.hadoop.hbase.client.ResultScanner resultScanner = table.getScanner(scan);
        java.util.Iterator<org.apache.hadoop.hbase.client.Result> it = resultScanner.iterator();

        List<String[]> list = new ArrayList<>();
        while (it.hasNext()) {
            org.apache.hadoop.hbase.client.Result result = it.next();
            String[] arr = new String[qualifier.size() + 1];
            int index = 0;
            String rowkey = new String(result.getRow());
            arr[index] = rowkey;
            for (byte[] item : qualifier) {
                byte[] value = result.getValue(family, item);
                if (value != null) {
                    index += 1;
                    arr[index] = new String(value, "UTF8");
                }
            }
            list.add(arr);
        }
        return list;
    }

    @Override
    public void close() throws Exception {
        if (table != null) {
            table.close();
        }
    }

    public static void main(String[] args) throws Exception {
        JoinHbaseNonRowkeyNoCache joinHbase = new JoinHbaseNonRowkeyNoCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10");
        joinHbase.open(null);
        joinHbase.eval("002");
    }
}
