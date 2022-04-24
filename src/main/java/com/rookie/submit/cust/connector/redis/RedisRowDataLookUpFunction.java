package com.rookie.submit.cust.connector.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;
import org.apache.flink.connector.jdbc.dialect.MySQLDialect;
import org.apache.flink.connector.jdbc.internal.converter.JdbcRowConverter;
import org.apache.flink.connector.jdbc.statement.FieldNamedPreparedStatement;
import org.apache.flink.shaded.guava30.com.google.common.cache.Cache;
import org.apache.flink.shaded.guava30.com.google.common.cache.CacheBuilder;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The MysqlRowDataLookUpFunction is a standard user-defined table function, it can be used in
 * tableAPI and also useful for temporal table join plan in SQL. It looks up the result as {@link
 * RowData}.
 */
public class RedisRowDataLookUpFunction extends TableFunction<RowData> {
    private static final long serialVersionUID = 1008611L;
    private static final Logger LOG = LoggerFactory.getLogger(RedisRowDataLookUpFunction.class);

    private final RedisOption options;
    private final long cacheMaxSize;
    private final long cacheExpireMs;
    private final int maxRetryTimes;
    private final JdbcRowConverter lookupKeyRowConverter;
    private final JdbcRowConverter jdbcRowConverter;

    private transient FieldNamedPreparedStatement statement;
    private transient Cache<RowData, RowData> cache;
    private final String[] keyNames;

    private RedisClient redisClient;
    private StatefulRedisConnection<String, String> connection;
    private RedisClusterCommands<String, String> command;

    public RedisRowDataLookUpFunction(String[] fieldNames, String[] keyNames,
                                      DataType producedDataType, RedisOption options, RowType rowType) {
        this.cacheMaxSize = options.getCacheMaxSize();
        this.cacheExpireMs = options.getCacheExpireMs();
        this.maxRetryTimes = options.getMaxRetryTimes();
        this.options = options;
        this.keyNames = keyNames;

        // generate lookup query sql
        MySQLDialect mySQLDialect = new MySQLDialect();

        List<String> list = Arrays.asList(fieldNames);
        // get jdbc row converter
        this.jdbcRowConverter = mySQLDialect.getRowConverter(rowType);
        int keyLength = keyNames.length;
        LogicalType[] logicalTypes = new LogicalType[keyLength];
        for (int i = 0; i < keyLength; i++) {
            // find query key type by key name
            logicalTypes[i] = producedDataType.getLogicalType().getChildren().get(list.indexOf(keyNames[i]));
        }

        // get lookup key row converter
        this.lookupKeyRowConverter = mySQLDialect.getRowConverter(RowType.of(logicalTypes));
    }

    @Override
    public void open(FunctionContext context) {
        try {
            reconnect();
            // cache, if not set "mysql.lookup.cache.max.size" and "mysql.lookup.cache.expire.ms", do not use cache
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

    }

    public void eval(Object... keys) {
        RowData keyRow = GenericRowData.of(keys);
        // get row from cache
        if (cache != null) {
            RowData cachedRows = cache.getIfPresent(keyRow);
            if (cachedRows != null) {
                collect(cachedRows);
                return;
            }
        }

        String result = command.get(keys[0].toString());

        if (result == null) {

            RowData rows = GenericRowData.of(result);
            rows.setRowKind(RowKind.INSERT);
            collect(rows);
            cache.put(keyRow, rows);
        }
    }


    @Override
    public void close() throws Exception {
        LOG.info("redis dynamic table source close");
        connection.close();
    }
}
