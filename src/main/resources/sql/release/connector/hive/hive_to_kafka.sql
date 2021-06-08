-- read hive, write to kafka -- batch when read complete, job finish
-- sink
drop table if exists read_hiv_sink;
CREATE TABLE read_hiv_sink (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,dt VARCHAR
  ,hr VARCHAR
) WITH (
  'connector.type' = 'kafka'
  ,'connector.version' = 'universal'
  ,'connector.topic' = 'read_hiv_sink'
  ,'connector.properties.zookeeper.connect' = 'venn:2181'
  ,'connector.properties.bootstrap.servers' = 'venn:9092'
  ,'connector.properties.group.id' = 'flink_sql'
  ,'connector.startup-mode' = 'group-offsets'
  ,'connector.sink-partitioner' = 'fixed'
  ,'format.type' = 'json'
);

insert into read_hiv_sink select user_id, item_id, category_id, behavior, dt, hr from hive_table;