package com.rookie.submit.cust.connector.mysql;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.connector.hbase.options.HBaseLookupOptions;

import java.io.Serializable;
import java.util.Objects;

/**
 * mysql lookup require option
 */
public class MysqlOption implements Serializable {

    public static final ConfigOption<String> URL = ConfigOptions.key("mysql.url")
            .stringType()
            .noDefaultValue();

    public static final ConfigOption<String> USERNAME = ConfigOptions.key("mysql.username")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<String> PASSWORD = ConfigOptions.key("mysql.password")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<String> DATABASE = ConfigOptions.key("mysql.database")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<String> TABLE = ConfigOptions.key("mysql.table")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<Long> CACHE_MAX_SIZE = ConfigOptions.key("mysql.lookup.cache.max.size")
            .longType()
            .defaultValue(-1l);
    public static final ConfigOption<Long> CACHE_EXPIRE_MS = ConfigOptions.key("mysql.lookup.cache.expire.ms")
            .longType()
            .defaultValue(-1l);
    public static final ConfigOption<Integer> MAX_RETRY_TIMES = ConfigOptions.key("mysql.lookup.max.retry.times")
            .intType()
            .defaultValue(3);

    public static final ConfigOption<Integer> TIME_OUT = ConfigOptions.key("mysql.timeout")
            .intType()

            .defaultValue(10);
    private static final int DEFAULT_MAX_RETRY_TIMES = 3;

    private final String url;
    private final String database;
    private final String table;
    private final String username;
    private final String password;
    private final long cacheMaxSize;
    private final long cacheExpireMs;
    private final int maxRetryTimes;
    private final boolean lookupAsync;

    // second
    private final int timeOut;

    public MysqlOption(String url, String database, String table, String username, String password,
                       long cacheMaxSize, long cacheExpireMs, int maxRetryTimes, boolean lookupAsync, int timeOut) {
        this.url = url;
        this.database = database;
        this.table = table;
        this.username = username;
        this.password = password;
        this.cacheMaxSize = cacheMaxSize;
        this.cacheExpireMs = cacheExpireMs;
        this.maxRetryTimes = maxRetryTimes;
        this.lookupAsync = lookupAsync;
        this.timeOut = timeOut;
    }

    public String getUrl() {
        return url;
    }

    public String getDatabase() {
        return database;
    }

    public String getTable() {
        return table;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
        if (o instanceof MysqlOption) {
            MysqlOption options = (MysqlOption) o;
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
        private String url;
        private String database;
        private String table;
        private String username;
        private String password;
        private long cacheMaxSize = -1l;
        private long cacheExpireMs = -1l;
        private int maxRetryTimes = DEFAULT_MAX_RETRY_TIMES;
        private boolean lookupAsync = false;
        private int timeOut = 60;


        public MysqlOption.Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public MysqlOption.Builder setDatabase(String database) {
            this.database = database;
            return this;
        }

        public MysqlOption.Builder setTable(String table) {
            this.table = table;
            return this;
        }

        public MysqlOption.Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public MysqlOption.Builder setPassword(String password) {
            this.password = password;
            return this;

        }

        /**
         * optional, lookup cache max size, over this value, the old data will be eliminated.
         */
        public MysqlOption.Builder setCacheMaxSize(long cacheMaxSize) {
            this.cacheMaxSize = cacheMaxSize;
            return this;
        }

        /**
         * optional, lookup cache expire mills, over this time, the old data will expire.
         */
        public MysqlOption.Builder setCacheExpireMs(long cacheExpireMs) {
            this.cacheExpireMs = cacheExpireMs;
            return this;
        }

        /**
         * optional, max retry times for Hbase connector.
         */
        public MysqlOption.Builder setMaxRetryTimes(int maxRetryTimes) {
            this.maxRetryTimes = maxRetryTimes;
            return this;
        }

        /**
         * optional, whether to set async lookup.
         */
        public MysqlOption.Builder setLookupAsync(boolean lookupAsync) {
            this.lookupAsync = lookupAsync;
            return this;
        }

        public MysqlOption.Builder setTimeOut(int timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        public MysqlOption build() {
            return new MysqlOption(url, database, table, username, password, cacheMaxSize, cacheExpireMs, maxRetryTimes, lookupAsync, timeOut);
        }
    }
}
