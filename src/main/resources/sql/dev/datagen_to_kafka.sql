-- kafka source
-- drop table if exists user_log;
CREATE TABLE user_log
(
    user_id     VARCHAR,
    item_id     VARCHAR,
    category_id VARCHAR,
    behavior    VARCHAR
) WITH (
      'connector' = 'datagen'
      ,'rows-per-second' = '20'
      ,'number-of-rows' = '10000'
      ,'fields.user_id.kind' = 'random'
      ,'fields.item_id.kind' = 'random'
      ,'fields.category_id.kind' = 'random'
      ,'fields.behavior.kind' = 'random'
      ,'fields.user_id.length' = '20'
      ,'fields.item_id.length' = '10'
      ,'fields.category_id.length' = '10'
      ,'fields.behavior.length' = '10'
      );
--
--
-- -- set table.sql-dialect=hive;
-- -- kafka sink
-- drop table if exists user_log_sink;
CREATE TABLE user_log_sink
(
    user_id     STRING,
    item_id     STRING,
    category_id STRING,
    behavior    STRING
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log_test_20230309'
      -- ,'properties.bootstrap.servers' = 'host.docker.internal:9092'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );


-- streaming sql, insert into mysql table
insert into user_log_sink
SELECT user_id, item_id, category_id, behavior
FROM user_log;
