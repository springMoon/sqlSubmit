package com.rookie.submit.cust.connector.bigjdbc.split;

import org.apache.flink.connector.kafka.source.split.KafkaPartitionSplit;

public class BigJdbcSplitState extends BigJdbcSplit{
    public BigJdbcSplitState(BigJdbcSplit split) {
        super(split.getSplitStart(), split.getSplitEnd());
    }

    public BigJdbcSplit toBigJdbcSplit() {
        return new BigJdbcSplit(getSplitStart(), getSplitEnd());
    }
}
