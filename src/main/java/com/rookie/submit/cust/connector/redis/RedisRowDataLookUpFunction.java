package com.rookie.submit.cust.connector.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.jdbc.dialect.MySQLDialect;
import org.apache.flink.connector.jdbc.internal.converter.JdbcRowConverter;
import org.apache.flink.connector.jdbc.statement.FieldNamedPreparedStatement;
import org.apache.flink.shaded.guava30.com.google.common.cache.Cache;
import org.apache.flink.shaded.guava30.com.google.common.cache.CacheBuilder;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.StringData;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.types.RowKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * lookup join redis source
 */
public class RedisRowDataLookUpFunction extends TableFunction<RowData> {
    private static final long serialVersionUID = 10086111L;
    private static final Logger LOG = LoggerFactory.getLogger(RedisRowDataLookUpFunction.class);

    private final RedisOption options;
    private final long cacheMaxSize;
    private final long cacheExpireMs;
    private final int maxRetryTimes;

    private transient Cache<RowData, List<RowData>> cache;
    private final String[] keyNames;

    private RedisClient redisClient;
    private StatefulRedisConnection<String, String> connection;
    private RedisClusterCommands<String, String> command;

    public RedisRowDataLookUpFunction(String[] keyNames,
                                      RedisOption options) {
        this.cacheMaxSize = options.getCacheMaxSize();
        this.cacheExpireMs = options.getCacheExpireMs();
        this.maxRetryTimes = options.getMaxRetryTimes();
        this.options = options;
        this.keyNames = keyNames;
    }

    @Override
    public void open(FunctionContext context) {
        try {
            reconnect();
            // cache, if not set "lookup.cache.max.size" and "lookup.cache.expire.ms", do not use cache
            this.cache =
                    cacheMaxSize == -1 || cacheExpireMs == -1
                            ? null
                            : CacheBuilder.newBuilder()
                            .expireAfterWrite(cacheExpireMs, TimeUnit.MILLISECONDS)
                            .maximumSize(cacheMaxSize)
                            .build();
        } catch (SQLException sqe) {
            throw new IllegalArgumentException("open() failed.", sqe);
        }
    }


    private void reconnect() throws SQLException {
        redisClient = RedisClient.create(options.getUrl());
        connection = redisClient.connect();
        command = connection.sync();

        LOG.info("reconnect redis");
    }

    public void eval(Object... keys) {
        RowData keyRow = GenericRowData.of(keys);
        // get row from cache
        if (cache != null) {
            List<RowData> cachedRows = cache.getIfPresent(keyRow);
            if (cachedRows != null) {
                for (RowData cachedRow : cachedRows) {
                    collect(cachedRow);
                }
                return;
            }
        }

        List<Tuple2<String, String>> list = new ArrayList<>();

        String key = keys[0].toString();
        String type = command.type(key);

        switch (type) {
            case "string":
                String result = command.get(key);
                list.add(new Tuple2<>(key, result));
                break;
            case "list":
                List<String> result1 = command.lrange(key, 0, -1);
                result1.forEach((String v) -> list.add(new Tuple2<>(key, v)));
                break;
            case "hash":
                if (keys.length == 2) {
                    String key2 = keys[1].toString();
                    String result3 = command.hget(key, key2);
                    list.add(new Tuple2<>(key, result3));
                } else {
                    Map<String, String> result4 = command.hgetall(key);
                    result4.entrySet().forEach((Map.Entry<String, String> item) -> list.add(new Tuple2<>(item.getKey(), item.getValue())));
                }
                break;
            case "set":
                Set<String> result5 = command.smembers(key);
                for (int i = 0; i < result5.size(); i++) {
                    result5.forEach((String v) -> list.add(new Tuple2<>(key, v)));
                }
                break;
            case "zset":
                List<String> result6 = command.zrange(key, 0, -1);
                result6.forEach((String v) -> list.add(new Tuple2<>(key, v)));
                break;
            default:
                LOG.debug("nothing");
                break;
        }

        if (list.size() > 0) {
            List<RowData> cacheList = new ArrayList<>();
            for (Tuple2<String, String> item : list) {
                GenericRowData row = new GenericRowData(3);
                row.setField(0, StringData.fromString(key));
                row.setField(1, StringData.fromString(item.f0));
                row.setField(2, StringData.fromString(item.f1));
                row.setRowKind(RowKind.INSERT);
                collect(row);
                cacheList.add(row);
            }


            cache.put(keyRow, cacheList);
        }
    }


    @Override
    public void close() throws Exception {
        LOG.info("redis dynamic table source close");
        connection.close();
    }
}

