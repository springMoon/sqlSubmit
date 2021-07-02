<<<<<<< HEAD
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
=======
-- sets up the result mode to tableau to show the results directly in the CLI
-- set execution.result-mode=tableau;
-- set sql-client.execution.result-mode=tableau;
CREATE TABLE t1(
  uuid VARCHAR(20), -- you can use 'PRIMARY KEY NOT ENFORCED' syntax to mark the field as record key
  name VARCHAR(10),
  age INT,
  ts TIMESTAMP(3),
  `partition` VARCHAR(20)
)
PARTITIONED BY (`partition`)
WITH (
  'connector' = 'hudi',
  'path' = 'hdfs:///user/wuxu/hudi/t1',
--    'path' = 'file:///opt/data',
  'write.tasks' = '1', -- default is 4 ,required more resource
  'compaction.tasks' = '1', -- default is 10 ,required more resource
  'table.type' = 'MERGE_ON_READ' -- this creates a MERGE_ON_READ table, by default is COPY_ON_WRITE
);

-- insert data using values
-- sets up the result mode to tableau to show the results directly in the CLI
-- set execution.result-mode=tableau;
-- set sql-client.execution.result-mode=tableau;
>>>>>>> 33f6f92ef88703ff136a650917f48758be4cfbd8

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
<<<<<<< HEAD
INSERT INTO user_log_sink
select /*+ OPTIONS('read.streaming.check-interval' = '4') */user_id as uuid, category_id, behavior, ts, item_id from user_log;
=======
INSERT INTO t1 VALUES  ('id1','Danny',23,TIMESTAMP '1970-01-01 00:00:01','par1');
>>>>>>> 33f6f92ef88703ff136a650917f48758be4cfbd8
