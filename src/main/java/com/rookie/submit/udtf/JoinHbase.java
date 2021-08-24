package com.rookie.submit.udtf;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;


@FunctionHint(output = @DataTypeHint("ROW<word STRING, length INT>"))
public class JoinHbase extends TableFunction<Row> {

    @Override
    public void open(FunctionContext context) throws Exception {
        super.open(context);
    }

    public void eval(String str) {
        for (String s : str.split(" ")) {
            // use collect(...) to emit a row
            collect(Row.of(s, s.length()));
        }
    }

    @Override
    public void close() throws Exception {
        super.close();
    }
}
