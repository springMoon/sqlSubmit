package com.rookie.submit.cust.connector.bigjdbc.reader.fetch;

import com.google.gson.JsonElement;
import com.rookie.submit.cust.connector.bigjdbc.split.BigJdbcSplit;
import org.apache.flink.connector.base.source.reader.RecordsWithSplitIds;
import org.apache.flink.connector.base.source.reader.fetcher.SingleThreadFetcherManager;
import org.apache.flink.connector.base.source.reader.splitreader.SplitReader;
import org.apache.flink.connector.base.source.reader.synchronization.FutureCompletingBlockingQueue;

import java.util.function.Supplier;

public class BigJdbcSourceFetcherManager extends SingleThreadFetcherManager<JsonElement, BigJdbcSplit> {
    public BigJdbcSourceFetcherManager(FutureCompletingBlockingQueue<RecordsWithSplitIds<JsonElement>> elementsQueue,
                                       Supplier<SplitReader<JsonElement,
                                             BigJdbcSplit>> splitReaderSupplier) {
        super(elementsQueue, splitReaderSupplier);
    }
}

