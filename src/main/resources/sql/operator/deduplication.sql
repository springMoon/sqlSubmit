-- 去重查询
-- kafka source
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior INT
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
  ,ts TIMESTAMP(3)
  ,num BIGINT
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
insert into user_log_sink(user_id, item_id, category_id,behavior,ts,num)
SELECT user_id, item_id, category_id,behavior,ts,rownum
FROM (
   SELECT user_id, item_id, category_id,behavior,ts,
     ROW_NUMBER() OVER (PARTITION BY category_id ORDER BY ts desc) AS rownum -- desc use the latest one,
   FROM user_log)
WHERE rownum=1
-- 只能使用 rownum=1，如果写 rownum=2（或<10） 会识别为 top n