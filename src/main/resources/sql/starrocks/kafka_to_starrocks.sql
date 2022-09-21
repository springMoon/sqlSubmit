drop table if exists user_log;
CREATE TABLE user_log (
    user_id VARCHAR
    ,item_id VARCHAR
    ,category_id VARCHAR
    ,behavior VARCHAR
    ,proc_time as PROCTIME()
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


drop table if  exists  starrocks_sink;
create table if not exists starrocks_sink (
    `col1` string
    ,`col2` string
    ,`col3` string
    ,`col4` string
    ,PRIMARY key(col1) NOT ENFORCED
) WITH (
          'connector'='starrocks',
          'load-url'='172.31.0.13:8030',
          'jdbc-url'='jdbc:mysql://172.31.0.13:9030',
          'username'='develop',
          'password'='Develo123p@',
          'database-name'='test',
          'table-name'='datagen_key',
      'sink.buffer-flush.max-rows' = '1000000',
      'sink.buffer-flush.max-bytes' = '300000000',
      'sink.buffer-flush.interval-ms' = '5000'
    ,'sink.properties.format' = 'json'
    ,'sink.properties.strip_outer_array' = 'true'
);

insert into starrocks_sink
select user_id, item_id, category_id, behavior from user_log ;
