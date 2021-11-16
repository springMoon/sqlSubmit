-- kafka source
drop table if exists user_log;
CREATE TABLE  user_log (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts timestamp(3)
  , WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_behavior'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);


---sinkTable
drop table if exists user_log_sink;
CREATE TABLE user_log_sink (
   user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts timestamp(3)
) WITH (
 'connector' = 'kafka'
  ,'topic' = 'user_behavior_sink'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'

);

---order_sink
INSERT INTO user_log_sink
SELECT a.user_id, count(a.user_id)
FROM user_log a
where date_format(ts,'yyyy-MM-dd') >=  date_format(LOCALTIMESTAMP, 'yyyy-MM-dd')
group by TUMBLE(ts, INTERVAL '10' second),a.user_id
--where DATE_FORMAT(ts, 'yyyyMMdd') >=  date_format(LOCALTIMESTAMP, 'yyyy-MM-dd')
;

SELECT date_format(ts,'yyyy-MM-dd'), count(a.user_id)
FROM user_log a
where date_format(ts,'yyyy-MM-dd') >=  date_format(LOCALTIMESTAMP, 'yyyy-MM-dd')
group by date_format(ts,'yyyy-MM-dd')
--where DATE_FORMAT(ts, 'yyyyMMdd') >=  date_format(LOCALTIMESTAMP, 'yyyy-MM-dd')
;