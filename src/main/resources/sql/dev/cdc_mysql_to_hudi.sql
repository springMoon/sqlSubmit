-- mysql cdc to print
-- creates a mysql table source
drop table if exists t_feature;
CREATE TABLE t_feature (
  id varchar
  ,code VARCHAR
  ,send_time VARCHAR
  ,rms VARCHAR
  ,mean VARCHAR
  ,peak varchar
  ,kurtosis varchar
  ,skewness varchar
  ,proc_time as PROCTIME()
  ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
 'connector' = 'mysql-cdc'
 ,'hostname' = 'localhost'
 ,'port' = '3306'
 ,'username' = 'root'
 ,'password' = '123456'
 ,'database-name' = 'test_db'
 ,'table-name' = 't_feature'
 ,'server-id' = '1'
);

-- kafka sink
drop table if exists t_feature_sink;
CREATE TABLE t_feature_sink (
  id varchar
  ,code VARCHAR
  ,send_time VARCHAR
  ,rms VARCHAR
  ,mean VARCHAR
  ,peak varchar
  ,kurtosis varchar
  ,skewness varchar
  ,ts timestamp(3)
) WITH (
  'connector' = 'hudi'
   ,'path' = 'hdfs://thinkpad:8020/tmp/hudi/t_feature'
   ,'hoodie.datasource.write.recordkey.field' = 'id'
   ,'write.precombine.field' = 'ts'
   ,'write.tasks' = '1'
   ,'table.type' = 'MERGE_ON_READ'
   ,'compaction.tasks' = '1'
   ,'compaction.trigger.strategy' = 'num_or_time'
   ,'compaction.delta_commits' = '100'
   ,'compaction.delta_seconds' = '6000'
--    ,'read.tasks' = '1'
--    ,'read.streaming.enabled' = 'true'
--    ,'hoodie.datasource.query.type' = 'snapshot'
--    ,'read.streaming.start-commit' = '20210101000000'
--    ,'read.streaming.check-interval' = '1'
--    ,'hoodie.datasource.merge.type' = 'payload_combine'
);

-- sink to kafka
insert into t_feature_sink
select id, code, send_time, rms, mean, peak, kurtosis, skewness, proc_time
from t_feature;
