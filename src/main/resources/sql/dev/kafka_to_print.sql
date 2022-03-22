-- kafka source
CREATE TABLE user_log (
  user_id string
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_log_x'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
);

drop table if exists hbase_user_log_sink ;
CREATE TABLE hbase_user_log_sink (
   user_id STRING
) WITH (
   'connector' = 'print'
);

insert into hbase_user_log_sink
select * from (
                  select split_index(REGEXP_REPLACE(user_id, '[|]', ''),',') aa
                  from user_log
              );