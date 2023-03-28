-- kafka source
drop table if exists user_log;
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
    ,category_id VARCHAR
    ,behavior VARCHAR
  ,price double
  ,proc_time as PROCTIME()
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
-- drop table if exists user_log_sink;
-- CREATE TABLE user_log_sink (
--   user_id STRING
--   ,item_id STRING
--   ,category_id STRING
--   ,behavior STRING
--   ,proc_time timestamp(3)
--   ,ts timestamp(3)
-- ) WITH (
--   'connector' = 'print'
-- );
--
--
-- -- streaming sql, insert into mysql table
-- insert into user_log_sink
-- SELECT user_id, item_id, category_id, behavior, proc_time,ts
-- from user_log
-- ;
create table user_log_sink(
    item_id string
    ,median_price double
)WITH(
    'connector' = 'print'
);

insert into user_log_sink
select item_id, udaf_median(cast(price as double)) median_price
from user_log
group by item_id;