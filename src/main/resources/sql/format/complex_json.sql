-- flink json format, parse complex json
drop table if exists user_log;
CREATE TABLE user_log (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,sub_json ROW(sub_name STRING, password STRING, doub STRING)
) WITH (
   'connector' = 'kafka'
  ,'topic' = 'user_behavior_1'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log_1'
  ,'scan.startup.mode' = 'latest-offset'
  ,'format' = 'json'
  ,'json.ignore-parse-errors' = 'false'
);

-- set table.sql-dialect=hive;
-- kafka sink
drop table if exists mysql_table_venn_user_log_sink;
CREATE TABLE mysql_table_venn_user_log_sink (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,sub_name STRING
  ,password STRING
  ,doub STRING
) WITH (
  'connector' = 'print'
);

-- streaming sql, insert into mysql table
insert into mysql_table_venn_user_log_sink
SELECT user_id, item_id, category_id, sub_json.sub_name, sub_json.password, sub_json.doub
FROM user_log;
