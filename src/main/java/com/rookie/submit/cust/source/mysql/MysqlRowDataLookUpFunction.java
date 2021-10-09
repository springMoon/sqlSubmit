//package com.rookie.submit.cust.source.mysql;
//
//import org.apache.flink.api.common.typeinfo.TypeInformation;
//import org.apache.flink.connector.jdbc.dialect.MySQLDialect;
//import org.apache.flink.connector.jdbc.statement.FieldNamedPreparedStatementImpl;
//import org.apache.flink.connector.jdbc.utils.JdbcTypeUtil;
//import org.apache.flink.connector.jdbc.utils.JdbcUtils;
//import org.apache.flink.shaded.guava30.com.google.common.cache.Cache;
//import org.apache.flink.shaded.guava30.com.google.common.cache.CacheBuilder;
//import org.apache.flink.table.data.RowData;
//import org.apache.flink.table.functions.FunctionContext;
//import org.apache.flink.table.functions.TableFunction;
//import org.apache.flink.types.Row;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import static org.apache.flink.connector.jdbc.utils.JdbcUtils.getFieldFromResultSet;
//import static org.apache.flink.util.Preconditions.checkArgument;
//
///**
// * The MysqlRowDataLookUpFunction is a standard user-defined table function, it can be used in
// * tableAPI and also useful for temporal table join plan in SQL. It looks up the result as {@link
// * RowData}.
// */
//public class MysqlRowDataLookUpFunction extends TableFunction<RowData> {
//    private static final Logger LOG = LoggerFactory.getLogger(MysqlRowDataLookUpFunction.class);
//
//    private transient Connection conn;
//    private final int[] keySqlTypes;
//    private final TypeInformation[] keyTypes;
//
//    private String url;
//    private String username;
//    private String password;
//    private String database;
//    private String table;
//    private MysqlLookupOption mysqlLookupOption;
//    private final long cacheMaxSize;
//    private final long cacheExpireMs;
//    private final int maxRetryTimes;
//
//    private transient PreparedStatement statement;
//    private transient Cache<Row, List<RowData>> cache;
//
//    private final String query;
//
//
//    public MysqlRowDataLookUpFunction(String url, String username, String password, String database,
//                                      String table, String[] fieldNames, String[] keyNames,
//                                      TypeInformation[] fieldTypes, MysqlLookupOption mysqlLookupOption) {
//        this.url = url;
//        this.username = username;
//        this.password = password;
//        this.database = database;
//        this.table = table;
//        this.cacheMaxSize = mysqlLookupOption.getCacheMaxSize();
//        this.cacheExpireMs = mysqlLookupOption.getCacheExpireMs();
//        this.maxRetryTimes = mysqlLookupOption.getMaxRetryTimes();
//        this.mysqlLookupOption = mysqlLookupOption;
//
//        List<String> nameList = Arrays.asList(fieldNames);
//        this.keyTypes =
//                Arrays.stream(keyNames)
//                        .map(
//                                s -> {
//                                    checkArgument(
//                                            nameList.contains(s),
//                                            "keyName %s can't find in fieldNames %s.",
//                                            s,
//                                            nameList);
//                                    return fieldTypes[nameList.indexOf(s)];
//                                })
//                        .toArray(TypeInformation[]::new);
//
//        this.keySqlTypes =
//                Arrays.stream(keyTypes).mapToInt(JdbcTypeUtil::typeInformationToSqlType).toArray();
//
//
//        query = FieldNamedPreparedStatementImpl.parseNamedStatement(
//                new MySQLDialect()
//                        .getSelectFromStatement(
//                                table, fieldNames, keyNames),
//                new HashMap<>());
//
//    }
//
//    @Override
//    public void open(FunctionContext context) throws Exception {
//        try {
//            establishConnectionAndStatement();
//            this.cache =
//                    cacheMaxSize == -1 || cacheExpireMs == -1
//                            ? null
//                            : CacheBuilder.newBuilder()
//                            .expireAfterWrite(cacheExpireMs, TimeUnit.MILLISECONDS)
//                            .maximumSize(cacheMaxSize)
//                            .build();
//        } catch (SQLException sqe) {
//            throw new IllegalArgumentException("open() failed.", sqe);
//        } catch (ClassNotFoundException cnfe) {
//            throw new IllegalArgumentException("JDBC driver class not found.", cnfe);
//        }
//
//
//    }
//
//    private void establishConnectionAndStatement() throws SQLException, ClassNotFoundException {
//        conn = DriverManager.getConnection(url, username, password);
//        statement = conn.prepareStatement(query);
//    }
//
//
//    public void eval(Object... keys) {
//        Row keyRow = Row.of(keys);
//        if (cache != null) {
//            List<RowData> cachedRows = cache.getIfPresent(keyRow);
//            if (cachedRows != null) {
//                for (RowData cachedRow : cachedRows) {
//                    collect(cachedRow);
//                }
//                return;
//            }
//        }
//
//        for (int retry = 0; retry <= maxRetryTimes; retry++) {
//            try {
//                statement.clearParameters();
//                for (int i = 0; i < keys.length; i++) {
//                    JdbcUtils.setField(statement, keySqlTypes[i], keys[i], i);
//                }
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (cache == null) {
//                        while (resultSet.next()) {
//                            collect(convertToRowDataFromResultSet(resultSet));
//                        }
//                    } else {
//                        ArrayList<Row> rows = new ArrayList<>();
//                        while (resultSet.next()) {
//                            Row row = convertToRowDataFromResultSet(resultSet);
//                            rows.add(row);
//                            collect(row);
//                        }
//                        rows.trimToSize();
//                        cache.put(keyRow, rows);
//                    }
//                }
//                break;
//            } catch (SQLException e) {
//                LOG.error(String.format("JDBC executeBatch error, retry times = %d", retry), e);
//                if (retry >= maxRetryTimes) {
//                    throw new RuntimeException("Execution of JDBC statement failed.", e);
//                }
//
//                try {
//                    if (!conn.isValid(mysqlLookupOption.getTimeOut())) {
//                        statement.close();
//                        conn.close();
//                        establishConnectionAndStatement();
//                    }
//                } catch (SQLException | ClassNotFoundException excpetion) {
//                    LOG.error(
//                            "JDBC connection is not valid, and reestablish connection failed",
//                            excpetion);
//                    throw new RuntimeException("Reestablish JDBC connection failed", excpetion);
//                }
//
//                try {
//                    Thread.sleep(1000 * retry);
//                } catch (InterruptedException e1) {
//                    throw new RuntimeException(e1);
//                }
//            }
//        }
//    }
//
//    private RowData convertToRowDataFromResultSet(ResultSet resultSet) throws SQLException {
//        RowData row = new Row(outputSqlTypes.length);
//        for (int i = 0; i < outputSqlTypes.length; i++) {
//            row.setField(i, getFieldFromResultSet(i, outputSqlTypes[i], resultSet));
//        }
//        return row;
//    }
//
//
//    @Override
//    public void close() throws Exception {
//    }
//}
