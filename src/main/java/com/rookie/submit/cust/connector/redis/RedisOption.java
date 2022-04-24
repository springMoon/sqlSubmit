package com.rookie.submit.cust.connector.redis;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.connector.hbase.options.HBaseLookupOptions;

import java.io.Serializable;
import java.util.Objects;

/**
 * mysql lookup require option
 */
public class RedisOption implements Serializable {

    public static final ConfigOption<String> REDIS_URL = ConfigOptions.key("redis.url")
            .stringType()
            .noDefaultValue();

    public static final ConfigOption<String> PASSWORD = ConfigOptions.key("pass")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<String> TYPE = ConfigOptions.key("key.type")
            .stringType()
            .noDefaultValue();
    public static final ConfigOption<Long> CACHE_MAX_SIZE = ConfigOptions.key("redis.lookup.cache.max.size")
            .longType()
            .defaultValue(-1l);
    public static final ConfigOption<Long> CACHE_EXPIRE_MS = ConfigOptions.key("redis.lookup.cache.expire.ms")
            .longType()
            .defaultValue(-1l);
    public static final ConfigOption<Integer> MAX_RETRY_TIMES = ConfigOptions.key("redis.lookup.max.retry.times")
            .intType()
            .defaultValue(3);

    public static final ConfigOption<Integer> TIME_OUT = ConfigOptions.key("mysql.timeout")
            .intType()

            .defaultValue(10);
    private static final int DEFAULT_MAX_RETRY_TIMES = 3;

    private final String url;
    private final String type;
    private final String password;
    private final long cacheMaxSize;
    private final long cacheExpireMs;
    private final int maxRetryTimes;
    private final boolean lookupAsync;

    // second
    private final int timeOut;

    public RedisOption(String url, String type, String password,
                       long cacheMaxSize, long cacheExpireMs, int maxRetryTimes, boolean lookupAsync, int timeOut) {
        this.url = url;
        this.type = type;
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

    public String getType(){
        return type;
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
        if (o instanceof RedisOption) {
            RedisOption options = (RedisOption) o;
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
        private String type;
        private String password;
        private long cacheMaxSize = -1l;
        private long cacheExpireMs = -1l;
        private int maxRetryTimes = DEFAULT_MAX_RETRY_TIMES;
        private boolean lookupAsync = false;
        private int timeOut = 60;


        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
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

        public RedisOption build() {
            return new RedisOption(url, type, password, cacheMaxSize, cacheExpireMs, maxRetryTimes, lookupAsync, timeOut);
        }
    }
}
