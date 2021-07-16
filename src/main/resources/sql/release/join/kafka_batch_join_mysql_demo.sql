-- Scan Source: Bounded 虽然是 join， 但也是 bounded 模式，mysql 表也是一次读完，就结束了
-- kafka source
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_behavior'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

-- mysql source
drop table if exists item ;
CREATE TABLE mysql_behavior_conf (
    id int
  ,behavior VARCHAR
  ,behavior_map VARCHAR
  ,update_time TIMESTAMP(3)
  ,primary key (id) not enforced
) WITH (
   'connector' = 'jdbc'
   ,'url' = 'jdbc:mysql://venn:3306/venn'
   ,'table-name' = 'behavior_conf'
   ,'username' = 'root'
   ,'password' = '123456'
--    ,'lookup.cache.max-rows' = '1000'
--    ,'lookup.cache.ttl' = '2 minute'
);

---sinkTable
CREATE TABLE kakfa_join_mysql_demo (
    user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
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

---order_sink
INSERT INTO kakfa_join_mysql_demo(user_id, item_id, category_id, behavior, behavior_map, ts)
SELECT a.user_id, a.item_id, a.category_id, a.behavior, b.behavior_map, a.ts
FROM user_log a
  left join mysql_behavior_conf b ON a.behavior = b.behavior
where a.behavior is not null;
