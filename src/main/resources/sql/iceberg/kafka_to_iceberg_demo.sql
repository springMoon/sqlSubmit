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
-- create catalog
CREATE CATALOG ice WITH (
  'type'='iceberg',
  'catalog-type'='hive',
  'uri'='thrift://thinkpad:9083',
  'clients'='5',
  'property-version'='1',
  'warehouse'='hdfs://thinkpad:8020/user/hive/datalake/ice'
);
-- use catalog
use catalog ice;
-- create database
create database ice;
-- use database;

CREATE TABLE ice.ice.user_log_sink (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,ts timestamp(3)
   ,PRIMARY KEY (user_id) NOT ENFORCED
);


-- streaming sql, insert into mysql table
insert into ice.ice.user_log_sink
SELECT user_id, item_id, category_id, behavior, ts
FROM user_log;


-- read
SET table.dynamic-table-options.enabled=true;
SELECT * FROM user_log_sink /*+ OPTIONS('streaming'='true', 'monitor-interval'='1s')*/ ;
