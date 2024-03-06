package com.rookie.submit.cust.connector.mysql;

import org.apache.flink.connector.jdbc.converter.JdbcRowConverter;
import org.apache.flink.connector.jdbc.databases.mysql.dialect.MySqlDialect;
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
public class MysqlRowDataLookUpFunction extends TableFunction<RowData> {
    private static final long serialVersionUID = 1008611L;
    private static final Logger LOG = LoggerFactory.getLogger(MysqlRowDataLookUpFunction.class);

    private Connection dbConn;
    private final MysqlOption options;
    private final long cacheMaxSize;
    private final long cacheExpireMs;
    private final int maxRetryTimes;
    private final JdbcRowConverter lookupKeyRowConverter;
    private final JdbcRowConverter jdbcRowConverter;

    private transient FieldNamedPreparedStatement statement;
    private transient Cache<RowData, List<RowData>> cache;
    private final String[] keyNames;
    private final String query;

    public MysqlRowDataLookUpFunction(String[] fieldNames, String[] keyNames,
                                      DataType producedDataType, MysqlOption options, RowType rowType) {
        this.cacheMaxSize = options.getCacheMaxSize();
        this.cacheExpireMs = options.getCacheExpireMs();
        this.maxRetryTimes = options.getMaxRetryTimes();
        this.options = options;
        this.keyNames = keyNames;

        // generate lookup query sql
        MySqlDialect mySQLDialect = new MySqlDialect();
        this.query =
                mySQLDialect
                        .getSelectFromStatement(options.getTable(), fieldNames, keyNames);

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
            establishConnectionAndStatement();
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

    private void establishConnectionAndStatement() throws SQLException {
        dbConn = DriverManager.getConnection(options.getUrl(), options.getUsername(), options.getPassword());
        statement = FieldNamedPreparedStatement.prepareStatement(dbConn, query, keyNames);
    }

    /**
     * method eval lookup key,
     * search cache first
     * if cache not exit, query third system
     *
     * @param keys query parameter
     */
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
        // query mysql, retry maxRetryTimes count
        for (int retry = 0; retry <= maxRetryTimes; retry++) {
            try {
                statement.clearParameters();
                statement = lookupKeyRowConverter.toExternal(keyRow, statement);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (cache == null) {
                        // if cache is null, loop to collect result
                        while (resultSet.next()) {
                            collect(jdbcRowConverter.toInternal(resultSet));
                        }
                    } else {
                        // cache is not null, loop to collect result, and save result to cache
                        ArrayList<RowData> rows = new ArrayList<>();
                        while (resultSet.next()) {
                            RowData row = jdbcRowConverter.toInternal(resultSet);
                            rows.add(row);
                            collect(row);
                        }
                        rows.trimToSize();
                        cache.put(keyRow, rows);
                    }
                }
                break;
            } catch (SQLException e) {
                LOG.error(String.format("JDBC executeBatch error, retry times = %d", retry), e);
                if (retry >= maxRetryTimes) {
                    throw new RuntimeException("Execution of JDBC statement failed.", e);
                }

                try {
                    if (!dbConn.isValid(options.getTimeOut())) {
                        statement.close();
                        dbConn.close();
                        establishConnectionAndStatement();
                    }
                } catch (SQLException exception) {
                    LOG.error(
                            "JDBC connection is not valid, and reestablish connection failed", exception);
                    throw new RuntimeException("Reestablish JDBC connection failed", exception);
                }
                try {
                    Thread.sleep(1000 * retry);
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }
            }
        }
    }

    @Override
    public void close() throws Exception {
        if (cache != null) {
            cache.cleanUp();
            cache = null;
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.info("JDBC statement could not be closed: " + e.getMessage());
            } finally {
                statement = null;
            }
        }

        dbConn.close();
    }
}
