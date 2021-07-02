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

CREATE TABLE user_log_sink(
  uuid VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
  ,item_id VARCHAR
)
PARTITIONED BY (`item_id`)
WITH (
  'connector' = 'hudi',
  -- 'path' = 'hdfs:///user/root/hudi/ods_t1_mor',
  'path' = 'file:///tmp/flink-hudi/ods_t1_mor',
  'write.precombine.field' = 'ts',
  'write.tasks' = '1', -- default is 4 ,required more resource
  'compaction.tasks' = '1', -- default is 10 ,required more resource
  'table.type' = 'MERGE_ON_READ', -- this creates a MERGE_ON_READ table, by default is COPY_ON_WRITE
  'compaction.trigger.strategy' = 'num_or_time',
  'compaction.delta_commits' = '5',
  'compaction.delta_seconds' = '60'
);

-- insert data using values
INSERT INTO user_log_sink
select /*+ OPTIONS('read.streaming.check-interval' = '4') */user_id as uuid, category_id, behavior, ts, item_id from user_log;