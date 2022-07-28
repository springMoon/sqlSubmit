package com.rookie.submit.cust.connector.bigjdbc.split;

import org.apache.flink.api.connector.source.SourceSplit;

import java.io.Serializable;

public class BigJdbcSplit implements SourceSplit, Serializable {


    // split start, include
    private final Long splitStart;
    // split end,
    private final Long splitEnd;

    public BigJdbcSplit(Long splitStart, Long splitEnd) {
        this.splitStart = splitStart;
        this.splitEnd = splitEnd;
    }

    public Long getSplitStart() {
        return splitStart;
    }

    public Long getSplitEnd() {
        return splitEnd;
    }

    @Override
    public String splitId() {
        return splitStart + "_" + splitEnd;
    }
}
