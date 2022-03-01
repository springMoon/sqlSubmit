-- kafka source
drop table if exists user_log;
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
  ,WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
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
drop table if exists socket_sink;
CREATE TABLE socket_sink (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,ts timestamp(3)
) WITH (
  'connector' = 'socket'
  ,'hostname' = 'localhost'
  ,'max.retry' = '2'
--   ,'retry.interval' = '2'
  ,'port' = '19870'
  ,'format' = 'json'
);


-- streaming sql, insert into mysql table
insert into socket_sink
SELECT user_id, item_id, category_id, behavior, ts
FROM user_log;
