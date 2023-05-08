-- kafka source   --- fail, proctime/rowtime 都不行
-- drop table if exists user_log;
CREATE TABLE if not exists user_log
(
    user_id     VARCHAR,
    behavior    VARCHAR,
    price       INT,
    ts          TIMESTAMP(3),
    WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
)
WITH (
    'connector' = 'kafka'
    ,'topic' = 'user_log'
    ,'properties.bootstrap.servers' = 'localhost:9092'
    ,'properties.group.id' = 'user_log'
    ,'scan.startup.mode' = 'latest-offset'
    ,'format' = 'json'
);
--
--
-- -- set table.sql-dialect=hive;
-- -- kafka sink
drop table if exists user_log_sink;
CREATE TABLE user_log_sink
(
    aid     STRING,
    bid     STRING,
    cid     STRING
) WITH (
      'connector' = 'print'
);


-- streaming sql, insert into mysql table
insert into user_log_sink
SELECT T.aid, T.bid, T.cid
FROM user_log
    MATCH_RECOGNIZE (
      PARTITION BY user_id
--       ORDER BY proctime -- rowtime
      MEASURES
        A.behavior AS aid,
        B.behavior AS bid,
        C.behavior AS cid
      PATTERN (A B C)
      DEFINE
        A AS price < 100,
        B AS price <= 2500,
        C AS price >2500
    ) AS T
