-- kafka source
drop table if exists user_log;
CREATE TABLE user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
  ,WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_log'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
);


drop table if exists user_log_2;
CREATE TABLE user_log_2 (
    user_id VARCHAR
    ,page as uuid()
    ,ts TIMESTAMP(3)
    ,WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );



-- set table.sql-dialect=hive;
-- kafka sink
drop table if exists user_log_sink;
CREATE TABLE user_log_sink (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
  ,page STRING
  ,ts timestamp(3)
) WITH (
  'connector' = 'print'
);


-- 5 s, interval join
-- insert into user_log_sink
-- SELECT a.user_id, a.item_id, a.category_id, a.behavior,b.page, a.ts
-- FROM user_log a
--          INNER JOIN user_log_2 b ON a.user_id = b.user_id
--             and a.ts BETWEEN b.ts - INTERVAL '5' SECOND AND b.ts + INTERVAL '5' SECOND
-- where a.user_id is not null
-- ;

-- join an agg
CREATE TABLE user_log_sink_2 (
    behavior STRING
    ,pv bigint
    ,uv bigint
    ,uv_cate bigint
    ,max_page STRING
    ,max_ts timestamp(3)
) WITH (
      'connector' = 'print'
      );

insert into user_log_sink_2
SELECT a.behavior, count(a.user_id) pv, count(distinct a.user_id) uv, count(distinct category_id) uv_cate, uuid() max_page
, max(a.ts) max_ts
FROM user_log a
         INNER JOIN user_log_2 b ON a.user_id = b.user_id
    and a.ts BETWEEN b.ts - INTERVAL '5' SECOND AND b.ts + INTERVAL '5' SECOND
where a.user_id is not null
group by a.behavior
;

