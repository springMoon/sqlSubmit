-- kafka source
drop table if exists user_log;
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior INT
  ,ts TIMESTAMP(3)
  ,process_time as proctime()
  , WATERMARK FOR ts AS ts
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_behavior'
  ,'properties.bootstrap.servers' = 'venn:9092'
  ,'properties.group.id' = 'user_log_x'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

-- kafka sink
drop table if exists user_log_sink;
CREATE TABLE user_log_sink (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,ts  TIMESTAMP(3)
) WITH (
  'connector.type' = 'kudu'
  ,'kudu.masters' = 'venn:7051,venn:7151,venn:7251'
  ,'kudu.table' = 'user_log'
  ,'kudu.hash-columns' = 'user_id'
  ,'kudu.primary-key-columns' = 'user_id'
  ,'kudu.max-buffer-size' = '5000'
  ,'kudu.flush-interval' = '1000'
);

-- insert
insert into user_log_sink
select user_id, item_id, category_id,ts
from user_log;
