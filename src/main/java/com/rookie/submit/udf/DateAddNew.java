package com.rookie.submit.udf;

import com.rookie.submit.util.DateTimeUtil;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.ScalarFunction;

import java.util.Date;

public class DateAddNew extends ScalarFunction {

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

        return resultStr;

    }
}
