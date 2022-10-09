-- kafka source
drop table if exists user_log;
CREATE TABLE user_log (
  arr ARRAY<STRING>
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_arr'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_arr'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
);


-- set table.sql-dialect=hive;
-- kafka sink
drop table if exists user_log_sink;
CREATE TABLE user_log_sink (
  arr ARRAY<STRING>
  , a string
  ,b  string
) WITH (
  'connector' = 'print'
);


-- streaming sql, insert into mysql table
insert into user_log_sink
SELECT arr, arr[1], arr[2]
from user_log
;