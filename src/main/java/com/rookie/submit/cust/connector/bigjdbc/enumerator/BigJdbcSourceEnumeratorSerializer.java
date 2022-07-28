package com.rookie.submit.cust.connector.bigjdbc.enumerator;

import com.rookie.submit.cust.connector.bigjdbc.split.BigJdbcSplit;
import org.apache.flink.connector.kafka.source.enumerator.KafkaSourceEnumState;
import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.kafka.common.TopicPartition;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class BigJdbcSourceEnumeratorSerializer implements SimpleVersionedSerializer<BigJdbcSourceEnumeratorState>
{

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public byte[] serialize(BigJdbcSourceEnumeratorState state) throws IOException {
        Set<BigJdbcSplit> splitSet = state.assignedPartitions();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             DataOutputStream out = new DataOutputStream(baos)) {

            out.writeInt(splitSet.size());
            for (BigJdbcSplit split : splitSet) {
                out.writeLong(split.getSplitStart());
                out.writeLong(split.getSplitEnd());
            }
            out.flush();
            return baos.toByteArray();
        }
    }

    @Override
    public BigJdbcSourceEnumeratorState deserialize(int version, byte[] serialized) throws IOException {
        Set<BigJdbcSplit> splitSet;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(serialized);
             DataInputStream in = new DataInputStream(bais)) {

            final int numPartitions = in.readInt();
            splitSet = new HashSet<>(numPartitions);
            for (int i = 0; i < numPartitions; i++) {
                final long splitStart = in.readLong();
                final long splitEnd = in.readLong();
                splitSet.add(new BigJdbcSplit(splitStart, splitEnd));
            }
            if (in.available() > 0) {
                throw new IOException("Unexpected trailing bytes in serialized topic partitions");
            }
        }
        return new BigJdbcSourceEnumeratorState(splitSet);

    }
}
