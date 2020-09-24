-- kafka source
drop table if exists user_log;
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
) WITH (
  'connector.type' = 'kafka'
  ,'connector.version' = 'universal'
  ,'connector.topic' = 'user_behavior'
  ,'connector.properties.zookeeper.connect' = 'venn:2181'
  ,'connector.properties.bootstrap.servers' = 'venn:9092'
  ,'connector.properties.group.id' = 'user_log_x'
  ,'connector.startup-mode' = 'group-offsets'
  ,'connector.sink-partitioner' = 'fixed'
  ,'format.type' = 'json'
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
