package com.rookie.submit.udf;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.FunctionHint;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;
import org.apache.flink.types.RowKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParseDctJson extends TableFunction<Row> {

    private final static Logger LOG = LoggerFactory.getLogger(ParseDctJson.class);

    @FunctionHint(output = @DataTypeHint("ROW<arr ARRAY<STRING>>"))
    public void eval(String json) {
        if (json == null || json.length() == 0) {
            return;
        }
        String[] arr = getString(json);
        RowKind rowKind = RowKind.fromByteValue((byte) 0);
        Row row = new Row(rowKind, 1);
        row.setField(0, arr);
        collect(row);
    }

    /**
     * parse user columns from json and provider column name
     */
    private String[] getString(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

            JsonObject data = jsonObject.getAsJsonObject("data");
            if (data == null) {
                return null;
            }
            JsonElement rows = data.get("rows");
            if (rows == null) {
                return null;
            }
            JsonArray array = rows.getAsJsonArray();

            String[] result = new String[array.size()];

            for (int i = 0; i < result.length; i++) {

                JsonElement tmp = array.get(i);
                if (tmp == null || tmp.isJsonNull()) {
                    result[i] = null;
                } else {
                    result[i] = tmp.getAsString();
                }
            }
            return result;

        } catch (Exception e){
            LOG.warn("parse input error : " + json);
            e.printStackTrace();
        }

        return null;
    }
}
