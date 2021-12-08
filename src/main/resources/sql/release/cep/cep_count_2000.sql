-- cep 失败
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
  ,'topic' = 'user_log'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
);

CREATE TABLE user_log_sink (
   first_ts timestamp (3)
   ,last_ts timestamp (3)
   ,cout bigint
) WITH (
   'connector' = 'print'
);

-- cep 连续事件: a b c
insert into user_log_sink
select first_ts, last_ts, cout
from user_log
MATCH_RECOGNIZE(
--     partition by item_id
    order by process_time
    MEASURES
     FIRST(a.process_time) as first_ts
     ,last(a.process_time) as last_ts
     ,count(a.user_id) as cout
    ONE ROW PER MATCH
    PATTERN (a{2000}) --WITHIN INTERVAL '1' MINUTE
    DEFINE
      a as a.user_id is not null
)as t