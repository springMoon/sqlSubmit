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

-- mysql source
-- Table 'JDBC:MySQL' declares metadata columns,
-- but the underlying DynamicTableSource doesn't implement the SupportsReadingMetadata interface.
-- Therefore, metadata cannot be read from the given source.
drop table if exists mysql_behavior_conf ;
CREATE TEMPORARY TABLE mysql_behavior_conf (
   id int
  ,col_1 STRING
  ,col_2 STRING
) WITH (
   'connector' = 'jdbc'
   ,'url' = 'jdbc:mysql://localhost:3306/venn'
   ,'table-name' = 'test_lookup_join'
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
  ,item_id STRING  -- join key
  ,category_id STRING
  ,behavior STRING
  ,id INT
  ,col_2 STRING
  ,ts TIMESTAMP(3)
  ,primary key (user_id) not enforced
) WITH (
   'connector' = 'print'
);


INSERT INTO kakfa_join_mysql_demo(user_id, item_id, category_id, behavior, id, col_2, ts)
SELECT a.user_id, a.item_id, a.category_id, a.behavior, c.id, c.col_2, a.ts
FROM user_log a
  left join mysql_behavior_conf FOR SYSTEM_TIME AS OF a.process_time AS c
  ON a.item_id = c.col_1
where a.behavior is not null;
