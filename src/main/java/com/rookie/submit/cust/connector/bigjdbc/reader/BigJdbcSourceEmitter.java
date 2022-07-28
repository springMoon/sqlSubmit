package com.rookie.submit.cust.connector.bigjdbc.reader;

import com.google.gson.JsonObject;
import com.rookie.submit.cust.connector.bigjdbc.split.BigJdbcSplitState;
import org.apache.flink.api.connector.source.SourceOutput;
import org.apache.flink.connector.base.source.reader.RecordEmitter;

public class BigJdbcSourceEmitter<T> implements RecordEmitter<JsonObject, JsonObject, BigJdbcSplitState> {

    @Override
    public void emitRecord(JsonObject element, SourceOutput<JsonObject> output, BigJdbcSplitState splitState) throws Exception {

        output.collect(element);

    }
}
