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
  ,'topic' = 'user_log'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  --,'scan.startup.mode' = 'group-offsets'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
);
-- kafka source
CREATE TABLE behavior_map (
  id STRING
  ,behavior STRING
  ,behavior_map STRING
  ,ts TIMESTAMP(3)
  ,process_time as proctime()
  , WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'behavior_map'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  --,'scan.startup.mode' = 'group-offsets'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
);

---sinkTable
--订单表 关联 产品表 成订购表
CREATE TABLE user_log_sink (
    user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,behavior_map STRING
  ,ts TIMESTAMP(3)
  ,map_id STRING
) WITH (
 'connector' = 'kafka'
  ,'topic' = 'user_behavior_sink'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'sink.partitioner' = 'fixed'
  ,'format' = 'json'
);
-- test data
-- log:
-- {"user_id":"1","item_id":"item_1","category_id":"cate_1","behavior":"behavior_1","behavior_map":"behavior_1_map","ts":"2021-12-10 13:59:37.936"}
-- {"user_id":"2","item_id":"item_2","category_id":"cate_2","behavior":"behavior_1","behavior_map":"behavior_1_map","ts":"2021-12-10 13:59:37.936"}
-- {"user_id":"3","item_id":"item_3","category_id":"cate_3","behavior":"behavior_1","behavior_map":"behavior_1_map","ts":"2021-12-10 13:59:37.936"}
-- {"user_id":"4","item_id":"item_4","category_id":"cate_4","behavior":"behavior_1","behavior_map":"behavior_1_map","ts":"2021-12-10 13:59:37.936"}
-- {"user_id":"5","item_id":"item_5","category_id":"cate_5","behavior":"behavior_1","behavior_map":"behavior_1_map","ts":"2021-12-10 13:59:37.936"}
-- {"user_id":"6","item_id":"item_6","category_id":"cate_6","behavior":"behavior_1","behavior_map":"behavior_1_map","ts":"2021-12-10 13:59:37.936"}
-- {"user_id":"7","item_id":"item_7","category_id":"cate_7","behavior":"behavior_1","behavior_map":"behavior_1_map","ts":"2021-12-10 13:59:37.936"}
--
-- map:
-- {"id":"1","behavior":"behavior_1","behavior_map":"behavior_1_map","ts":"2021-12-10 13:59:37.936"}
-- {"id":"2","behavior":"behavior_1","behavior_map":"behavior_2_map","ts":"2021-12-10 13:59:38.936"}
-- {"id":"3","behavior":"behavior_1","behavior_map":"behavior_3_map","ts":"2021-12-10 13:59:39.936"}
-- {"id":"4","behavior":"behavior_1","behavior_map":"behavior_4_map","ts":"2021-12-10 13:59:40.936"}

---order_sink
INSERT INTO user_log_sink
SELECT a.user_id,a.item_id,a.category_id,a.behavior,b.behavior_map,a.ts,b.id
FROM user_log a
  INNER JOIN behavior_map b ON a.behavior = b.behavior and a.ts BETWEEN b.ts - INTERVAL '10' MINUTE AND b.ts + INTERVAL '10' MINUTE
where a.user_id is not null;
