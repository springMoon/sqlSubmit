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
   aid STRING
   ,bid STRING
   ,cid STRING
) WITH (
   'connector' = 'print'
);

-- cep 连续事件: a b c
insert into user_log_sink
select t.aid,t.bid,t.cid
from user_log
MATCH_RECOGNIZE(
    partition by item_id
    order by process_time
    MEASURES
     a.user_id as aid
    ,b.user_id as bid
    ,d.user_id as cid
    ONE ROW PER MATCH
    PATTERN (a b d) --WITHIN INTERVAL '1' MINUTE
    DEFINE
      a as a.user_id='user_id_1'
     ,b as b.user_id='user_id_2'
     ,d as d.user_id='user_id_3'
)as t