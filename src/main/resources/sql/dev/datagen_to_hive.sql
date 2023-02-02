-- -- kafka source
set execution.runtime-mode=BATCH;
-- set pipeline.name = xxx;
-- drop table if exists user_log;
-- CREATE TABLE user_log
-- (
--     user_id     VARCHAR,
--     item_id     VARCHAR,
--     category_id VARCHAR,
--     behavior    VARCHAR
-- ) WITH (
--       'connector' = 'datagen'
--       ,'rows-per-second' = '200'
--       ,'number-of-rows' = '10000'
--       ,'fields.user_id.kind' = 'random'
--       ,'fields.item_id.kind' = 'random'
--       ,'fields.category_id.kind' = 'random'
--       ,'fields.behavior.kind' = 'random'
--       ,'fields.user_id.length' = '20'
--       ,'fields.item_id.length' = '10'
--       ,'fields.category_id.length' = '10'
--       ,'fields.behavior.length' = '10'
--       );
--
--
-- set table.sql-dialect=hive;
-- set sql-client.execution.result-mode=tableau;
-- drop table if exists myHive.test.user_log;
-- CREATE TABLE myHive.test.user_log (
--     user_id STRING
--     ,item_id STRING
--     ,category_id STRING
--     ,behavior STRING
-- ) PARTITIONED BY (ds STRING) STORED AS parquet TBLPROPERTIES (
--   'partition.time-extractor.timestamp-pattern'='$dt $hr:00:00',
--   'sink.partition-commit.trigger'='partition-time',
--   'sink.partition-commit.delay'='1 min',
--   'sink.partition-commit.policy.kind'='metastore,success-file'
-- );
--
--
-- -- streaming sql, insert into hive table
-- set table.sql-dialect=default;
-- insert into myHive.test.user_log
-- SELECT user_id, item_id, category_id, behavior, DATE_FORMAT(now(), 'yyyy-MM-dd') --,DATE_FORMAT(now(), 'HH')
-- FROM user_log;
--


insert into myHive.test.user_log
select user_id, item_id, category_id, behavior, DATE_FORMAT(now(), 'yyyy-MM-dd')
from user_log;
