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

public class BloomFilter extends AggregateFunction<Integer, CountAcc > {

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
    }

    @Override
    public Integer getValue(CountAcc acc) {
        LOG.info("filter : " + (filter == null));
        // get
        return acc.count;
    }

    @Override
    public CountAcc createAccumulator() {
        CountAcc acc = new CountAcc();
        return acc;
    }

    public void merge(CountAcc acc, Iterable<CountAcc> it) {
        int last = acc.count;
        StringBuilder builder = new StringBuilder();
        for (CountAcc a : it) {
//            LOG.info("last value : " + a.count);
            acc.count += a.count;
        }
    }


}
