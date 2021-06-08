-- Lookup Source: Sync Mode
-- kafka source
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior INT
  ,ts TIMESTAMP(3)
  ,process_time as proctime()
  , WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_behavior'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

-- mysql source
drop table if exists mysql_behavior_conf ;
CREATE TABLE mysql_behavior_conf (
    id int
  ,code VARCHAR
  ,map_val VARCHAR
  ,update_time TIMESTAMP(3)
  ,process_time as proctime()
  ,primary key (id) not enforced
  ,WATERMARK FOR update_time AS update_time - INTERVAL '5' SECOND
) WITH (
   'connector' = 'jdbc'
   ,'url' = 'jdbc:mysql://venn:3306/venn'
   ,'table-name' = 'behavior_conf'
   ,'username' = 'root'
   ,'password' = '123456'
   ,'scan.partition.column' = 'id'
   ,'scan.partition.num' = '10'
   ,'scan.partition.lower-bound' = '0'
   ,'scan.partition.upper-bound' = '9999'
   ,'lookup.cache.max-rows' = '1000'
   ,'lookup.cache.ttl' = '2 minute'
);

---sinkTable
CREATE TABLE kakfa_join_mysql_demo (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior INT
  ,behavior_map VARCHAR
  ,ts TIMESTAMP(3)
  ,primary key (user_id) not enforced
) WITH (
'connector' = 'upsert-kafka'
  ,'topic' = 'user_behavior_sink'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'key.format' = 'json'
  ,'key.json.ignore-parse-errors' = 'true'
  ,'value.format' = 'json'
  ,'value.json.fail-on-missing-field' = 'false'
  ,'value.fields-include' = 'ALL'
);

---sink
-- 时态表 join 和 一般的 join 的效果看起来并没有什么不一样的（user_log 表需要去掉 事件事件属性），维表都是一次性读取，然后 finish
-- INSERT INTO kakfa_join_mysql_demo(user_id, item_id, category_id, behavior, behavior_map, ts)
-- SELECT a.user_id, a.item_id, a.category_id, a.behavior, b.behavior_map, a.ts
-- FROM user_log a
--   left join mysql_behavior_conf b on a.behavior = b.id
-- where a.behavior is not null;

INSERT INTO kakfa_join_mysql_demo(user_id, item_id, category_id, behavior, behavior_map, ts)
SELECT a.user_id, a.item_id, a.category_id, a.behavior, b.behavior_map, a.ts
FROM user_log a
  left join mysql_behavior_conf for system_time as of a.ts as b on a.behavior = b.id
where a.behavior is not null;
