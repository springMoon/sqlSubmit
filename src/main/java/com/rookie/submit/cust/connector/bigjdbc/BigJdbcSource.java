//package com.rookie.submit.cust.connector.bigjdbc;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.rookie.submit.cust.connector.bigjdbc.reader.BigJdbcSourceEmitter;
//import com.rookie.submit.cust.connector.bigjdbc.reader.BigJdbcSourceReader;
//import com.rookie.submit.cust.connector.bigjdbc.reader.BigJdbcSourceSplitReader;
//import com.rookie.submit.cust.connector.bigjdbc.reader.fetch.BigJdbcSourceFetcherManager;
//import com.rookie.submit.cust.connector.bigjdbc.split.BigJdbcSplit;
//import org.apache.flink.api.common.typeinfo.TypeInformation;
//import org.apache.flink.api.connector.source.*;
//import org.apache.flink.api.java.typeutils.ResultTypeQueryable;
//import org.apache.flink.connector.base.source.reader.RecordsWithSplitIds;
//import org.apache.flink.connector.base.source.reader.splitreader.SplitReader;
//import org.apache.flink.connector.base.source.reader.synchronization.FutureCompletingBlockingQueue;
//import org.apache.flink.connector.kafka.source.enumerator.KafkaSourceEnumState;
//import org.apache.flink.connector.kafka.source.reader.fetcher.KafkaSourceFetcherManager;
//import org.apache.flink.core.io.SimpleVersionedSerializer;
//
//import java.util.function.Supplier;
//
// todo
//public class BigJdbcSource<OUT> implements Source<OUT, BigJdbcSplit, KafkaSourceEnumState>,
//        ResultTypeQueryable<OUT> {
//
//    private final String url;
//    private final String user;
//    private final String pass;
//    private final String sql;
//
//    public BigJdbcSource(String url, String user, String pass, String sql) {
//        this.url = url;
//        this.user = user;
//        this.pass = pass;
//        this.sql = sql;
//    }
//
//    @Override
//    public Boundedness getBoundedness() {
//        return Boundedness.BOUNDED;
//    }
//
//    @Override
//    public SourceReader<OUT, BigJdbcSplit> createReader(SourceReaderContext readerContext) throws Exception {
//        FutureCompletingBlockingQueue<RecordsWithSplitIds<JsonObject>>
//                elementsQueue = new FutureCompletingBlockingQueue<>();
//
//        Supplier<BigJdbcSourceSplitReader> splitReaderSupplier =
//                () -> new BigJdbcSourceSplitReader(url,user,pass,sql);
////        BigJdbcSourceSplitReader  bigJdbcSourceSplitReader = new BigJdbcSourceSplitReader(url,user,pass,sql);
//
//
////        Supplier<BigJdbcSourceSplitReader<JsonElement,
////                BigJdbcSplit>> splitReaderSupplierx = splitReaderSupplier::get;
//        BigJdbcSourceFetcherManager fetcherManager = new BigJdbcSourceFetcherManager(
//                elementsQueue, splitReaderSupplier::get);
//
//        BigJdbcSourceEmitter<OUT> recordEmitter = new BigJdbcSourceEmitter<>();
////        return new BigJdbcSourceReader<>(
////                elementsQueue,
////                fetcherManager,
////                recordEmitter,
////                toConfiguration(props),
////                readerContext,
////                kafkaSourceReaderMetrics);
//        return null;
//    }
//
//    @Override
//    public SplitEnumerator<BigJdbcSplit, KafkaSourceEnumState> createEnumerator(SplitEnumeratorContext<BigJdbcSplit> enumContext) throws Exception {
//        return null;
//    }
//
//    @Override
//    public SplitEnumerator<BigJdbcSplit, KafkaSourceEnumState> restoreEnumerator(SplitEnumeratorContext<BigJdbcSplit> enumContext, KafkaSourceEnumState checkpoint) throws Exception {
//        return null;
//    }
//
//    @Override
//    public SimpleVersionedSerializer<BigJdbcSplit> getSplitSerializer() {
//        return null;
//    }
//
//    @Override
//    public SimpleVersionedSerializer<KafkaSourceEnumState> getEnumeratorCheckpointSerializer() {
//        return null;
//    }
//
//    @Override
//    public TypeInformation<OUT> getProducedType() {
//        return null;
//    }
//}
