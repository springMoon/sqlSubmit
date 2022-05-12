-- flink 1.14.0 cumulate window offset
-- kafka source
CREATE TABLE user_log (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,ts TIMESTAMP(3)
  ,process_time as proctime()
  , WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_behavior'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  --,'scan.startup.mode' = 'group-offsets'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
);

---sinkTable
CREATE TABLE user_log_sink (
  window_start timestamp(3)
  ,window_end timestamp(3)
  ,window_time timestamp(3)
  ,coun bigint
) WITH (
   'connector' = 'print'
);

insert into user_log_sink
select window_start,window_end,window_time, count(user_id)
from TABLE(CUMULATE(TABLE user_log, DESCRIPTOR(ts), interval '1' minute, interval '1' day, interval '10' minute))
group by window_start,window_end,window_time