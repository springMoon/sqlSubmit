-- count 每天输入数据量达到 2000 条，输出一条数据
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
   `day` string
   ,num bigint
) WITH (
   'connector' = 'print'
);

-- cep 连续事件: a b c
insert into user_log_sink
select `day`,num
from(
select DATE_FORMAT(ts,'yyyyMMdd') `day`,count(1) num
from user_log
-- where DATE_FORMAT(ts,'yyyyMMdd') = date_format(current_timestamp, 'yyyyMMdd')
group by DATE_FORMAT(ts,'yyyyMMdd')
)t1
where num = 2000
;