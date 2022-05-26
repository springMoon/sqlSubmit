package com.rookie.submit.cust.connector.hbase;

import org.apache.flink.connector.hbase.util.HBaseTableSchema;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.connector.source.LookupTableSource;
import org.apache.flink.table.connector.source.TableFunctionProvider;
import org.apache.flink.table.types.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class HbaseDynamicTableSource implements LookupTableSource {

    private final Logger LOG = LoggerFactory.getLogger(HbaseDynamicTableSource.class);

    private final DataType producedDataType;
    private final HbaseOption options;
    private final HBaseTableSchema hbaseSchema;

    public HbaseDynamicTableSource(

            DataType producedDataType,
            HbaseOption options,
            HBaseTableSchema hbaseSchema) {

        this.producedDataType = producedDataType;
        this.options = options;
        this.hbaseSchema = hbaseSchema;
    }

    @Override
    public DynamicTableSource copy() {
        return new HbaseDynamicTableSource(producedDataType, options, hbaseSchema);
    }

    @Override
    public String asSummaryString() {
        return "Hbase Table Source, support Lookup function";
    }

    @Override
    public LookupRuntimeProvider getLookupRuntimeProvider(LookupContext context) {

        HbaseRowDataLookUpFunction lookUpFunction = null;
        try {
            lookUpFunction = new HbaseRowDataLookUpFunction(hbaseSchema, options);
        } catch (UnsupportedEncodingException e) {
            LOG.error("table schema encoding must by UTF-8", e);
        }

        return TableFunctionProvider.of(lookUpFunction);
    }
}