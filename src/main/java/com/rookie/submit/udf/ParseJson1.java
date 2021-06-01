package com.rookie.submit.udf;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;
import org.apache.flink.types.RowKind;


public class ParseJson1 extends TableFunction<Row> {

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING>"))
    public void eval(String json, String col1) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[1];
        inputArr[0] = col1;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING, col2 STRING>"))
    public void eval(String json, String col1, String col2) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[2];
        inputArr[0] = col1;
        inputArr[1] = col2;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING, col2 STRING, col3 STRING>"))
    public void eval(String json, String col1, String col2, String col3) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[3];
        inputArr[0] = col1;
        inputArr[1] = col2;
        inputArr[2] = col3;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING, col2 STRING, col3 STRING, col4 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[4];
        inputArr[0] = col1;
        inputArr[1] = col2;
        inputArr[2] = col3;
        inputArr[3] = col4;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING, col2 STRING, col3 STRING, col4 STRING, col5 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[5];
        inputArr[0] = col1;
        inputArr[1] = col2;
        inputArr[2] = col3;
        inputArr[3] = col4;
        inputArr[4] = col5;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4]));
    }
    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING>"))
    public void eval(String json,String col1,String col2,String col3,String col4,String col5,String col6) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[6];
        inputArr[0] = col1;
        inputArr[1] = col2;
        inputArr[2] = col3;
        inputArr[3] = col4;
        inputArr[4] = col5;
        inputArr[5] = col6;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5]));
    }
    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[7];
        inputArr[0] = col1;
        inputArr[1] = col2;
        inputArr[2] = col3;
        inputArr[3] = col4;
        inputArr[4] = col5;
        inputArr[5] = col6;
        inputArr[6] = col7;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[8];
        inputArr[0] = col1;
        inputArr[1] = col2;
        inputArr[2] = col3;
        inputArr[3] = col4;
        inputArr[4] = col5;
        inputArr[5] = col6;
        inputArr[6] = col7;
        inputArr[7] = col8;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[9];
        inputArr[0] = col1;
        inputArr[1] = col2;
        inputArr[2] = col3;
        inputArr[3] = col4;
        inputArr[4] = col5;
        inputArr[5] = col6;
        inputArr[6] = col7;
        inputArr[7] = col8;
        inputArr[8] = col9;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[10];
        inputArr[0] = col1;
        inputArr[1] = col2;
        inputArr[2] = col3;
        inputArr[3] = col4;
        inputArr[4] = col5;
        inputArr[5] = col6;
        inputArr[6] = col7;
        inputArr[7] = col8;
        inputArr[8] = col9;
        inputArr[9] = col10;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9]));
    }

    /**
     * parse user columns from json and provider column name
     *
     * @param str input json and column name
     * @return result column value
     */
    private String[] getStrings(String json, String[] str) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        int len = str.length;
        String[] arr = new String[len];
        for (int i = 0; i < len; ++i) {
            JsonElement tm = jsonObject.get(str[i]);
            if (tm != null) {
                arr[i] = tm.getAsString();
            } else {
                arr[i] = null;
            }
        }
        return arr;
    }

}
