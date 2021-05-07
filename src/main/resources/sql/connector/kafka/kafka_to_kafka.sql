-- kafka source
drop table if exists user_log ;
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
drop table if exists user_log_sink ;
CREATE TABLE user_log_sink (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
) WITH (
  'connector' = 'kafka'
--   ,'pipeline.name' = 'kafka_to_Kafka'
  ,'topic' = 'user_behavior_sink'                            -- required: topic name from which the table is read
  ,'properties.bootstrap.servers' = 'localhost:9092'    -- required: specify the Kafka server connection string
  ,'properties.group.id' = 'user_log'                   -- optional: required in Kafka consumer, specify consumer group
  ,'sink.partitioner' = 'fixed'                         --optional fixed 每个 flink 分区数据只发到 一个 kafka 分区
                                                                          -- round-robin flink 分区轮询分配到 kafka 分区
                                                                          -- custom 自定义分区策略
  --,'connector.sink-partitioner-class' = 'org.mycompany.MyPartitioner'   -- 自定义分区类
  ,'format' = 'json'                 -- required:  'csv', 'json' and 'avro'.
);

-- insert
insert into user_log_sink
select user_id, item_id, category_id, behavior || '_1', ts
from user_log;

-- insert 2
insert into user_log_sink
select user_id, item_id, category_id, behavior || '_2', ts
from user_log;