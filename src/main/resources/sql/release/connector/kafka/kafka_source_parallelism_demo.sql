-- execute config: 'table.exec.source.force-break-chain' = 'true'
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
  --,'source.parallelism' = '-2'
  ,'source.parallelism' = '2'
);

CREATE TABLE user_log_sink (
   `day` string
   ,num bigint
   ,min_user_id bigint
   ,max_user_id bigint
) WITH (
   'connector' = 'print'
);

insert into user_log_sink
select `day`
, num
, min_user_id, max_user_id
from(
select DATE_FORMAT(ts,'yyyyMMdd') `day`
,count(distinct user_id) num
,min(cast(replace(user_id,'xxxxxxxxxxxxx','') as bigint)) min_user_id
,max(cast(replace(user_id,'xxxxxxxxxxxxx','') as bigint)) max_user_id
from user_log
-- where DATE_FORMAT(ts,'yyyyMMdd') = date_format(current_timestamp, 'yyyyMMdd')
group by DATE_FORMAT(ts,'yyyyMMdd')
)t1
 where num % 2 = 0
;