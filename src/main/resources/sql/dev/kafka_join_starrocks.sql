--- lookup join starrocks

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

drop table if exists starrocks_user_log ;
CREATE TEMPORARY TABLE starrocks_user_log (
   behavior string
    ,coun bigint

) WITH (
   'connector' = 'cust-starrocks'
   ,'url' = 'jdbc:mysql://10.201.0.230:19030/hive'
   ,'sql' = 'select behavior,count(1) coun from hive.user_log where behavior = ? group by behavior'
   ,'username' = 'root'
   ,'password' = '123456'
   ,'lookup.cache.max.size' = '28'
   ,'lookup.cache.expire.ms' = '5555' -- ttl time 超过这么长时间无数据才行
);


-- set table.sql-dialect=hive;
-- kafka sink
drop table if exists user_log_sink;
CREATE TABLE user_log_sink
(
    user_id     VARCHAR,
    item_id     VARCHAR,
    category_id VARCHAR,
    behavior    VARCHAR,
    ts          TIMESTAMP(3),
    coun        bigint
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
select user_id, item_id, category_id, t1.behavior, ts, coun
from user_log t1
    left join starrocks_user_log FOR SYSTEM_TIME AS OF t1.proc_time AS t2 on t1.behavior = t2.behavior
