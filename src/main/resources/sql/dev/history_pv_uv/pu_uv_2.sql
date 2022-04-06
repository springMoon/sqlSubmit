-- flink cumulate window tvf calc pv&uv, only current day data + history
drop table if exists user_log;
CREATE TABLE user_log
(
     user_id     VARCHAR
    ,item_id     VARCHAR
    ,category_id VARCHAR
    ,behavior    VARCHAR
    ,ts          TIMESTAMP(3)
    ,proc_time   as PROCTIME()
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
    ,last_pv  bigint
    ,last_uv  bigint
    ,PRIMARY KEY (cal_day, behavior) NOT ENFORCED
) with (
      'connector' = 'jdbc'
      ,'url' = 'jdbc:mysql://venn:3306/venn'
      ,'table-name' = 'pv_uv'
      ,'username' = 'root'
      ,'password' = '123456'
);

create table if not exists user_log_lookup_join(
    cal_day varchar
    ,behavior varchar
    ,pv  bigint
    ,uv  bigint
    ,PRIMARY KEY (cal_day, behavior) NOT ENFORCED
    ) with (
          'connector' = 'jdbc'
          ,'url' = 'jdbc:mysql://localhost:3306/venn'
          ,'table-name' = 'pv_uv'
          ,'username' = 'root'
          ,'password' = '123456'
          ,'scan.partition.column' = 'cal_day'
          ,'scan.partition.num' = '1'
          ,'scan.partition.lower-bound' = '0'
          ,'scan.partition.upper-bound' = '9999'
          ,'lookup.cache.max-rows' = '1000'
        -- one day, once cache, the value will not update
          ,'lookup.cache.ttl' = '86400000' -- ttl time 超过这么长时间无数据才行
    );

insert into user_log_sink
select
     a.cal_day
    ,a.behavior
    ,'' start_time
    ,date_format(a.proc_time, 'yyyy-MM-dd HH:mm:ss')
    ,a.pv + COALESCE(c.pv,0)
    ,a.uv + COALESCE(c.uv,0)
,c.pv last_uv
,c.uv last_uv
from(
    select
     date_format(window_start, 'yyyy-MM-dd') cal_day
     ,behavior
     ,max(proc_time) proc_time
     ,count(user_id) pv
     ,count(distinct user_id) uv
    FROM TABLE(
        CUMULATE(TABLE user_log, DESCRIPTOR(ts), INTERVAL '10' SECOND, INTERVAL '1' DAY))
      GROUP BY window_start, window_end, behavior
        )a
        left join user_log_lookup_join FOR SYSTEM_TIME AS OF a.proc_time AS c
                  ON  a.behavior = c.behavior
--                          and udf_date_add(a.proc_time, 1) = c.cal_day
                      and udf_date_add_new(date_format(a.proc_time, 'yyyy-MM-dd HH:mm:ss'), -1) = c.cal_day
--                       and a.cal_day = c.cal_day
;

--- 接口查询需要查询对应行为全部时间的结果求和, uv 暂时不可用
-- select behavior,sum(pv)
-- from pv_uv
-- group by behavior
