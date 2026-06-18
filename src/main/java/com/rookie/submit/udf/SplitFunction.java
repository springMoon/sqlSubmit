package com.rookie.submit.udf;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;

@FunctionHint(output = @DataTypeHint("ROW<word STRING, length INT>"))
public class SplitFunction extends TableFunction<Row> {

    public void eval(String str) {
        if (str == null || str.isEmpty()) {
            return;
        }
        for (String s : str.split(" ")) {
            collect(Row.of(s, Integer.valueOf(s.length())));
        }
    }
}
