package com.rookie.submit.cust.source.mysql;

import com.rookie.submit.cust.source.base.RowDataConverterBase;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.connector.jdbc.internal.options.JdbcLookupOptions;
import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.SimpleCounter;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.connector.source.LookupTableSource;
import org.apache.flink.table.connector.source.SourceFunctionProvider;
import org.apache.flink.table.connector.source.TableFunctionProvider;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.RowType;
import org.apache.flink.types.RowKind;
import scala.math.Ordering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import static org.apache.flink.table.types.utils.TypeConversions.fromDataTypeToLegacyInfo;

/**
 * mysql table source
 * query mysql data
 */
public class MysqlSource extends RichSourceFunction<RowData>  {

    private volatile boolean isRunning = true;
    private final String url;
    private final String username;
    private final String password;
    private final String database;
    private final String table;
    //    private DeserializationSchema<RowData> deserializer;
    private transient Counter counter;
    private transient Connection conn;
    private final DataType producedDataType;
    private final String[] fieldNames;
    private final int fieldCount;
    private final RowType rowType;
    private MysqlLookupOption lookupOption;
    private final ReadableConfig options;

    public MysqlSource(String url, String username, String password, String database, String table, DataType producedDataType, ReadableConfig options) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.database = database;
        this.table = table;
        this.producedDataType = producedDataType;
        rowType = (RowType) producedDataType.getLogicalType();

        fieldCount = rowType.getFieldCount();
        List<String> nameList = rowType.getFieldNames();
        fieldNames = nameList.toArray(new String[fieldCount]);
        this.options = options;

    }

    @Override
    public void open(Configuration parameters) throws Exception {

        counter = new SimpleCounter();
        this.counter = getRuntimeContext()
                .getMetricGroup()
                .counter("myCounter");

        // jdbc connection
        conn = DriverManager.getConnection(this.url, this.username, this.password);

    }

    @Override
    public void run(SourceContext<RowData> ctx) throws Exception {

        // splicing query sql
        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        for (int i = 0; i < fieldCount; i++) {
            if (i == fieldCount - 1) {
                builder.append(fieldNames[i]).append(" ");
            } else {
                builder.append(fieldNames[i]).append(",");
            }
        }
        builder.append("from ");
        builder.append(database).append(".");
        builder.append(table);
        String sql = builder.toString();

        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        // loop result set
        while (isRunning && resultSet.next()) {

            GenericRowData result = new GenericRowData(fieldCount);
            result.setRowKind(RowKind.INSERT);
            for (int i = 0; i < fieldCount; i++) {
                LogicalType type = rowType.getTypeAt(i);
                String value = resultSet.getString(i + 1);
                Object fieldValue = RowDataConverterBase.createConverter(type, value);

                result.setField(i, fieldValue);
                // out result
            }
            ctx.collect(result);
            this.counter.inc();
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }

}
