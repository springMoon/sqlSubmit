package com.rookie.submit.udf;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;
import org.apache.flink.types.RowKind;

// 对应 sql ： parse_complex_json_1.sql
public class ParseJson extends TableFunction<Row> {

    @FunctionHint(output = @DataTypeHint("ROW<arr ARRAY<STRING>>"))
    public void eval(String... json) {
        if (json == null || json.length == 0 || json[0] == null) {
            return;
        }
        String[] arr = getStrings(json);
        RowKind rowKind = RowKind.fromByteValue((byte) 0);
        Row row = new Row(rowKind, 1);
        row.setField(0, arr);
        collect(row);
    }

    /**
     * parse user columns from json and provider column name
     */
    private String[] getStrings(String[] json) {
        JsonObject jsonObject = new JsonParser().parse(json[0]).getAsJsonObject();
        int len = json.length - 1;
        String[] arr = new String[len];
        for (int i = 0; i < len; ++i) {
            JsonElement tm = jsonObject.get(json[i + 1]);
            if (tm != null) {
                arr[i] = tm.getAsString();
            } else {
                arr[i] = null;
            }
        }
        return arr;
    }
}
