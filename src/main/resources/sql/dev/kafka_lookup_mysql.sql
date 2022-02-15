-- flink lookup mysql test
-- kafka source
drop table if exists user_log;
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
  ,process_time as proctime()
  ,WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_log'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
);


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

drop table if exists mysql_behavior_conf_2 ;
CREATE TEMPORARY TABLE mysql_behavior_conf_2 (
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

-- set table.sql-dialect=hive;
-- kafka sink
drop table if exists print_sink;
CREATE TABLE print_sink (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,behavior_map STRING
  ,behavior_map2 STRING
  ,ts timestamp(3)
) WITH (
  'connector' = 'print'
);


-- streaming sql
-- INSERT INTO print_sink(user_id, item_id, category_id, behavior, behavior_map, behavior_map2, ts)
-- SELECT a.user_id, a.item_id, a.category_id, a.behavior, c.`value`, d.`value`, a.ts
-- FROM user_log a
--   left join mysql_behavior_conf FOR SYSTEM_TIME AS OF a.process_time AS
--      c ON  a.behavior = c.code
--   left join mysql_behavior_conf_2 FOR SYSTEM_TIME AS OF a.process_time AS
--      d ON  a.behavior = d.code
-- where a.behavior is not null;

-- -- 非 lookup join 不能关联到数据
INSERT INTO print_sink(user_id, item_id, category_id, behavior, behavior_map, behavior_map2, ts)
SELECT a.user_id, a.item_id, a.category_id, a.behavior, c.`value`, d.`value`, a.ts
FROM user_log a
  left join mysql_behavior_conf --FOR SYSTEM_TIME AS OF a.process_time AS
     c ON  a.behavior = c.code
  left join mysql_behavior_conf_2 --FOR SYSTEM_TIME AS OF a.process_time AS
     d ON  a.behavior = d.code
where a.behavior is not null;

