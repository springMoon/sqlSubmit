package com.rookie.submit.cust.source.hbase;

import com.rookie.submit.cust.source.mysql.MysqlSource;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.connector.hbase.util.HBaseTableSchema;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.table.api.TableColumn;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.source.*;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.RowType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.apache.flink.table.types.utils.TypeConversions.fromDataTypeToLegacyInfo;

public class HbaseDynamicTableSource implements LookupTableSource {

    private final Logger LOG = LoggerFactory.getLogger(HbaseDynamicTableSource.class);

    private final DataType producedDataType;
    private final HbaseOption options;
    private final TableSchema physicalSchema;

    public HbaseDynamicTableSource(

            DataType producedDataType,
            HbaseOption options,
            TableSchema physicalSchema) {

        this.producedDataType = producedDataType;
        this.options = options;
        this.physicalSchema = physicalSchema;
    }

    @Override
    public DynamicTableSource copy() {
        return new HbaseDynamicTableSource(producedDataType, options, physicalSchema);
    }

    @Override
    public String asSummaryString() {
        return "Hbase Table Source, support Lookup function";
    }

    @Override
    public LookupRuntimeProvider getLookupRuntimeProvider(LookupContext context) {

        HBaseTableSchema hbaseSchema = HBaseTableSchema.fromTableSchema(physicalSchema);

        HbaseRowDataLookUpFunction lookUpFunction = null;
        try {
            lookUpFunction = new HbaseRowDataLookUpFunction(hbaseSchema, options);
        } catch (UnsupportedEncodingException e) {
            LOG.error("table schema encoding must by UTF-8", e);
        }

        return TableFunctionProvider.of(lookUpFunction);
    }
}