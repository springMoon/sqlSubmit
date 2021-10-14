package com.rookie.submit.cust.source.mysql;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;

public class MysqlOption {

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
            .defaultValue(100l);
    public static final ConfigOption<Long> CACHE_EXPIRE_MS = ConfigOptions.key("mysql.lookup.cache.expire.ms")
            .longType()
            .defaultValue(1000l);
    public static final ConfigOption<Integer> MAX_RETRY_TIMES = ConfigOptions.key("mysql.lookup.max.retry.times")
            .intType()
            .defaultValue(3);

    public static final ConfigOption<Integer> TIME_OUT = ConfigOptions.key("mysql.timeout")
            .intType()
            .defaultValue(10);
}
