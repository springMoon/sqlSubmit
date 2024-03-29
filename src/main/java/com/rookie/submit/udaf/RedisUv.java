package com.rookie.submit.udaf;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.apache.flink.table.functions.AggregateFunction;
import org.apache.flink.table.functions.FunctionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * accumulate add user_id to redis
 * getValue: get all redis user_id, count the uv
 */
public class RedisUv extends AggregateFunction<Integer, Integer> {

    private final static Logger LOG = LoggerFactory.getLogger(RedisUv.class);
    // "redis://localhost"
    private String url;
    private StatefulRedisConnection<String, String> connection;
    private RedisClient redisClient;
    private RedisCommands<String, String> sync;
    private volatile String key;

    public RedisUv(String url, String key ) {
        this.url = url;
//        this.key = key;
    }

    @Override
    public void open(FunctionContext context) throws Exception {
        // connect redis
        reconnect();
    }

    public void reconnect() {
        redisClient = RedisClient.create(this.url);
        connection = redisClient.connect();
        sync = connection.sync();
    }

    public void accumulate(Integer acc, String key, String userId) {

        System.out.println("acc :" + Thread.currentThread().getName());
        if (this.key == null) {
            this.key = key;
        }
        int retry = 3;
        while (retry >= 1) {
            try {
                sync.hset(key, userId, "0");
                return;
            } catch (Exception e) {
                LOG.info("set redis error, retry");
                reconnect();
                retry -= 1;
            }
        }

    }

    @Override
    public Integer getValue(Integer accumulator) {

        System.out.println("get :" + Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        int size = 0;
        if (this.key == null) {
            return size;
        }
        // get all userId, count size
        int retry = 3;
        while (retry >= 1) {
            try {
                size = sync.hgetall(this.key).size();
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
    public Integer createAccumulator() {
        return 0;
    }

    public void merge(Integer acc, Iterable<Integer> it) {
        // do nothing
    }
}
