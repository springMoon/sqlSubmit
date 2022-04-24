-- kafka source
CREATE TABLE user_log
(
    user_id     VARCHAR,
    item_id     VARCHAR,
    category_id VARCHAR,
    behavior    VARCHAR,
    ts          TIMESTAMP(3),
    WATERMARK FOR ts AS ts - INTERVAL '1' MINUTES
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );

-- set table.sql-dialect=hive;
-- kafka sink
CREATE TABLE user_log_sink
(
    start_time   timestamp(3),
    end_time     timestamp(3),
    coun         bigint
--         coun         MULTISET<TIMESTAMP(3)>
) WITH (
      'connector' = 'print'
);


-- streaming sql, insert into mysql table
-- insert into user_log_sink
-- select window_start, window_end, count(user_id)
-- from TABLE(
--    TUMBLE(TABLE user_log, DESCRIPTOR(ts), INTERVAL '10' MINUTES))
-- group by window_start, window_end;

-- insert into user_log_sink
-- select window_start, window_end
--     ,count(user_id)
--      -- , COLLECT(ts)
-- from TABLE(
--         HOP(TABLE user_log, DESCRIPTOR(ts), INTERVAL '3' MINUTES ,INTERVAL '10' MINUTES ))
-- group by window_start, window_end;

insert into user_log_sink
select window_start, window_end, count(user_id)
from TABLE(
        CUMULATE(TABLE user_log, DESCRIPTOR(ts), INTERVAL '2' MINUTES ,INTERVAL '10' MINUTES ))
group by window_start, window_end;
