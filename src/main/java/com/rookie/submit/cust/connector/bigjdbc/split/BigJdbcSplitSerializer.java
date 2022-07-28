package com.rookie.submit.cust.connector.bigjdbc.split;

import org.apache.flink.connector.kafka.source.split.KafkaPartitionSplit;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.kafka.common.TopicPartition;

import java.io.*;

public class BigJdbcSplitSerializer implements SimpleVersionedSerializer<BigJdbcSplit> {
    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public byte[] serialize(BigJdbcSplit split) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             DataOutputStream out = new DataOutputStream(baos)) {
            out.writeLong(split.getSplitStart());
            out.writeLong(split.getSplitEnd());
            out.flush();
            return baos.toByteArray();
        }
    }

    @Override
    public BigJdbcSplit deserialize(int version, byte[] serialized) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(serialized);
             DataInputStream in = new DataInputStream(bais)) {
            long splitStart = in.readLong();
            long splitEnd = in.readLong();
            return new BigJdbcSplit(splitStart, splitEnd);
        }
    }
}
