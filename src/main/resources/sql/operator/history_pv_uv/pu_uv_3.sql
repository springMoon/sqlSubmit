-- flink cumulate window tvf calc pv&uv, only current day data + history, uv
-- bloom filter cal uv
-- 失败，布隆过滤器无法直接获取 uv 数量，cumulate 窗口中使用 agg function 又不能获取窗口结束状态，无法在窗口结束的时候累加 uv 数
-- 无法解决任务重启，历史 userId 丢失的问题（第4版，用 redis 了）
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
      'connector' = 'print'
--       'connector' = 'jdbc'
--       ,'url' = 'jdbc:mysql://venn:3306/venn'
--       ,'table-name' = 'pv_uv'
--       ,'username' = 'root'
--       ,'password' = '123456'
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
    ,date_format(a.ts, 'yyyy-MM-dd HH:mm:ss')
    ,a.pv + COALESCE(c.pv,0) -- add last
    ,a.uv + COALESCE(c.uv,0)
    ,c.pv last_uv
    ,c.uv last_uv
from(
    select
     date_format(window_start, 'yyyy-MM-dd') cal_day
     ,behavior
     ,max(ts) ts
     ,max(proc_time) proc_time
     ,count(user_id) pv
     ,udaf_uv_count(user_id) uv
    FROM TABLE(
        CUMULATE(TABLE user_log, DESCRIPTOR(ts), INTERVAL '10' minute, INTERVAL '1' day))
      GROUP BY window_start, window_end, behavior
        )a
        left join user_log_lookup_join FOR SYSTEM_TIME AS OF a.proc_time AS c
                  ON  a.behavior = c.behavior
                      and udf_date_add(date_format(a.proc_time, 'yyyy-MM-dd HH:mm:ss'), -1) = c.cal_day
;

-- 直接 lookup join 加上昨天的 pv. uv use bloom filter count
-- select behavior,pv,uv
-- from pv_uv
-- group by behavior
