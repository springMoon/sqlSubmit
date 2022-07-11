--- test flink window parameter
-- table.exec.emit.early-fire.enabled: true
-- table.exec.emit.early-fire.delay: 5000 # 5s
-- set table.exec.emit.early-fire.enabled = true;
-- set table.exec.emit.early-fire.delay = 5000;
-- set pipeline.name = test_table_parameter;
set table.exec.resource.default-parallelism = 2;

-- kafka source
drop table if exists user_log;
CREATE TABLE user_log
(
    `event_time` TIMESTAMP(3) METADATA FROM 'timestamp' VIRTUAL,  -- from Debezium format
    `partition_id` BIGINT METADATA FROM 'partition' VIRTUAL,  -- from Kafka connector
    `offset` BIGINT METADATA VIRTUAL,  -- from Kafka connector
    user_id     VARCHAR,
    item_id     VARCHAR,
    category_id VARCHAR,
    behavior    VARCHAR,
    proc_time  as PROCTIME(),
    ts          TIMESTAMP(3),
    WATERMARK FOR ts AS ts - INTERVAL '1' SECOND
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );

drop table if exists user_log_sink_1;
CREATE TABLE user_log_sink_1
(
    wCurrent string
    ,wStart     STRING
    ,wEnd    STRING
    ,pv    bigint
    ,uv    bigint
    ,user_max    STRING
    ,primary key(wCurrent,wStart,wEnd) not enforced
) WITH (
--       'connector' = 'print'
      'connector' = 'upsert-kafka'
      ,'topic' = 'user_log_sink'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
--       ,'scan.startup.mode' = 'latest-offset'
      ,'key.format' = 'json'
      ,'value.format' = 'json'
      );

-- group window aggregation
insert into user_log_sink_1
select 'group window'
     ,date_format(TUMBLE_START(ts, INTERVAL '1' minute), 'yyyy-MM-dd HH:mm:ss') AS wStart
     ,date_format(TUMBLE_END(ts, INTERVAL '1' minute), 'yyyy-MM-dd HH:mm:ss') AS wEnd
     ,count(user_id) pv
     ,count(distinct user_id) uv
    ,max(user_id)
from user_log
group by TUMBLE(ts, INTERVAL '1' minute)
;