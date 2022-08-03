drop table if exists mysql_behavior_conf ;
CREATE TEMPORARY TABLE mysql_behavior_conf (
   id int
  ,code STRING
  ,`value` STRING
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

-- 正常执行
select count(code) from mysql_behavior_conf;
-- 报错：语法错误， flink 不能解析 1 , 构造的 sql是这样的： select from mysql_behavior_config;
-- select count(1) from mysql_behavior_conf;
-- 报错：语法错误， flink 不能解析 * , 构造的 sql是这样的： select from mysql_behavior_config;
-- select count(*) from mysql_behavior_conf;
