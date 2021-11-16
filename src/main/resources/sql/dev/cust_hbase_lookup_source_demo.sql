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
 ,'hbase.lookup.key' = 'cf:code'
--  ,'hbase.lookup.key' = 'cf:code,cf2:code'
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
<<<<<<< HEAD
  -- 必须要一个key 做关联条件，实际上不会用这个做关联
  -- lookup join may hive multiple join condition
  -- flink 只能解析外层的字段，所以折中一下，使用配置和拼接的方式确定hbase筛选字段和传入参数
=======
  -- on a.user_id = c.cf.code and a.item_id = c.cf2.code
  -- 必须要一个key 做关联条件，实际上不会用这个做关联条件
>>>>>>> dev
  -- 流输入端的字段使用 ',' 拼接的方式传入参数
  -- hbase 端通过参数 'hbase.lookup.key' = 'cf:code,cf2:code' 传入过滤的字段，两边必须的数量必须匹配
  ON  a.behavior = c.rowkey
--   ON  concat(a.behavior,',',a.category_id) = c.rowkey --and a.item_id = c.cf.`code`
where a.behavior is not null;
