package com.rookie.submit.udf;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;
import org.apache.flink.types.RowKind;


public class ParseJson extends TableFunction<Row> {

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
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6) {
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
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]));
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

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[11];
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
        inputArr[10] = col11;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[12];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[13];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[14];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[15];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[16];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[17];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[18];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[19];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[20];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[21];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[22];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[23];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[24];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[25];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[26];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[27];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[28];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[29];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[30];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[31];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[32];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[33];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[34];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[35];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[36];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[37];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[38];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[39];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[40];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[41];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[42];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[43];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[44];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[45];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[46];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[47];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[48];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[49];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[50];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[51];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[52];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[53];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[54];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[55];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[56];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[57];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[58];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[59];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[60];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[61];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[62];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[63];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[64];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[65];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[66];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[67];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[68];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[69];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[70];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[71];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[72];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[73];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[74];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[75];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[76];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[77];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[78];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[79];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[80];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[81];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[82];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[83];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[84];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[85];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[86];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[87];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[88];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING,col89 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88, String col89) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[89];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        inputArr[88] = col89;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87], arr[88]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING,col89 STRING,col90 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88, String col89, String col90) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[90];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        inputArr[88] = col89;
        inputArr[89] = col90;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87], arr[88], arr[89]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING,col89 STRING,col90 STRING,col91 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88, String col89, String col90, String col91) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[91];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        inputArr[88] = col89;
        inputArr[89] = col90;
        inputArr[90] = col91;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87], arr[88], arr[89], arr[90]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING,col89 STRING,col90 STRING,col91 STRING,col92 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88, String col89, String col90, String col91, String col92) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[92];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        inputArr[88] = col89;
        inputArr[89] = col90;
        inputArr[90] = col91;
        inputArr[91] = col92;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87], arr[88], arr[89], arr[90], arr[91]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING,col89 STRING,col90 STRING,col91 STRING,col92 STRING,col93 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88, String col89, String col90, String col91, String col92, String col93) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[93];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        inputArr[88] = col89;
        inputArr[89] = col90;
        inputArr[90] = col91;
        inputArr[91] = col92;
        inputArr[92] = col93;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87], arr[88], arr[89], arr[90], arr[91], arr[92]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING,col89 STRING,col90 STRING,col91 STRING,col92 STRING,col93 STRING,col94 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88, String col89, String col90, String col91, String col92, String col93, String col94) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[94];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        inputArr[88] = col89;
        inputArr[89] = col90;
        inputArr[90] = col91;
        inputArr[91] = col92;
        inputArr[92] = col93;
        inputArr[93] = col94;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87], arr[88], arr[89], arr[90], arr[91], arr[92], arr[93]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING,col89 STRING,col90 STRING,col91 STRING,col92 STRING,col93 STRING,col94 STRING,col95 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88, String col89, String col90, String col91, String col92, String col93, String col94, String col95) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[95];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        inputArr[88] = col89;
        inputArr[89] = col90;
        inputArr[90] = col91;
        inputArr[91] = col92;
        inputArr[92] = col93;
        inputArr[93] = col94;
        inputArr[94] = col95;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87], arr[88], arr[89], arr[90], arr[91], arr[92], arr[93], arr[94]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING,col89 STRING,col90 STRING,col91 STRING,col92 STRING,col93 STRING,col94 STRING,col95 STRING,col96 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88, String col89, String col90, String col91, String col92, String col93, String col94, String col95, String col96) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[96];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        inputArr[88] = col89;
        inputArr[89] = col90;
        inputArr[90] = col91;
        inputArr[91] = col92;
        inputArr[92] = col93;
        inputArr[93] = col94;
        inputArr[94] = col95;
        inputArr[95] = col96;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87], arr[88], arr[89], arr[90], arr[91], arr[92], arr[93], arr[94], arr[95]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING,col89 STRING,col90 STRING,col91 STRING,col92 STRING,col93 STRING,col94 STRING,col95 STRING,col96 STRING,col97 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88, String col89, String col90, String col91, String col92, String col93, String col94, String col95, String col96, String col97) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[97];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        inputArr[88] = col89;
        inputArr[89] = col90;
        inputArr[90] = col91;
        inputArr[91] = col92;
        inputArr[92] = col93;
        inputArr[93] = col94;
        inputArr[94] = col95;
        inputArr[95] = col96;
        inputArr[96] = col97;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87], arr[88], arr[89], arr[90], arr[91], arr[92], arr[93], arr[94], arr[95], arr[96]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING,col89 STRING,col90 STRING,col91 STRING,col92 STRING,col93 STRING,col94 STRING,col95 STRING,col96 STRING,col97 STRING,col98 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88, String col89, String col90, String col91, String col92, String col93, String col94, String col95, String col96, String col97, String col98) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[98];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        inputArr[88] = col89;
        inputArr[89] = col90;
        inputArr[90] = col91;
        inputArr[91] = col92;
        inputArr[92] = col93;
        inputArr[93] = col94;
        inputArr[94] = col95;
        inputArr[95] = col96;
        inputArr[96] = col97;
        inputArr[97] = col98;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87], arr[88], arr[89], arr[90], arr[91], arr[92], arr[93], arr[94], arr[95], arr[96], arr[97]));
    }

    @FunctionHint(output = @DataTypeHint("ROW<col1 STRING,col2 STRING,col3 STRING,col4 STRING,col5 STRING,col6 STRING,col7 STRING,col8 STRING,col9 STRING,col10 STRING,col11 STRING,col12 STRING,col13 STRING,col14 STRING,col15 STRING,col16 STRING,col17 STRING,col18 STRING,col19 STRING,col20 STRING,col21 STRING,col22 STRING,col23 STRING,col24 STRING,col25 STRING,col26 STRING,col27 STRING,col28 STRING,col29 STRING,col30 STRING,col31 STRING,col32 STRING,col33 STRING,col34 STRING,col35 STRING,col36 STRING,col37 STRING,col38 STRING,col39 STRING,col40 STRING,col41 STRING,col42 STRING,col43 STRING,col44 STRING,col45 STRING,col46 STRING,col47 STRING,col48 STRING,col49 STRING,col50 STRING,col51 STRING,col52 STRING,col53 STRING,col54 STRING,col55 STRING,col56 STRING,col57 STRING,col58 STRING,col59 STRING,col60 STRING,col61 STRING,col62 STRING,col63 STRING,col64 STRING,col65 STRING,col66 STRING,col67 STRING,col68 STRING,col69 STRING,col70 STRING,col71 STRING,col72 STRING,col73 STRING,col74 STRING,col75 STRING,col76 STRING,col77 STRING,col78 STRING,col79 STRING,col80 STRING,col81 STRING,col82 STRING,col83 STRING,col84 STRING,col85 STRING,col86 STRING,col87 STRING,col88 STRING,col89 STRING,col90 STRING,col91 STRING,col92 STRING,col93 STRING,col94 STRING,col95 STRING,col96 STRING,col97 STRING,col98 STRING,col99 STRING>"))
    public void eval(String json, String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8, String col9, String col10, String col11, String col12, String col13, String col14, String col15, String col16, String col17, String col18, String col19, String col20, String col21, String col22, String col23, String col24, String col25, String col26, String col27, String col28, String col29, String col30, String col31, String col32, String col33, String col34, String col35, String col36, String col37, String col38, String col39, String col40, String col41, String col42, String col43, String col44, String col45, String col46, String col47, String col48, String col49, String col50, String col51, String col52, String col53, String col54, String col55, String col56, String col57, String col58, String col59, String col60, String col61, String col62, String col63, String col64, String col65, String col66, String col67, String col68, String col69, String col70, String col71, String col72, String col73, String col74, String col75, String col76, String col77, String col78, String col79, String col80, String col81, String col82, String col83, String col84, String col85, String col86, String col87, String col88, String col89, String col90, String col91, String col92, String col93, String col94, String col95, String col96, String col97, String col98, String col99) {
        if (json == null || json.trim().length() == 0) {
            return;
        }
        String[] inputArr = new String[99];
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
        inputArr[10] = col11;
        inputArr[11] = col12;
        inputArr[12] = col13;
        inputArr[13] = col14;
        inputArr[14] = col15;
        inputArr[15] = col16;
        inputArr[16] = col17;
        inputArr[17] = col18;
        inputArr[18] = col19;
        inputArr[19] = col20;
        inputArr[20] = col21;
        inputArr[21] = col22;
        inputArr[22] = col23;
        inputArr[23] = col24;
        inputArr[24] = col25;
        inputArr[25] = col26;
        inputArr[26] = col27;
        inputArr[27] = col28;
        inputArr[28] = col29;
        inputArr[29] = col30;
        inputArr[30] = col31;
        inputArr[31] = col32;
        inputArr[32] = col33;
        inputArr[33] = col34;
        inputArr[34] = col35;
        inputArr[35] = col36;
        inputArr[36] = col37;
        inputArr[37] = col38;
        inputArr[38] = col39;
        inputArr[39] = col40;
        inputArr[40] = col41;
        inputArr[41] = col42;
        inputArr[42] = col43;
        inputArr[43] = col44;
        inputArr[44] = col45;
        inputArr[45] = col46;
        inputArr[46] = col47;
        inputArr[47] = col48;
        inputArr[48] = col49;
        inputArr[49] = col50;
        inputArr[50] = col51;
        inputArr[51] = col52;
        inputArr[52] = col53;
        inputArr[53] = col54;
        inputArr[54] = col55;
        inputArr[55] = col56;
        inputArr[56] = col57;
        inputArr[57] = col58;
        inputArr[58] = col59;
        inputArr[59] = col60;
        inputArr[60] = col61;
        inputArr[61] = col62;
        inputArr[62] = col63;
        inputArr[63] = col64;
        inputArr[64] = col65;
        inputArr[65] = col66;
        inputArr[66] = col67;
        inputArr[67] = col68;
        inputArr[68] = col69;
        inputArr[69] = col70;
        inputArr[70] = col71;
        inputArr[71] = col72;
        inputArr[72] = col73;
        inputArr[73] = col74;
        inputArr[74] = col75;
        inputArr[75] = col76;
        inputArr[76] = col77;
        inputArr[77] = col78;
        inputArr[78] = col79;
        inputArr[79] = col80;
        inputArr[80] = col81;
        inputArr[81] = col82;
        inputArr[82] = col83;
        inputArr[83] = col84;
        inputArr[84] = col85;
        inputArr[85] = col86;
        inputArr[86] = col87;
        inputArr[87] = col88;
        inputArr[88] = col89;
        inputArr[89] = col90;
        inputArr[90] = col91;
        inputArr[91] = col92;
        inputArr[92] = col93;
        inputArr[93] = col94;
        inputArr[94] = col95;
        inputArr[95] = col96;
        inputArr[96] = col97;
        inputArr[97] = col98;
        inputArr[98] = col99;
        String[] arr = getStrings(json, inputArr);
        collect(Row.of(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14], arr[15], arr[16], arr[17], arr[18], arr[19], arr[20], arr[21], arr[22], arr[23], arr[24], arr[25], arr[26], arr[27], arr[28], arr[29], arr[30], arr[31], arr[32], arr[33], arr[34], arr[35], arr[36], arr[37], arr[38], arr[39], arr[40], arr[41], arr[42], arr[43], arr[44], arr[45], arr[46], arr[47], arr[48], arr[49], arr[50], arr[51], arr[52], arr[53], arr[54], arr[55], arr[56], arr[57], arr[58], arr[59], arr[60], arr[61], arr[62], arr[63], arr[64], arr[65], arr[66], arr[67], arr[68], arr[69], arr[70], arr[71], arr[72], arr[73], arr[74], arr[75], arr[76], arr[77], arr[78], arr[79], arr[80], arr[81], arr[82], arr[83], arr[84], arr[85], arr[86], arr[87], arr[88], arr[89], arr[90], arr[91], arr[92], arr[93], arr[94], arr[95], arr[96], arr[97], arr[98]));
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

    /**
     * parse user columns from json and provider column name
     */
    private String[] getStrings(String[] json) {
        JsonObject jsonObject = new JsonParser().parse(json[0]).getAsJsonObject();
        int len = json.length - 1;
        String[] arr = new String[len];
        for (int i = 0; i < len - 1; ++i) {
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
