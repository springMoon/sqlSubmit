package com.rookie.submit.cust.source.mysql;

import org.apache.flink.connector.hbase.options.HBaseLookupOptions;

import java.util.Objects;

/**
 * mysql lookup require option
 */
public class MysqlLookupOption {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_MAX_RETRY_TIMES = 3;

    private final long cacheMaxSize;
    private final long cacheExpireMs;
    private final int maxRetryTimes;
    private final boolean lookupAsync;

    // second
    private final int timeOut;


    public MysqlLookupOption(
            long cacheMaxSize, long cacheExpireMs, int maxRetryTimes, boolean lookupAsync, int timeOut) {
        this.cacheMaxSize = cacheMaxSize;
        this.cacheExpireMs = cacheExpireMs;
        this.maxRetryTimes = maxRetryTimes;
        this.lookupAsync = lookupAsync;
        this.timeOut = timeOut;
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
        if (o instanceof MysqlLookupOption) {
            MysqlLookupOption options = (MysqlLookupOption) o;
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
        private long cacheMaxSize = -1L;
        private long cacheExpireMs = 0L;
        private int maxRetryTimes = DEFAULT_MAX_RETRY_TIMES;
        private boolean lookupAsync = false;
        private int timeOut = 60;

        /**
         * optional, lookup cache max size, over this value, the old data will be eliminated.
         */
        public MysqlLookupOption.Builder setCacheMaxSize(long cacheMaxSize) {
            this.cacheMaxSize = cacheMaxSize;
            return this;
        }

        /**
         * optional, lookup cache expire mills, over this time, the old data will expire.
         */
        public MysqlLookupOption.Builder setCacheExpireMs(long cacheExpireMs) {
            this.cacheExpireMs = cacheExpireMs;
            return this;
        }

        /**
         * optional, max retry times for Hbase connector.
         */
        public MysqlLookupOption.Builder setMaxRetryTimes(int maxRetryTimes) {
            this.maxRetryTimes = maxRetryTimes;
            return this;
        }

        /**
         * optional, whether to set async lookup.
         */
        public MysqlLookupOption.Builder setLookupAsync(boolean lookupAsync) {
            this.lookupAsync = lookupAsync;
            return this;
        }

        private MysqlLookupOption.Builder setTimeOut(int timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        public MysqlLookupOption build() {
            return new MysqlLookupOption(cacheMaxSize, cacheExpireMs, maxRetryTimes, lookupAsync, timeOut);
        }
    }
}
