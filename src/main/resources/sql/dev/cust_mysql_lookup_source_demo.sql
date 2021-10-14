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

create temporary table mysql_behavior_conf(
   id int
  ,code STRING
  ,`value` STRING
  ,update_time TIMESTAMP(3)
)WITH(
 'connector' = 'cust-mysql'
 ,'mysql.url' = 'jdbc:mysql://localhost:3306/venn?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true'
 ,'mysql.username' = 'root'
 ,'mysql.password' = '123456'
 ,'mysql.database' = 'venn'
 ,'mysql.table' = 'lookup_join_config'
 ,'format' = 'csv'
 ,'mysql.lookup.cache.max.size' = '1'
 ,'mysql.lookup.cache.expire.ms' = '600000'
 ,'mysql.lookup.max.retry.times' = '3'
 ,'mysql.timeout' = '10'
)
;

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

INSERT INTO kakfa_join_mysql_demo(user_id, item_id, category_id, behavior, behavior_map, ts)
SELECT a.user_id, a.item_id, a.category_id, a.behavior, c.`value`, a.ts
FROM user_log a
  left join mysql_behavior_conf FOR SYSTEM_TIME AS OF a.process_time AS c
  ON a.behavior = c.code
where a.behavior is not null;
