-- kafka source
drop table if exists user_log;
CREATE TABLE user_log
(
    user_id     VARCHAR,
    item_id     VARCHAR,
    category_id VARCHAR,
    behavior    VARCHAR,
    ts          TIMESTAMP(3),
    WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'earliest-offset'
      ,'format' = 'json'
      );


-- set table.sql-dialect=hive;
-- kafka sink
drop table if exists user_log_sink;
CREATE TABLE user_log_sink
(
    batch     STRING,
    user_id     STRING,
    item_id     STRING,
    category_id STRING,
    behavior    STRING,
    ts          timestamp(3),
    current_t     timestamp(3)
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log_sink_6'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );


-- streaming sql, insert into mysql table
insert into user_log_sink
SELECT '1',user_id, item_id, category_id, behavior, ts, now()
FROM user_log;
