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
  ,'topic' = 'user_log'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
);

CREATE TEMPORARY TABLE redis_table (
  code STRING
  ,code2 STRING
  ,`value` STRING
) WITH (
   'connector' = 'cust-redis'
   ,'redis.url' = 'redis://localhost'
   ,'lookup.cache.max.size' = '28'
   ,'lookup.cache.expire.ms' = '5555' -- ttl time 超过这么长时间无数据才行
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
   'connector' = 'print'
);

---sink
-- INSERT INTO kakfa_join_mysql_demo(user_id, item_id, category_id, behavior, behavior_map, ts)
-- SELECT a.user_id, a.item_id, a.category_id, a.behavior, c.`value`, a.ts
-- FROM user_log a
--   left join redis_table FOR SYSTEM_TIME AS OF a.process_time AS c
--   ON  a.behavior = c.code
-- where a.behavior is not null;

INSERT INTO kakfa_join_mysql_demo(user_id, item_id, category_id, behavior, behavior_map, ts)
SELECT a.user_id, a.item_id, a.category_id, a.behavior, c.`value`, a.ts
FROM user_log a
         left join redis_table FOR SYSTEM_TIME AS OF a.process_time AS c
             -- todo parameter item_id cannot get in redis Source
                   ON  a.behavior = c.code and a.item_id = c.code2
where a.behavior is not null;
