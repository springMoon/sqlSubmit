-- kafka source
-- drop table if exists user_log ;
-- CREATE TABLE user_log (
--   user_id VARCHAR
--   ,item_id VARCHAR
--   ,category_id VARCHAR
--   ,behavior VARCHAR
-- ) WITH (
--   'connector' = 'kafka'
--   ,'topic' = 'user_behavior'
--   ,'properties.bootstrap.servers' = 'localhost:9092'
--   ,'properties.group.id' = 'user_log'
--   ,'format' = 'json'
-- );

CREATE CATALOG hive_catalog WITH (
  'type'='iceberg',
  'catalog-type'='hive',
  'uri'='thrift://localhost:9083',
  'clients'='5',
  'property-version'='1',
  'warehouse'='hdfs:///tmp/iceberg/hive_catalog'
);
-- kafka sink
-- use catalog hive_catalog;
-- create database hive_catalog_db;
-- use hive_catalog_db;
-- CREATE TABLE hive_catalog.hive_catalog_db.user_log_sink (
--   user_id VARCHAR
--   ,item_id VARCHAR
--   ,category_id VARCHAR
--   ,behavior VARCHAR
-- ) WITH (
--   'type'='iceberg',
--   'catalog-type'='hadoop',
--   'warehouse'='hdfs:///tmp/iceberg/hive_catalog/hive_catalog_db/user_log_sink',
--   'property-version'='1'
-- );

-- insert
insert into hive_catalog.hive_catalog_db.user_log_sink
select user_id, item_id, category_id, behavior || '_1'
from user_log;