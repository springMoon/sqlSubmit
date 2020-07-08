-- kafka source
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
) WITH (
  'connector.type' = 'kafka'
  ,'connector.version' = 'universal'
  ,'connector.topic' = 'user_behavior_1'
  ,'connector.properties.zookeeper.connect' = 'master:2181,slave1:2181,slave2:2181'
  ,'connector.properties.bootstrap.servers' = 'master:6667,slave1:6667,slave2:6667,slave3:6667,slave4:6667'
  ,'connector.properties.group.id' = 'user_log_x'
  ,'connector.startup-mode' = 'group-offsets'
  ,'connector.sink-partitioner' = 'fixed'
  ,'format.type' = 'json'
);

-- kafka sink
CREATE TABLE user_log_sink (
  user_id string
  ,f ROW(item_id VARCHAR
      ,category_id VARCHAR
      ,behavior VARCHAR
      ,ts TIMESTAMP(3))
) WITH (
  'connector.type' = 'hbase'
  ,'connector.version' = '1.4.3'
  ,'connector.table-name' = 'collision_detection:terminal_geographic_info_30'
  ,'connector.zookeeper.quorum' = 'master:2181,slave1:2181,slave2:2181'
  ,'connector.zookeeper.znode.parent' = '/hbase-unsecure'
  ,'connector.write.buffer-flush.max-size' = '10mb'
  ,'connector.write.buffer-flush.max-rows' = '1000'
  ,'connector.write.buffer-flush.interval' = '2s'
);

-- insert
insert into user_log_sink
select user_id, ROW(item_id, category_id, behavior, ts) as f
from user_log;