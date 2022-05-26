package com.rookie.submit.udf;

import com.rookie.submit.util.DateTimeUtil;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DateAdd extends ScalarFunction {

    private final static Logger LOG = LoggerFactory.getLogger(DateAdd.class);
    public static volatile Map<String, String> map = new HashMap<>();

    @Override
    public void open(FunctionContext context) throws Exception {
        super.open(context);
        map.put("" + System.currentTimeMillis(), "aa-" + System.currentTimeMillis());

        // new Timer
//        Timer timer = new Timer(true);
//        // schedule is 10 second 定义了一个10秒的定时器，定时执行查询数据库的方法
//
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                query();
//            }
//        }, 10000, 10000);
    }

//    public void query(){
//        map.put("" + System.currentTimeMillis(), "aa-" + System.currentTimeMillis());
//        LOG.info("timer run, map element size : " + map.size());
//    }

    @FunctionHint(output = @DataTypeHint("STRING"))
    public String eval(String tar, int num) {

        if (tar == null || tar.length() == 0)
            return null;
        Date day = null;
        try {
            day = DateTimeUtil.parse(tar);
        } catch (Exception e) {
            return null;
        }
        day = DateTimeUtil.plusDay(day, num);

        String resultStr = DateTimeUtil.format(day, DateTimeUtil.YYYY_MM_DD);

        return resultStr + "-" + map.size();

    }
}
