package com.rookie.submit.udtf;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;


/**
 * udtf timer
 */
@FunctionHint(output = @DataTypeHint("ROW<word STRING, length INT>"))
public class UdtfTimer extends TableFunction<Row> {
    private final static Logger LOG = LoggerFactory.getLogger(UdtfTimer.class);

    private long expireTime;
    private boolean closeConnect = true;
//    private static transient Cache<String, HashSet<String>> cache;
//    private static final String SQL = "";
//    private static transient Connection conn;
//    private static transient PreparedStatement ps;

    public static volatile Map<String, String> map = new HashMap<>();

    public UdtfTimer(long expireTime) {
        this.expireTime = expireTime;
        if (expireTime < 600) {
            closeConnect = false;
        }
    }

    @Override
    public void open(FunctionContext context) throws Exception {
//        cache = CacheBuilder.newBuilder()
////                .maximumSize(2)
//                .expireAfterWrite(expireTime, TimeUnit.SECONDS)
//                .build();

        // new Timer
        Timer timer = new Timer(true);
        // schedule is 10 second 定义了一个10秒的定时器，定时执行查询数据库的方法

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                query();
            }
        }, 10000, 10000);

    }
    public void query(){
        map.put("" + System.currentTimeMillis(), "aa-" + System.currentTimeMillis());
        LOG.info("timer run, map element size : " + map.size());
    }


    public void eval(String key) throws SQLException {
//        if (cache.size() == 0) {
//            initData();
//        }
//        Set<String> set = cache.getIfPresent(key);
//        for (String value : set) {
//            collect(Row.of(key, value));
//        }

        collect(Row.of(key, map.size()));
    }

    /**
     * init data
     */
//    private void initData() throws SQLException {
//
//        if (conn == null) {
//            reconnect();
//        }
//
//        try {
//            queryData();
//        } catch (SQLException e) {
//            // case connection expire
//            reconnect();
//            queryData();
//        }
//
//        // close connection
//        if (closeConnect) {
//            closeConnect();
//        }
//    }

//    private void queryData() throws SQLException {
//        ResultSet resultSet = ps.executeQuery();
//        while (resultSet.next()) {
//            String key = resultSet.getString(1);
//            String value = resultSet.getString(2);
//            HashSet<String> valueSet = cache.getIfPresent(key);
//            if (valueSet == null) {
//                valueSet = new HashSet<>();
//                cache.put(key, valueSet);
//            }
//            valueSet.add(value);
//        }
//    }

//    private void closeConnect() throws SQLException {
//
//        if (conn != null) {
//            conn.close();
//        }
//    }

    /**
     * reconnect to external storage system
     */
//    public void reconnect() throws SQLException {
//        if (conn == null) {
//            conn = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
//            ps = null;
//        }
//        if (ps == null) {
//            ps = conn.prepareStatement(SQL);
//        }
//
//    }

    @Override
    public void close() throws Exception {
        super.close();
    }
}
