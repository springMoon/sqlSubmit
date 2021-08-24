package com.rookie.submit.udtf;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.rookie.submit.common.Constant;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@FunctionHint(output = @DataTypeHint("ROW<word STRING, length INT>"))
public class JoinMysql extends TableFunction<Row> {

    private long expireTime;
    private boolean closeConnect = true;
    private static transient Cache<String, HashSet<String>> cache;
    private static final String SQL = "";
    private static transient Connection conn;
    private static transient PreparedStatement ps;

    public JoinMysql(long expireTime) {
        this.expireTime = expireTime;
        if (expireTime < 600) {
            closeConnect = false;
        }
    }

    @Override
    public void open(FunctionContext context) throws Exception {
        cache = CacheBuilder.newBuilder()
//                .maximumSize(2)
                .expireAfterWrite(expireTime, TimeUnit.SECONDS)
                .build();

    }


    public void eval(String key) throws SQLException {
        if (cache.size() == 0) {
            initData();
        }
        Set<String> set = cache.getIfPresent(key);
        for (String value : set) {
            collect(Row.of(key, value));
        }
    }

    /**
     * init data
     */
    private void initData() throws SQLException {

        if (conn == null) {
            reconnect();
        }

        try {
            queryData();
        } catch (SQLException e) {
            // case connection expire
            reconnect();
            queryData();
        }

        // close connection
        if (closeConnect) {
            closeConnect();
        }
    }

    private void queryData() throws SQLException {
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            String key = resultSet.getString(1);
            String value = resultSet.getString(2);
            HashSet<String> valueSet = cache.getIfPresent(key);
            if (valueSet == null) {
                valueSet = new HashSet<>();
                cache.put(key, valueSet);
            }
            valueSet.add(value);
        }
    }

    private void closeConnect() throws SQLException {

        if (conn != null) {
            conn.close();
        }
    }

    /**
     * reconnect to external storage system
     */
    public void reconnect() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
            ps = null;
        }
        if (ps == null) {
            ps = conn.prepareStatement(SQL);
        }

    }

    @Override
    public void close() throws Exception {
        super.close();
    }
}
