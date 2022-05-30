--- test flink window parameter
-- table.exec.emit.early-fire.enabled: true
-- table.exec.emit.early-fire.delay: 5000 # 5s
set table.exec.emit.early-fire.enabled = true;
set table.exec.emit.early-fire.delay = 5000;
set pipeline.name = test_table_parameter;

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
    WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );

-- set table.sql-dialect=hive;
-- kafka sink
drop table if exists user_log_sink;
CREATE TABLE user_log_sink
(
    current_time_str string
    ,item_id     STRING
    ,behavior    STRING
    ,coun    bigint
    ,hou string
    ,window_start timestamp(3)
    ,window_end timestamp(3)
) WITH (
      'connector' = 'print'
      );


-- window tvf doesn't support early-fire and late-fire
-- insert into user_log_sink
-- SELECT item_id ,behavior , 0, DATE_FORMAT(window_start, 'HH') `hour`, window_start, window_end
-- FROM TABLE(
--      TUMBLE(TABLE user_log, DESCRIPTOR(proc_time), INTERVAL '1' MINUTES )) t1
-- group by  item_id, behavior, window_start, window_end
-- ;

-- group window aggregation
insert into user_log_sink
select date_format(now(), 'yyyy-MM-dd HH:mm:ss')
     ,max(item_id) item_id
     ,max(behavior) behavior
     ,count(1) coun
     ,''
     ,TUMBLE_START(proc_time, INTERVAL '1' minute) AS wStart
     ,TUMBLE_END(proc_time, INTERVAL '1' minute) AS wEnd
from user_log
group by TUMBLE(proc_time, INTERVAL '1' minute)