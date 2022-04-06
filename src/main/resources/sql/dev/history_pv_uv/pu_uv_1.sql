-- flink cumulate window tvf calc pv&uv, only current day data
drop table if exists user_log;
CREATE TABLE user_log
(
     user_id     VARCHAR
    ,item_id     VARCHAR
    ,category_id VARCHAR
    ,behavior    VARCHAR
    ,ts          TIMESTAMP(3)
    ,WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
);

create table if not exists user_log_sink(
    cal_day varchar
    ,behavior varchar
    ,start_time VARCHAR
    ,end_time VARCHAR
    ,pv  bigint
    ,uv  bigint
    ,PRIMARY KEY (cal_day, behavior) NOT ENFORCED
) with (
      'connector' = 'jdbc'
      ,'url' = 'jdbc:mysql://venn:3306/venn'
      ,'table-name' = 'pv_uv'
      ,'username' = 'root'
      ,'password' = '123456'
);

insert into user_log_sink
select
 date_format(window_start, 'yyyy-MM-dd') cal_day
 ,behavior
 ,date_format(window_start, 'HH:mm:ss') start_time
 , date_format(window_end, 'HH:mm:ss') end_time
 , count(user_id) pv
 , count(distinct user_id) uv
FROM TABLE(
    CUMULATE(TABLE user_log, DESCRIPTOR(ts), INTERVAL '10' SECOND, INTERVAL '1' DAY))
  GROUP BY window_start, window_end, behavior
;

--- 接口查询需要查询对应行为全部时间的结果求和, uv 暂时不可用
-- select behavior,sum(pv)
-- from pv_uv
-- group by behavior
