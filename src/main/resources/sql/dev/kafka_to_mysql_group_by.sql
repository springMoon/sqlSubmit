-- calc pv
-- test multi column primary key update mysql

-- kafka source
drop table if exists user_log;
CREATE TABLE user_log
(
    `event_time` TIMESTAMP(3) METADATA FROM 'timestamp' VIRTUAL,  -- from Debezium format
    `partition_id` BIGINT METADATA FROM 'partition' VIRTUAL,  -- from Kafka connector
    `offset` BIGINT METADATA VIRTUAL,  -- from Kafka connector
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

-- set table.sql-dialect=hive;
-- kafka sink
CREATE TABLE user_log_sink
(
    category_id varchar(20),
    behavior    varchar(20),
    cnt         bigint,
    primary key(category_id, behavior) NOT ENFORCED
) WITH (
      'connector' = 'jdbc'
      ,'url' = 'jdbc:mysql://localhost:3306/venn'
      ,'table-name' = 'user_view'
      ,'username' = 'root'
      ,'password' = '123456'
      );


-- streaming sql, insert into mysql table
insert into user_log_sink
SELECT category_id, behavior, count(user_id) cnt
FROM user_log
group by category_id, behavior;
