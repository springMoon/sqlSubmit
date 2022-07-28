package com.rookie.submit.cust.connector.bigjdbc.reader;

import com.google.gson.JsonObject;
import com.rookie.submit.cust.connector.bigjdbc.split.BigJdbcSplit;
import org.apache.flink.connector.base.source.reader.RecordsWithSplitIds;
import org.apache.flink.connector.base.source.reader.splitreader.SplitReader;
import org.apache.flink.connector.base.source.reader.splitreader.SplitsChange;
import org.apache.flink.util.Preconditions;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class BigJdbcSourceSplitReader implements SplitReader<JsonObject, BigJdbcSplit> {

    private final String url;
    private final String user;
    private final String pass;
    private final String sql;

    public BigJdbcSourceSplitReader(String url, String user, String pass, String sql) {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.sql = sql;

    }

    @Override
    public RecordsWithSplitIds<JsonObject> fetch() throws IOException {
        // query mysql
        ResultSet resultSet = null;
        try {
            Connection connection = DriverManager.getConnection(url,user,pass);
            PreparedStatement ps = connection.prepareStatement(sql);

            resultSet = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        BigJdbcSplitRecord bigJdbcSplitRecord = new BigJdbcSplitRecord(resultSet);

        return bigJdbcSplitRecord;
    }

    @Override
    public void handleSplitsChanges(SplitsChange<BigJdbcSplit> splitsChanges) {

    }

    @Override
    public void wakeUp() {

    }

    @Override
    public void close() throws Exception {

    }

    private static class BigJdbcSplitRecord
            implements RecordsWithSplitIds<JsonObject> {

        private final ResultSet consumerRecords;
        private final Set<String> finishedSplits = new HashSet<>();

        public BigJdbcSplitRecord(ResultSet consumerRecords) {
            this.consumerRecords = consumerRecords;
        }

        private void addFinishedSplit(String splitId) {
            finishedSplits.add(splitId);
        }

        @Nullable
        @Override
        public String nextSplit() {
            // no next
            return null;
        }

        @Nullable
        @Override
        public JsonObject nextRecordFromSplit() {
            Preconditions.checkNotNull(
                    "Make sure nextSplit() did not return null before "
                            + "iterate over the records split.");
            try {
                if (consumerRecords.next()) {
                    // load data
                    ResultSetMetaData meta = consumerRecords.getMetaData();
                    int columnCount = consumerRecords.getMetaData().getColumnCount();
                    JsonObject record = new JsonObject();
                    for(int i=0; i< columnCount ; ++i){
                        String name = meta.getColumnName(i);
                        //TODO meta.getColumnType(i)
                        // type
                        String value = consumerRecords.getString(i);
                        record.addProperty(name, value);
                    }
                    // Only emit records before stopping offset
                    return record;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Set<String> finishedSplits() {
            return finishedSplits;
        }
    }
}
