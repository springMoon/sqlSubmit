package com.rookie.submit.cust.connector.bigjdbc.enumerator;

import com.rookie.submit.cust.connector.bigjdbc.split.BigJdbcSplit;
import org.apache.flink.api.connector.source.Boundedness;
import org.apache.flink.api.connector.source.SplitEnumerator;
import org.apache.flink.api.connector.source.SplitEnumeratorContext;
import org.apache.flink.api.connector.source.SplitsAssignment;
import org.apache.flink.connector.kafka.source.split.KafkaPartitionSplit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

public class BigJdbcSourceEnumerator implements SplitEnumerator<BigJdbcSplit, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(BigJdbcSourceEnumerator.class);
    private final Set<BigJdbcSplit> assignedPartitions;
    private final SplitEnumeratorContext<BigJdbcSplit> context;
    /**
     * The discovered and initialized partition splits that are waiting for owner reader to be
     * ready.
     */
    private final Map<Integer, Set<BigJdbcSplit>> pendingPartitionSplitAssignment;

    // This flag will be marked as true if periodically partition discovery is disabled AND the
    // initializing partition discovery has finished.
    private boolean noMoreNewPartitionSplits = false;

    public BigJdbcSourceEnumerator(Set<BigJdbcSplit> assignedPartitions, SplitEnumeratorContext<BigJdbcSplit> context) {

        this.assignedPartitions = assignedPartitions;
        this.context = context;
        this.pendingPartitionSplitAssignment = new HashMap<>();
    }

    @Override
    public void start() {
        // load all split
        assignedPartitions.add(new BigJdbcSplit(0l, 1000l));
    }

    @Override
    public void handleSplitRequest(int i, @Nullable String s) {
        // do nothing
    }

    @Override
    public void addReader(int pendingReader) {
        LOG.debug(
                "Adding reader {} to BigJdbcSourceEnumerator.",
                pendingReader);

        checkReaderRegistered(pendingReader);

// Remove pending assignment for the reader
        final Set<BigJdbcSplit> pendingAssignmentForReader =
                pendingPartitionSplitAssignment.remove(pendingReader);

        Map<Integer, List<BigJdbcSplit>> incrementalAssignment = new HashMap<>();
        if (pendingAssignmentForReader != null && !pendingAssignmentForReader.isEmpty()) {
            // Put pending assignment into incremental assignment
            incrementalAssignment
                    .computeIfAbsent(pendingReader, (ignored) -> new ArrayList<>())
                    .addAll(pendingAssignmentForReader);

            // Mark pending partitions as already assigned
            pendingAssignmentForReader.forEach(
                    split -> assignedPartitions.add(new BigJdbcSplit(split.getSplitStart(), split.getSplitEnd())));
        }
        // Assign pending splits to readers
        if (!incrementalAssignment.isEmpty()) {
            LOG.info("Assigning splits to readers {}", incrementalAssignment);
            context.assignSplits(new SplitsAssignment<>(incrementalAssignment));
        }

        // If periodically partition discovery is disabled and the initializing discovery has done,
        // signal NoMoreSplitsEvent to pending readers
        if (noMoreNewPartitionSplits ) {
            LOG.debug(
                    "No more KafkaPartitionSplits to assign. Sending NoMoreSplitsEvent to reader {}",
                    pendingReader);

            context.signalNoMoreSplits(pendingReader);
        }

    }
    private void checkReaderRegistered(int readerId) {
        if (!context.registeredReaders().containsKey(readerId)) {
            throw new IllegalStateException(
                    String.format("Reader %d is not registered to source coordinator", readerId));
        }
    }

    @Override
    public BigJdbcSourceEnumeratorState snapshotState(long l) throws Exception {
        // snapshot
        return new BigJdbcSourceEnumeratorState(assignedPartitions);
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void addSplitsBack(List<BigJdbcSplit> splits, int subtaskId) {
       // do nothing
    }
}
