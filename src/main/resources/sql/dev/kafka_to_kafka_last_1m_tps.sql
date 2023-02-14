-- kafka source
drop table if exists user_log;
CREATE TABLE user_log
(
    `event_time`   TIMESTAMP(3) METADATA FROM 'timestamp' VIRTUAL, -- from Debezium format
    `partition_id` BIGINT METADATA FROM 'partition' VIRTUAL,       -- from Kafka connector
    `offset`       BIGINT METADATA VIRTUAL,                        -- from Kafka connector
    user_id        VARCHAR,
    item_id        VARCHAR,
    category_id    VARCHAR,
    behavior       VARCHAR,
    ts             TIMESTAMP(3),
    WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log_1'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );


-- set table.sql-dialect=hive;
-- kafka sink
drop table if exists user_log_sink;
CREATE TABLE user_log_sink
(
    window_start STRING,
    window_end   STRING,
    tps          DECIMAL(26, 6)
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log_tps'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );


-- 每10秒计算最近1分钟的 TPS
-- insert into user_log_sink
-- select date_format(window_start, 'yyyy-MM-dd HH:mm:ss'), date_format(window_end, 'yyyy-MM-dd HH:mm:ss'), count(user_id)/60.0 tps
-- FROM TABLE(
--         HOP(TABLE user_log, DESCRIPTOR(ts), INTERVAL '10' SECOND, INTERVAL '60' SECOND))
-- group by window_start, window_end
-- ;

-- 整点计算最近 1 分钟的 TPS
drop table if exists user_log_sink_1;
CREATE TABLE user_log_sink_1
(
    window_start STRING,
    window_end   STRING,
    ts_min       STRING,
    ts_max       STRING,
    tps          DECIMAL(26, 6)
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log_tps'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );

-- flink sql 竟然没有date_add 函数，官网的日期函数都是些什么啊
-- 为了方便测试，这个每 10 分钟计算一次
-- insert into user_log_sink_1
-- select window_start, window_end, ts_min, ts_max, tps
-- from(
--         select date_format(window_start, 'yyyy-MM-dd HH:mm:ss') window_start
--              , date_format(window_end, 'yyyy-MM-dd HH:mm:ss') window_end
--              , count(user_id)/60.0 tps
--              , date_format(min(ts), 'yyyy-MM-dd HH:mm:ss') ts_min
--              , date_format(max(ts), 'yyyy-MM-dd HH:mm:ss') ts_max
--         FROM TABLE(
--                 TUMBLE(TABLE user_log, DESCRIPTOR(ts), INTERVAL '10' MINUTE))
--         where TIMESTAMPDIFF( MINUTE, ts , window_end) <= 0
--         group by window_start, window_end
--     )
-- ;

-- 整点计算过去没10秒的最近一分钟 tps
drop table if exists user_log_sink_2;
CREATE TABLE user_log_sink_2
(
    window_start STRING,
    window_end   STRING,
    window_timxx timestamp(3),
    ts timestamp(3),
    PRIMARY KEY (window_start) NOT ENFORCED
) WITH (
      'connector' = 'upsert-kafka'
      ,'topic' = 'user_log_tps'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'key.format' = 'json'
      ,'value.format' = 'json'
      );


---- 失败，flink 不支持不等值链接
-- insert into user_log_sink_2
--     t1.window_start_str
--     ,t1.window_end_str
-- , t1.window_time
-- , sum(t2.coun)/60.0 tps
-- from (
-- select date_format(window_start, 'yyyy-MM-dd HH:mm:ss')                                    window_start_str
--      , date_format(window_end, 'yyyy-MM-dd HH:mm:ss')                                      window_end_str
--     , window_end
--      , TO_TIMESTAMP(concat(substring(date_format(ts, 'yyyy-MM-dd HH:mm:ss'), 0, 18), '0')) window_time
-- FROM TABLE(
--     HOP(TABLE user_log, DESCRIPTOR(ts), INTERVAL '10' MINUTE, INTERVAL '10' MINUTE))
-- group by window_start, window_end, substring(date_format(ts, 'yyyy-MM-dd HH:mm:ss'), 0, 18)
--     ) t1
--     left join
--     (select window_start, window_end, ts, count(1) coun
--     TABLE(
--         HOP(TABLE user_log, DESCRIPTOR(ts), INTERVAL '10' MINUTE, INTERVAL '10' MINUTE))
--     group by window_start, window_end, ts
--         )t2 on t1.window_end = t2.window_end and 1=1
-- --             and TIMESTAMPDIFF( second, t1.window_time , t2.ts) <= 0
-- --         and TIMESTAMPDIFF( second, t1.window_time , t2.ts) >= -60
-- group by t1.window_start_str
--         ,t1.window_end_str
--         , t1.window_time
-- ;

insert into user_log_sink_2
    t1.window_start_str
    ,t1.window_end_str
, t1.window_time
, sum(t2.coun)/60.0 tps
from (
    select date_format(window_start, 'yyyy-MM-dd HH:mm:ss')                                    window_start_str
        , date_format(window_end, 'yyyy-MM-dd HH:mm:ss')                                      window_end_str
        , window_end
        , TO_TIMESTAMP(concat(substring(date_format(ts, 'yyyy-MM-dd HH:mm:ss'), 0, 18), '0')) window_time
    FROM TABLE(
    HOP(TABLE user_log, DESCRIPTOR(ts), INTERVAL '10' MINUTE, INTERVAL '10' MINUTE))
    group by window_start, window_end, substring(date_format(ts, 'yyyy-MM-dd HH:mm:ss'), 0, 18)
    ) t1
    left join user_log t2 on t1.window_time = t2.ts
    --             and TIMESTAMPDIFF( second, t1.window_time , t2.ts) <= 0
--         and TIMESTAMPDIFF( second, t1.window_time , t2.ts) >= -60
group by t1.window_start_str
        ,t1.window_end_str
        , t1.window_time
;








