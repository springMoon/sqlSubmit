set execution.runtime-mode=BATCH;
set table.sql-dialect=hive;
drop table if exists myHive.test.user_log_1;
CREATE TABLE myHive.test.user_log_1 (
    user_id STRING
    ,item_id STRING
    ,category_id STRING
    ,behavior STRING
) PARTITIONED BY (ds STRING) STORED AS parquet TBLPROPERTIES (
  'partition.time-extractor.timestamp-pattern'='$dt $hr:00:00',
  'sink.partition-commit.trigger'='partition-time',
  'sink.partition-commit.delay'='1 min',
  'sink.partition-commit.policy.kind'='metastore,success-file'
);

set table.sql-dialect=default;
insert into myHive.test.user_log_1
select * from myHive.test.user_log;