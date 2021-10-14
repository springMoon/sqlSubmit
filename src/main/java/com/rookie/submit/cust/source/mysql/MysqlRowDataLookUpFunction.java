package com.rookie.submit.cust.source.mysql;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.jdbc.dialect.MySQLDialect;
import org.apache.flink.connector.jdbc.internal.converter.JdbcRowConverter;
import org.apache.flink.connector.jdbc.statement.FieldNamedPreparedStatement;
import org.apache.flink.connector.jdbc.utils.JdbcTypeUtil;
import org.apache.flink.shaded.guava30.com.google.common.cache.Cache;
import org.apache.flink.shaded.guava30.com.google.common.cache.CacheBuilder;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.table.types.logical.VarCharType;
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

import static org.apache.flink.util.Preconditions.checkArgument;

/**
 * The MysqlRowDataLookUpFunction is a standard user-defined table function, it can be used in
 * tableAPI and also useful for temporal table join plan in SQL. It looks up the result as {@link
 * RowData}.
 */
public class MysqlRowDataLookUpFunction extends TableFunction<RowData> {
    private static final long serialVersionUID = 1008611L;
    private static final Logger LOG = LoggerFactory.getLogger(MysqlRowDataLookUpFunction.class);

    private transient Connection dbConn;
    private final DataType producedDataType;

    private final String url;
    private final String username;
    private final String password;
    private final transient MysqlLookupOption mysqlLookupOption;
    private final long cacheMaxSize;
    private final long cacheExpireMs;
    private final int maxRetryTimes;
    private final JdbcRowConverter lookupKeyRowConverter;
    private final JdbcRowConverter jdbcRowConverter;

    private transient FieldNamedPreparedStatement statement;
    private transient Cache<RowData, List<RowData>> cache;
    private final String[] keyNames;

    private final String query;


    public MysqlRowDataLookUpFunction(String url, String username, String password,
                                      String table, String[] fieldNames, String[] keyNames,
                                      DataType producedDataType, MysqlLookupOption mysqlLookupOption, RowType rowType) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.cacheMaxSize = mysqlLookupOption.getCacheMaxSize();
        this.cacheExpireMs = mysqlLookupOption.getCacheExpireMs();
        this.maxRetryTimes = mysqlLookupOption.getMaxRetryTimes();
        this.mysqlLookupOption = mysqlLookupOption;
        this.producedDataType = producedDataType;

        this.keyNames = keyNames;

        MySQLDialect mySQLDialect = new MySQLDialect();
        this.query =
                mySQLDialect
                        .getSelectFromStatement(table, fieldNames, keyNames);

        List<String> list = Arrays.asList(fieldNames);

        this.jdbcRowConverter = mySQLDialect.getRowConverter(rowType);
        int keyLength = keyNames.length;
        LogicalType[] logicalTypes = new LogicalType[keyLength];
        for (int i = 0; i < keyLength; i++) {
            //  通过 名字找 对应类型
            logicalTypes[i] = producedDataType.getLogicalType().getChildren().get(list.indexOf(keyNames[i]));
        }

        this.lookupKeyRowConverter = mySQLDialect.getRowConverter(RowType.of(logicalTypes));

    }

    @Override
    public void open(FunctionContext context) throws Exception {
        try {
            establishConnectionAndStatement();
            this.cache =
                    cacheMaxSize == -1 || cacheExpireMs == -1
                            ? null
                            : CacheBuilder.newBuilder()
                            .expireAfterWrite(cacheExpireMs, TimeUnit.MILLISECONDS)
                            .maximumSize(cacheMaxSize)
                            .build();
        } catch (SQLException sqe) {
            throw new IllegalArgumentException("open() failed.", sqe);
        } catch (ClassNotFoundException cnfe) {
            throw new IllegalArgumentException("JDBC driver class not found.", cnfe);
        }


    }

    private void establishConnectionAndStatement() throws SQLException, ClassNotFoundException {
        dbConn = DriverManager.getConnection(url, username, password);
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
                        while (resultSet.next()) {
                            collect(jdbcRowConverter.toInternal(resultSet));
                        }
                    } else {
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
                    if (!dbConn.isValid(mysqlLookupOption.getTimeOut())) {
                        statement.close();
                        dbConn.close();
                        establishConnectionAndStatement();
                    }
                } catch (SQLException | ClassNotFoundException exception) {
                    LOG.error(
                            "JDBC connection is not valid, and reestablish connection failed",
                            exception);
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
