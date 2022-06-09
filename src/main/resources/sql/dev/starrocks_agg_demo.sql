-- starrocks to print

-- mini batch invalid
set table.exec.mini-batch.enabled=true;
set table.exec.mini-batch.allow-latency=20 s;
set table.exec.mini-batch.size=100;


CREATE TABLE user_log (
   id          bigint
    ,user_id     bigint
    ,item_id     bigint
    ,category_id bigint
    ,behavior    string
    ,ts          timestamp(3)
    ,create_time timestamp(3)
) WITH (
   'connector' = 'jdbc'
   ,'url' = 'jdbc:mysql://10.201.0.230:19030/hive'
   ,'table-name' = 'user_log'
   ,'username' = 'root'
   ,'password' = '123456'
   -- ,'scan.partition.column' = 'id'
   -- ,'scan.partition.num' = '5'
   -- ,'scan.partition.lower-bound' = '-999999999'
   -- ,'scan.partition.upper-bound' = '999999999'
   -- ,'lookup.cache.max-rows' = '28'
   -- ,'lookup.cache.ttl' = '5555' -- ttl time 超过这么长时间无数据才行
);
CREATE TABLE user_log_sink (
    item_id     bigint
    ,category_id bigint
    ,behavior    string
    ,pv          bigint
    ,uv          bigint
) WITH (
   'connector' = 'print'
);

insert into user_log_sink
select
    item_id
,category_id
,behavior
,count(id)
,count(distinct user_id)
from user_log
group by item_id,category_id,behavior
