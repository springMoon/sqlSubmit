package com.rookie.submit.udaf;

import org.apache.flink.table.functions.AggregateFunction;
import org.apache.flink.table.functions.FunctionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * udaf for query redis only get value(window fire)
 */
public class JedisRedisUv extends AggregateFunction<Integer, CountAcc> {

    private final static Logger LOG = LoggerFactory.getLogger(JedisRedisUv.class);
    private String host;
    private int port;
    private Jedis jedis;
    private String key;

    public JedisRedisUv(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void open(FunctionContext context) throws Exception {
        // connect redis
        reconnect();
    }

    public void reconnect() {
        jedis = new Jedis(this.host, this.port);
    }

    public void accumulate(CountAcc acc, String key, String userId) {

        this.key = key;
        if (acc.key == null) {
            acc.key = key;
        }
        acc.count += 1;
        int retry = 3;
        while (retry >= 1) {
            try {
                jedis.hset(key, userId, "1");
                break;
            } catch (Exception e) {
                LOG.info("set redis error, retry");
                reconnect();
                retry -= 1;
            }
        }

    }

    @Override
    public Integer getValue(CountAcc acc) {
        long start = System.currentTimeMillis();
        int size = 0;
        if (acc.key == null) {
            return size;
        }
        // get all userId, count size
        int retry = 3;
        while (retry >= 1) {
            try {
                jedis.flushAll();
                size = jedis.hgetAll(this.key).size();
                break;
            } catch (Exception e) {
                LOG.info("set redis error, retry");
                reconnect();
                retry -= 1;
            }
        }
        long end = System.currentTimeMillis();
        LOG.info("count all cost : " + (end - start));
        return size;
    }

    @Override
    public CountAcc createAccumulator() {

        CountAcc acc = new CountAcc();
        acc.key = this.key;
        return acc;
    }

    public void merge(CountAcc acc, Iterable<CountAcc> it) {
        // do nothing
        it.forEach(item -> acc.count += item.count);

    }
}
