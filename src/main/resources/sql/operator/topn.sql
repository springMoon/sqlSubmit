-- Top N 查询
-- kafka source
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior INT
  ,sales DOUBLE
  ,sort_col int
  ,ts TIMESTAMP(3)
  ,process_time as proctime()
  , WATERMARK FOR ts AS ts
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_behavior'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

---sink table
CREATE TABLE user_log_sink (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior INT
  ,sales DOUBLE
  ,sort_col INT
  ,ts TIMESTAMP(3)
  ,num bigint
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

-- insert
insert into user_log_sink(user_id, item_id, category_id,behavior,sales,ts,sort_col,rownum)
SELECT user_id, item_id, category_id,behavior,sales,sort_col,ts,rownum
FROM (
   SELECT user_id, item_id, category_id,behavior,sales, ts, sort_col,
     ROW_NUMBER() OVER (PARTITION BY category_id ORDER BY ts desc) AS rownum
   FROM user_log)
-- WHERE rownum < sort_col
WHERE rownum < 6
-- 只支持两种 top n:
--  rownum < 10 or rownum > 3 and rownum < 10
--  rownum < source_table.column
-- rownum > 3 是不支持的 Rank end is not specified. Currently rank only support TopN, which means the rank end must be specified.
-- 不输出 rownum 可以启动无排名优化，仅输出当前数据，对历史数据的排名更新，不再输出