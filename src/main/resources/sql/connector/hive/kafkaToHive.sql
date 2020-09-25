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
  'connector.type' = 'kafka'
  ,'connector.version' = 'universal'
  ,'connector.topic' = 'user_behavior'
  ,'connector.properties.zookeeper.connect' = 'venn:2181'
  ,'connector.properties.bootstrap.servers' = 'venn:9092'
  ,'connector.properties.group.id' = 'user_log'
  ,'connector.startup-mode' = 'group-offsets'
  ,'connector.sink-partitioner' = 'fixed'


  ,'format.type' = 'json'
);

-- set table.sql-dialect=hive;
-- kafka sink
drop table if exists hive_table;
CREATE TABLE hive_table (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
) PARTITIONED BY (dt STRING, hr STRING) STORED AS parquet TBLPROPERTIES (
  'partition.time-extractor.timestamp-pattern'='$dt $hr:00:00',
  'sink.partition-commit.trigger'='partition-time',
  'sink.partition-commit.delay'='1 min',
  'sink.partition-commit.policy.kind'='metastore,success-file'
);


-- streaming sql, insert into hive table
insert into table hive_table
SELECT user_id, item_id, category_id, behavior, DATE_FORMAT(ts, 'yyyy-MM-dd'), DATE_FORMAT(ts, 'HH')
FROM user_log;
