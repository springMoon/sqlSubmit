package com.rookie.submit.cust.source.http;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.SimpleCounter;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.table.data.RowData;

/**
 * http source
 */
public class HttpSource extends RichSourceFunction<RowData> {

    private volatile boolean isRunning = true;
    private String url;
    private long requestInterval;
    private DeserializationSchema<RowData> deserializer;
    private transient Counter counter;

    public HttpSource(String url, long requestInterval, DeserializationSchema<RowData> deserializer) {
        this.url = url;
        this.requestInterval = requestInterval;
        this.deserializer = deserializer;
    }

    @Override
    public void open(Configuration parameters) throws Exception {

        counter = new SimpleCounter();
        this.counter = getRuntimeContext()
                .getMetricGroup()
                .counter("myCounter");

    }

    @Override
    public void run(SourceContext<RowData> ctx) throws Exception {
        while (isRunning) {
            try {
                String messge = HttpClientUtil.doGet(url);

                // deserializer messge, http ignore
                ctx.collect(deserializer.deserialize(messge.getBytes()));
                this.counter.inc();

                Thread.sleep(requestInterval);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}
