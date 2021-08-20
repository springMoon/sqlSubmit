-- Lookup Source: Sync Mode, hbase source will finish, when load all data to state
-- kafka source
CREATE TABLE user_log (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,ts TIMESTAMP(3)
--   ,process_time as proctime()
--   , WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_behavior'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

drop table if exists hbase_behavior_conf ;
CREATE TABLE hbase_behavior_conf (
   rowkey STRING
  ,cf ROW(item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,ts TIMESTAMP(3))
) WITH (
   'connector' = 'hbase-2.2'
   ,'zookeeper.quorum' = 'thinkpad:12181'
   ,'table-name' = 'user_log'
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
   'connector' = 'upsert-kafka'
  ,'topic' = 'user_behavior_1'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'key.format' = 'csv'
  ,'value.format' = 'csv'
);

INSERT INTO kakfa_join_mysql_demo(user_id, item_id, category_id, behavior, behavior_map, ts)
SELECT a.user_id, a.item_id, a.category_id, a.behavior, concat('map_', c.cf.item_id), a.ts
FROM user_log a
  left join hbase_behavior_conf c ON a.item_id = cf.item_id
where a.behavior is not null;
