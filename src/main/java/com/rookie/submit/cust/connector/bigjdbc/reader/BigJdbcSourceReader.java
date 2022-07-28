//package com.rookie.submit.cust.connector.bigjdbc.reader;
//
//import com.rookie.submit.cust.connector.bigjdbc.split.BigJdbcSplit;
//import com.rookie.submit.cust.connector.bigjdbc.split.BigJdbcSplitState;
//import org.apache.flink.api.connector.source.SourceReaderContext;
//import org.apache.flink.configuration.Configuration;
//import org.apache.flink.connector.base.source.reader.RecordEmitter;
//import org.apache.flink.connector.base.source.reader.RecordsWithSplitIds;
//import org.apache.flink.connector.base.source.reader.SingleThreadMultiplexSourceReaderBase;
//import org.apache.flink.connector.base.source.reader.splitreader.SplitReader;
//import org.apache.flink.connector.base.source.reader.synchronization.FutureCompletingBlockingQueue;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.function.Supplier;
//
//public class BigJdbcSourceReader<T> extends SingleThreadMultiplexSourceReaderBase<Object, T, BigJdbcSplit, BigJdbcSplitState> {
//
//
//    private final ConcurrentMap<Long, Long> offsetsOfFinishedSplits;
//
//    public BigJdbcSourceReader(FutureCompletingBlockingQueue<RecordsWithSplitIds<ConsumerRecord<byte[], byte[]>>>
//                                       elementsQueue,
//                               ){
//        super(elementsQueue, kafkaSourceFetcherManager, recordEmitter, config, context);
//        this.offsetsOfFinishedSplits = new ConcurrentHashMap();
//    }
//
//    @Override
//    protected void onSplitFinished(Map<String, BigJdbcSplitState> finishedSplitIds) {
//        finishedSplitIds.forEach(
//                (ignored, splitState) -> {
//                    offsetsOfFinishedSplits.put(
//                            splitState.getSplitStart(), splitState.getSplitEnd());
//                }
//        );
//    }
//
//    @Override
//    protected BigJdbcSplitState initializedState(BigJdbcSplit split) {
//         return new BigJdbcSplitState(split);
//    }
//
//    @Override
//    protected BigJdbcSplit toSplitType(String splitId, BigJdbcSplitState splitState) {
//        return splitState.toBigJdbcSplit();
//    }
//}
