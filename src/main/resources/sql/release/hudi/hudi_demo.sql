-- test sync hudi metadata to hive metastore
create table if not exists kafka_ods_user_info (
    id        int
    ,name     string
    ,sex      string
    ,age      int
    ,birthday string
) with (
    'connector' = 'kafka'
    ,'topic' = 'datalake_test_topic_1'
    ,'properties.bootstrap.servers' = 'localhost:9092'
    ,'properties.group.id' = 'testGroup'
    ,'scan.startup.mode' = 'latest-offset'
    ,'format' = 'csv'
);

drop table ods_user_info_15;

create table if not exists ods_user_info_15(
    dl_uuid   string
    ,id        int
    ,name     string
    ,sex      string
    ,age      int
    ,birthday string
    ,`etl_create_time`     TIMESTAMP(3)   COMMENT 'ETL创建时间'
    ,`etl_update_time`     TIMESTAMP(3)   COMMENT 'ETL更新时间'
    ,`partition` string
) with (
    'connector' = 'hudi'
   ,'path' = 'hdfs://thinkpad:8020/user/hive/warehouse/dl_ods.db/ods_user_info_15'
   ,'hoodie.datasource.write.recordkey.field' = 'dl_uuid'
   ,'hoodie.datasource.write.partitionpath.field' = 'partition'
   ,'write.precombine.field' = 'etl_update_time'
   ,'write.tasks' = '1'
   ,'table.type' = 'MERGE_ON_READ'
   ,'compaction.tasks' = '1'
   ,'compaction.trigger.strategy' = 'num_or_time'
   ,'compaction.delta_commits' = '100'
   ,'compaction.delta_seconds' = '6000'
  ,'hive_sync.enable' = 'true'
  ,'hive_sync.db' = 'dl_ods'
  ,'hive_sync.table' = 'ods_user_info_15'
  ,'hive_sync.file_format' = 'PARQUET'
  ,'hive_sync.support_timestamp' = 'true'
  ,'hive_sync.use_jdbc' = 'true'
  ,'hive_sync.jdbc_url' = 'jdbc:hive2://thinkpad:10000'
  ,'hive_sync.metastore.uris' = 'thrift://thinkpad:9083'
 --  ,'hoodie.datasource.hive_style_partition' = 'true' -- already remove
  ,'hive_sync.partition_fields' = 'partition'
   ,'read.tasks' = '1'
   ,'read.streaming.enabled' = 'true'
   ,'hoodie.datasource.query.type' = 'snapshot'
   ,'read.streaming.start-commit' = '20210101000000'
   ,'read.streaming.check-interval' = '1'
   ,'hoodie.datasource.merge.type' = 'payload_combine'
   ,'read.utc-timezone' = 'false'
--    ,'hoodie.memory.spillable.map.path' = '/tmp/hudi'
);

-- set table.dynamic-table-options.enabled=true;
-- set 'pipeline.name' = 'insert_ods_user_info';
insert into ods_user_info_15
select /*+ OPTIONS('pipeline.name'='insert_ods_user_info') */  -- work on flink 1.13
    cast(id as string) dl_uuid
  ,id
  ,name
  ,sex
  ,age
  ,birthday
  ,now() etl_create_time
  ,now() etl_update_time
  ,date_format(now(), 'yyyy/MM/dd') -- only support partition format
from kafka_ods_user_info;
