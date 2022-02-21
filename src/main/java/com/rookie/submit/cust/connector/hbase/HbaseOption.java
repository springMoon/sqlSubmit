package com.rookie.submit.cust.connector.hbase;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.connector.hbase.options.HBaseLookupOptions;

import java.io.Serializable;
import java.util.Objects;

/**
 * mysql lookup require option
 */
public class HbaseOption implements Serializable {

    public static final ConfigOption<String> ZOOKEEPER_QUORUM = ConfigOptions.key("hbase.zookeeper.quorum")
            .stringType()
            .noDefaultValue();

    public static final ConfigOption<String> ZOOKEEPER_ZNODE_PARENT = ConfigOptions.key("zookeeper.znode.parent")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<String> NULL_STRING_LITERAL = ConfigOptions.key("hbase.null-string-literal")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<String> TABLE = ConfigOptions.key("hbase.tablename")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<String> LOOKUP_KEY = ConfigOptions.key("hbase.lookup.key")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<Long> CACHE_MAX_SIZE = ConfigOptions.key("hbase.lookup.cache.max.size")
            .longType()
            .defaultValue(100l);
    public static final ConfigOption<Long> CACHE_EXPIRE_MS = ConfigOptions.key("hbase.lookup.cache.expire.ms")
            .longType()
            .defaultValue(1000l);
    public static final ConfigOption<Integer> MAX_RETRY_TIMES = ConfigOptions.key("hbase.lookup.max.retry.times")
            .intType()
            .defaultValue(3);

    public static final ConfigOption<Integer> TIME_OUT = ConfigOptions.key("hbase.timeout")
            .intType()

            .defaultValue(10);
    private static final int DEFAULT_MAX_RETRY_TIMES = 3;

    private String zookeeperQuorum = "localhost:2181";
    private String zookeeperZnodeParent = "/hbase";
    private String nullStringLiteral = "null";
    private final String tableName;
    private String lookupKey;
    private long cacheMaxSize;
    private final long cacheExpireMs;
    private final int maxRetryTimes;
    private final boolean lookupAsync;

    // second
    private final int timeOut;

    public HbaseOption(String zookeeperQuorum, String zookeeperZnodeParent, String nullStringLiteral, String tableName, String lookupKey,
                       long cacheMaxSize, long cacheExpireMs, int maxRetryTimes, boolean lookupAsync, int timeOut) {
        this.zookeeperQuorum = zookeeperQuorum;
        this.zookeeperZnodeParent = zookeeperZnodeParent;
        this.nullStringLiteral = nullStringLiteral;
        this.tableName = tableName;
        this.lookupKey = lookupKey;
        this.cacheMaxSize = cacheMaxSize;
        this.cacheExpireMs = cacheExpireMs;
        this.maxRetryTimes = maxRetryTimes;
        this.lookupAsync = lookupAsync;
        this.timeOut = timeOut;
    }

    public String getZookeeperQuorum() {
        return zookeeperQuorum;
    }

    public String getZookeeperZnodeParent() {
        return zookeeperZnodeParent;
    }

    public String getNullStringLiteral() {
        return nullStringLiteral;
    }

    public String getTableName() {
        return tableName;
    }

    public boolean isLookupAsync() {
        return lookupAsync;
    }

    public long getCacheMaxSize() {
        return cacheMaxSize;
    }

    public long getCacheExpireMs() {
        return cacheExpireMs;
    }

    public int getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public String getLookupKey() {
        return lookupKey;
    }

    public boolean getLookupAsync() {
        return lookupAsync;
    }

    public static HBaseLookupOptions.Builder builder() {
        return new HBaseLookupOptions.Builder();
    }

    public int getTimeOut() {
        return timeOut;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof HbaseOption) {
            HbaseOption options = (HbaseOption) o;
            return Objects.equals(cacheMaxSize, options.cacheMaxSize)
                    && Objects.equals(cacheExpireMs, options.cacheExpireMs)
                    && Objects.equals(maxRetryTimes, options.maxRetryTimes)
                    && Objects.equals(lookupAsync, options.lookupAsync);
        } else {
            return false;
        }
    }

    /**
     * Builder of {@link HBaseLookupOptions}.
     */
    public static class Builder {
        private String zookeeperQuorum;
        private String zookeeperZnodeParent;
        private String nullStringLiteral;
        private String table;
        private String lookupKey;
        private long cacheMaxSize = -1L;
        private long cacheExpireMs = 0L;
        private int maxRetryTimes = DEFAULT_MAX_RETRY_TIMES;
        private boolean lookupAsync = false;
        private int timeOut = 60;


        public Builder setZookeeperQuorum(String zookeeperQuorum) {
            this.zookeeperQuorum = zookeeperQuorum;
            return this;
        }

        public Builder setZookeeperZnodeParent(String zookeeperZnodeParent) {
            this.zookeeperZnodeParent = zookeeperZnodeParent;
            return this;
        }

        public Builder setNullStringLiteral(String nullStringLiteral) {
            this.nullStringLiteral = nullStringLiteral;
            return this;
        }

        public Builder setTable(String table) {
            this.table = table;
            return this;
        }

        public Builder setLookupKey(String lookupKey) {
            this.lookupKey = lookupKey;
            return this;
        }

        /**
         * optional, lookup cache max size, over this value, the old data will be eliminated.
         */
        public Builder setCacheMaxSize(long cacheMaxSize) {
            this.cacheMaxSize = cacheMaxSize;
            return this;
        }

        /**
         * optional, lookup cache expire mills, over this time, the old data will expire.
         */
        public Builder setCacheExpireMs(long cacheExpireMs) {
            this.cacheExpireMs = cacheExpireMs;
            return this;
        }

        /**
         * optional, max retry times for Hbase connector.
         */
        public Builder setMaxRetryTimes(int maxRetryTimes) {
            this.maxRetryTimes = maxRetryTimes;
            return this;
        }

        /**
         * optional, whether to set async lookup.
         */
        public Builder setLookupAsync(boolean lookupAsync) {
            this.lookupAsync = lookupAsync;
            return this;
        }

        public Builder setTimeOut(int timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        public HbaseOption build() {
            return new HbaseOption(zookeeperQuorum, zookeeperZnodeParent, nullStringLiteral, table, lookupKey, cacheMaxSize, cacheExpireMs, maxRetryTimes, lookupAsync, timeOut);
        }
    }
}
