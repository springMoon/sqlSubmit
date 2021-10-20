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
  --,'scan.startup.mode' = 'group-offsets'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
);

create temporary table hbase_table_config(
  rowkey string
  ,cf ROW(code string, `value` string, update_time string)
  ,cf2 ROW(code string, `value` string, update_time string)
)WITH(
 'connector' = 'cust-hbase'
 ,'hbase.zookeeper.quorum' = 'thinkpad:12181'
 ,'zookeeper.znode.parent' = '/hbase'
 ,'hbase.tablename' = 'hbase_table_config'
 ,'hbase.null-string-literal' = 'null'
 ,'hbase.lookup.key' = 'cf:code,cf2:code'
 ,'hbase.lookup.cache.max.size' = '100'
 ,'hbase.lookup.cache.expire.ms' = '6'
 ,'hbase.lookup.max.retry.times' = '3'
 ,'hbase.timeout' = '10'
)
;

---sinkTable
CREATE TABLE kakfa_join_mysql_demo (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,rowkey STRING
  ,behavior_map STRING
  ,behavior_map2 STRING
  ,ts TIMESTAMP(3)
  ,primary key (user_id) not enforced
) WITH (
   'connector' = 'print'
);

INSERT INTO kakfa_join_mysql_demo(user_id, item_id, category_id, behavior,rowkey, behavior_map, behavior_map2, ts)
SELECT a.user_id, a.item_id, a.category_id, a.behavior,c.rowkey, c.cf.`value`, c.cf2.`value`,a.ts
FROM user_log a
  left join hbase_table_config FOR SYSTEM_TIME AS OF a.process_time AS c
  -- 必须要一个key 做关联条件，实际上不会用这个做关联
  -- lookup join 不能 join 两个字段 。。。。。吐血
  ON a.behavior = c.rowkey -- and a.category_id = c.rowkey
where a.behavior is not null;
