-- calc pv
-- test multi column primary key update mysql

-- kafka source
drop table if exists user_log_1;
CREATE TABLE user_log_1
(
    user_id     VARCHAR,
    item_id     VARCHAR,
    category_id VARCHAR,
    behavior    VARCHAR,
    ts          TIMESTAMP(3),
    WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );

-- kafka sink
CREATE TABLE user_log_sink_1
(
    user_id     VARCHAR,
    item_id     VARCHAR,
    category_id VARCHAR,
    primary key(user_id) NOT ENFORCED
) WITH (
      'connector' = 'jdbc'
      ,'url' = 'jdbc:mysql://localhost:3306/venn'
      ,'table-name' = 'user_info_sink'
      ,'username' = 'root'
      ,'password' = '123456'
      );

drop table if exists user_log_2;
CREATE TABLE user_log_2
(
    user_id     VARCHAR,
    item_id     VARCHAR,
    category_id VARCHAR,
    behavior    VARCHAR,
    ts          TIMESTAMP(3),
    WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log_2'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );
-- kafka sink
CREATE TABLE user_log_sink_2
(
    user_id     VARCHAR,
    behavior    VARCHAR,
    ts          TIMESTAMP(3),
    primary key(user_id) NOT ENFORCED
) WITH (
--       'connector' = 'print'
      'connector' = 'jdbc'
      ,'url' = 'jdbc:mysql://localhost:3306/venn?serverTimezone=GMT%2B8'
      ,'table-name' = 'user_info_sink'
      ,'username' = 'root'
      ,'password' = '123456'
      );


-- streaming sql, insert into mysql table
insert into user_log_sink_1
SELECT user_id, item_id, category_id
FROM user_log_1
;

insert into user_log_sink_2
select user_id, behavior, ts
from user_log_2;