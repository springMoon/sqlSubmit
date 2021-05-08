CREATE CATALOG hive_catalog WITH (
  'type'='iceberg',
  'catalog-type'='hive',
  'uri'='thrift://localhost:9083',
  'clients'='5',
  'property-version'='1',
  'warehouse'='hdfs:///tmp/iceberg/hive_catalog'
);

-- sink table
-- CREATE TABLE iceberg_to_kafka_user_log_sink (
--   user_id VARCHAR
--   ,item_id VARCHAR
--   ,category_id VARCHAR
--   ,behavior VARCHAR
-- ) WITH (
--   'connector' = 'kafka'
--   ,'topic' = 'user_behavior_sink'
--   ,'properties.bootstrap.servers' = 'localhost:9092'
--   ,'properties.group.id' = 'user_log'
--   ,'format' = 'json'
-- );

-- insert

insert into iceberg_to_kafka_user_log_sink
select /*+ OPTIONS('streaming'='true', 'monitor-interval'='1s')*/ user_id, item_id, category_id, behavior
from hive_catalog.hive_catalog_db.user_log_sink;