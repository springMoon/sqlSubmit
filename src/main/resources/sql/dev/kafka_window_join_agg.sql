--- window agg, export hold behavior
/**

用flinksql求每个app_code的pv、uv，1小时的窗口，30秒刷新一次结果，
  要求每个小时每个app_code都有统计结果（用0填充）。
  填充这个问题没弄好，本来准备把24小时与所有app_code进行full join，
  再left join流表，但这样会提交不上checkpoint。有没有大佬支个招，版本flink 1.11.1

  挑战失败，

  mysql 为主表，无法触发计算，改用 stream api

 */


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
      ,'scan.startup.mode' = 'earliest-offset'
      ,'format' = 'json'
      );

drop table if exists mysql_behavior_conf ;
CREATE TEMPORARY TABLE mysql_behavior_conf (
   id int
  ,code STRING
  ,`value` STRING
  ,`key` STRING
  ,update_time TIMESTAMP(3)
--   ,primary key (id) not enforced
--   ,WATERMARK FOR update_time AS update_time - INTERVAL '5' SECOND
) WITH (
   'connector' = 'jdbc'
   ,'url' = 'jdbc:mysql://localhost:3306/venn'
   ,'table-name' = 'lookup_join_config'
   ,'username' = 'root'
   ,'password' = '123456'
   ,'scan.partition.column' = 'id'
   ,'scan.partition.num' = '5'
   ,'scan.partition.lower-bound' = '5'
   ,'scan.partition.upper-bound' = '99999'
   ,'lookup.cache.max-rows' = '28'
   ,'lookup.cache.ttl' = '5555' -- ttl time 超过这么长时间无数据才行
);


-- set table.sql-dialect=hive;
-- kafka sink
drop table if exists user_log_sink;
CREATE TABLE user_log_sink
(
    item_id     STRING
    ,behavior    STRING
    ,coun    bigint
    ,hou string
    ,window_start timestamp(3)
    ,window_end timestamp(3)
) WITH (
      'connector' = 'print'
--       ,'topic' = 'user_log_sink_6'
--       ,'properties.bootstrap.servers' = 'localhost:9092'
--       ,'properties.group.id' = 'user_log'
--       ,'scan.startup.mode' = 'latest-offset'
--       ,'format' = 'json'
      );


-- streaming sql, insert into mysql table
insert into user_log_sink
select t1.item_id, t2.code, ifnull(coun, 0) coun, t1.`hour`, t1.window_start, t1.window_end
from mysql_behavior_conf t2
    left join (SELECT item_id, behavior, count(1) coun, window_start, window_end, DATE_FORMAT(window_start, 'HH') `hour`,max(proc_time) proc_time
     FROM TABLE(
                  TUMBLE(TABLE user_log, DESCRIPTOR(ts), INTERVAL '1' MINUTES )) t1
     group by item_id, behavior, window_start, window_end
    )t1 on t1.behavior = t2.code
--     left join mysql_behavior_conf FOR SYSTEM_TIME AS OF t1.proc_time AS t2 on t1.behavior = t2.code
;
