package com.rookie.submit.udaf;

import com.google.common.hash.Funnels;
import org.apache.flink.table.functions.AggregateFunction;
import org.apache.flink.table.functions.FunctionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * bloom filter
 *
 * todo acc cannot agg over window
 */

public class BloomFilter extends AggregateFunction< Integer, CountAcc > {

    private final static Logger LOG = LoggerFactory.getLogger(BloomFilter.class);
    private com.google.common.hash.BloomFilter<byte[]> filter;
    @Override
    public void open(FunctionContext context) throws Exception {
        LOG.info("bloom filter open...");
        // 创建布隆过滤器对象, 预期数据量，误判率
        filter = com.google.common.hash.BloomFilter.create(
                Funnels.byteArrayFunnel(),
                1000 * 10000,
                0.01);
    }

    public void accumulate(CountAcc acc, String userId) {

        LOG.info("userId : " + userId + ", acc count : " + acc.count + ", acc " + acc.toString());
        if (userId == null || userId.length() == 0) {
            return;
        }
        // parse userId to byte
        byte[] arr = userId.getBytes(StandardCharsets.UTF_8);
        // check userId exists bloom filter
        if(!filter.mightContain(arr)){
            // not exists
            filter.put(arr);
            // count ++
            acc.count += 1;
        }

    }

    @Override
    public void close() throws Exception {
        LOG.info("bloom filter udf exit");
    }

    @Override
    public Integer getValue(CountAcc acc) {
        // get
        LOG.info("get acc count : " + acc.count);
        return acc.count;
    }

    @Override
    public CountAcc createAccumulator() {
        CountAcc acc = new CountAcc();
        LOG.info("create acc"  + ", acc " + acc.toString());
        return acc;
    }

    public void merge(CountAcc acc, Iterable<CountAcc> it) {
//        LOG.info("count 1 : " + acc.count);
//        it.forEach(item -> acc.count += item.count);
//        LOG.info("count 2 : " + acc.count);

        int last = acc.count;
        StringBuilder builder = new StringBuilder();
        for (CountAcc a : it) {
//            LOG.info("last value : " + a.count);
            builder.append(a.count).append(",").append("acc : " + a.toString());
            acc.count += a.count;
        }
        LOG.info("last : " + last + ", last acc " + acc.toString() + ", result : " + acc.count + ", m : " + builder);
    }


}
