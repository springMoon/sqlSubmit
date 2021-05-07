-- kafka source
drop table if exists user_log;
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
) WITH (
   'connector' = 'kafka'
  ,'topic' = 'user_behavior'                            -- required: topic name from which the table is read
  ,'properties.bootstrap.servers' = 'localhost:9092'    -- required: specify the Kafka server connection string
  ,'properties.group.id' = 'user_log'                   -- optional: required in Kafka consumer, specify consumer group
  ,'format' = 'json'                 -- required:  'csv', 'json' and 'avro'.
);

-- kafka sink
drop table if exists user_log_sink;
CREATE TABLE user_log_sink (
  user_id string
  ,cf ROW(item_id VARCHAR
      ,category_id VARCHAR
      ,behavior VARCHAR
      ,ts TIMESTAMP(3))
) WITH (
  'connector.type' = 'hbase'
  ,'connector.version' = '1.4.3'
  ,'connector.table-name' = 'venn'
  ,'connector.zookeeper.quorum' = 'venn:2181'
  ,'connector.zookeeper.znode.parent' = '/hbase'
  ,'connector.write.buffer-flush.max-size' = '10mb'
  ,'connector.write.buffer-flush.max-rows' = '10'
  ,'connector.write.buffer-flush.interval' = '2s'
);

-- insert
insert into user_log_sink
select user_id, ROW(item_id, category_id, behavior, ts) as cf
from user_log;
