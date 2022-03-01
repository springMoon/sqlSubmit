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
   'connector' = 'socket'
  ,'hostname' = 'localhost'
  ,'port' = '12345'
  ,'format' = 'json'
--   ,'format' = 'csv'
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
  ,'port' = '12346'
  ,'format' = 'csv'
);


-- streaming sql, insert into mysql table
insert into socket_sink
SELECT user_id, item_id, category_id, behavior, ts
FROM user_log;
