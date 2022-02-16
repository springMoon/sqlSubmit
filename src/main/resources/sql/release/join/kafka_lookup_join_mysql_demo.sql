-- Lookup Source: Sync Mode
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
  ,'topic' = 'user_behavior'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
);

-- mysql source
-- Table 'JDBC:MySQL' declares metadata columns,
-- but the underlying DynamicTableSource doesn't implement the SupportsReadingMetadata interface.
-- Therefore, metadata cannot be read from the given source.
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

---sinkTable
CREATE TABLE kakfa_join_mysql_demo (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,behavior_map STRING
  ,ts TIMESTAMP(3)
  ,primary key (user_id) not enforced
) WITH (
   'connector' = 'print'
);

---sink
-- 时态表 join 和 一般的 join 的效果看起来并没有什么不一样的（user_log 表需要去掉 事件事件属性），维表都是一次性读取，然后 finish
-- INSERT INTO kakfa_join_mysql_demo(user_id, item_id, category_id, behavior, behavior_map, ts)
-- SELECT a.user_id, a.item_id, a.category_id, a.behavior, b.behavior_map, a.ts
-- FROM user_log a
--   left join mysql_behavior_conf b on a.behavior = b.id
-- where a.behavior is not null;

INSERT INTO kakfa_join_mysql_demo(user_id, item_id, category_id, behavior, behavior_map, ts)
SELECT a.user_id, a.item_id, a.category_id, a.behavior, c.`value`, a.ts
FROM user_log a
  left join mysql_behavior_conf FOR SYSTEM_TIME AS OF a.process_time AS c
  ON  a.behavior = c.code and a.item_id = c.`value`
where a.behavior is not null;
