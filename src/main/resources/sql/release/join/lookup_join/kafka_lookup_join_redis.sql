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
  `key` STRING
  ,filed STRING
  ,`value` STRING
) WITH (
   'connector' = 'cust-redis'
   ,'redis.url' = 'redis://localhost:6379?timeout=3000'
   ,'lookup.cache.max.size' = '28'
   ,'lookup.cache.expire.ms' = '3600000' -- ttl time 超过这么长时间无数据才行
--     ,'pass' = '11' -- todo test
);

---sinkTable
CREATE TABLE kakfa_join_redis_sink (
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
-- sting/list/set/zset test sql
-- INSERT INTO kakfa_join_redis_sink(user_id, item_id, category_id, behavior, behavior_map, ts)
-- SELECT a.user_id, a.item_id, a.category_id, a.behavior, b.`value`, a.ts
-- FROM user_log a
--          left join redis_table FOR SYSTEM_TIME AS OF a.process_time AS b
--                    ON a.behavior = b.`key`
-- where a.behavior is not null;

CREATE TABLE kakfa_join_redis_sink_1 (
                                       user_id STRING
    ,item_id STRING
    ,category_id STRING
    ,behavior STRING
    ,behavior_key STRING
    ,behavior_map STRING
    ,ts TIMESTAMP(3)
    ,primary key (user_id) not enforced
) WITH (
      'connector' = 'print'
      )
    ;


-- hash multiple input
INSERT INTO kakfa_join_redis_sink_1(user_id, item_id, category_id, behavior, behavior_key,behavior_map, ts)
SELECT a.user_id, a.item_id, a.category_id, a.behavior,b.filed, b.`value`, a.ts
FROM user_log a
         left join redis_table FOR SYSTEM_TIME AS OF a.process_time AS b
                   ON  a.behavior = b.key
where a.behavior is not null;

-- INSERT INTO kakfa_join_redis_sink_1(user_id, item_id, category_id, behavior, behavior_key,behavior_map, ts)
-- SELECT a.user_id, a.item_id, a.category_id, a.behavior,b.filed, b.`value`, a.ts
-- FROM user_log a
--          left join redis_table FOR SYSTEM_TIME AS OF a.process_time AS b
--                    ON  a.behavior = b.key and a.item = b.filed
-- where a.behavior is not null;