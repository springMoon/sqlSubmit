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

    public static volatile Map<String, String> map = new HashMap<>();

    public UdtfTimer(long expireTime) {
        this.expireTime = expireTime;
        if (expireTime < 600) {
            closeConnect = false;
        }
    }

    @Override
    public void open(FunctionContext context) throws Exception {

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
        collect(Row.of(key, map.size()));
    }


    @Override
    public void close() throws Exception {
        super.close();
    }
}
