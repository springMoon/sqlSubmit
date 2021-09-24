package com.rookie.submit.source;

import org.apache.flink.api.connector.source.*;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

/**
 * customer http source
 */
public class CustHttpSource extends RichSourceFunction<String> {

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void run(SourceContext<String> ctx) throws Exception {

    }

    @Override
    public void cancel() {

    }
}
