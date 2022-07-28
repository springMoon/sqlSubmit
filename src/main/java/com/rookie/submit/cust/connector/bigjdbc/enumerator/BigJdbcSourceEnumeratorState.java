package com.rookie.submit.cust.connector.bigjdbc.enumerator;

import com.rookie.submit.cust.connector.bigjdbc.split.BigJdbcSplit;

import java.util.Set;

public class BigJdbcSourceEnumeratorState {

    private final Set<BigJdbcSplit> assignedPartitions;

    public BigJdbcSourceEnumeratorState(Set<BigJdbcSplit> assignedPartitions) {
        this.assignedPartitions = assignedPartitions;
    }

    public Set<BigJdbcSplit> assignedPartitions() {
        return assignedPartitions;
    }
}
