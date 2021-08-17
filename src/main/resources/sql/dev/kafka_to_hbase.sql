-- Lookup Source: Sync Mode
-- kafka source
CREATE TABLE user_log (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,ts TIMESTAMP(3)
  ,process_time as proctime()
  , WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_behavior'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

drop table if exists hbase_user_log_sink ;
CREATE TABLE hbase_user_log_sink (
   user_id STRING
  ,cf ROW(item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,ts TIMESTAMP(3))
) WITH (
   'connector' = 'hbase-2.2'
   ,'zookeeper.quorum' = 'localhost:12181'
   ,'zookeeper.znode.parent' = '/hbase'
   ,'table-name' = 'user_log'
   -- ,'lookup.cache.max-rows' = '10000'
   -- ,'lookup.cache.ttl' = '10 minute' -- ttl time 超过这么长时间无数据才行
   -- ,'lookup.async' = 'true'
);

insert into hbase_user_log_sink
select user_id, row(item_id, category_id, behavior, ts)
from user_log;