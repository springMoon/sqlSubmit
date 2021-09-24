-- kafka source
CREATE TABLE user_log (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,ts TIMESTAMP(3)
  ,process_time as proctime()
--   , WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_behavior'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

---sinkTable
CREATE TABLE join_hbbase_sink (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,ts TIMESTAMP(3)
  ,rowkey STRING
  ,c1 STRING
  ,c2 STRING
  ,c3 STRING
  ,c4 STRING
  ,primary key (user_id) not enforced
) WITH (
   'connector' = 'upsert-kafka'
  ,'topic' = 'user_behavior_1'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'key.format' = 'csv'
  ,'value.format' = 'csv'
);

INSERT INTO join_hbbase_sink
SELECT a.user_id, a.item_id, a.category_id, a.behavior, a.ts, t2.col[1], t2.col[2], t2.col[3], t2.col[4], t2.col[5]
FROM user_log a
-- left join lateral table(udf_join_hbase_non_rowkey_no_cache(item_id)) as t2(col) on true
left join lateral table(udf_join_hbase_non_rowkey_cache(item_id)) as t2(col) on true
where a.item_id is not null
-- and t2.col[1] = a.item_id -- 返回多条数据可以在 where 条件后面过滤
;
