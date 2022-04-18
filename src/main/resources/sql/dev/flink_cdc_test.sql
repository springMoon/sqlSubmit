-- mysql cdc to print
-- creates a mysql table source
CREATE TABLE t_user_log (
  id bigint
  ,user_id bigint
  ,item_id bigint
  ,category_id bigint
  ,behavior varchar
  ,ts timestamp(3)
  ,proc_time as PROCTIME()
  ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
 'connector' = 'mysql-cdc'
 ,'hostname' = 'localhost'
 ,'port' = '3306'
 ,'username' = 'root'
 ,'password' = '123456'
 ,'database-name' = 'venn'
 ,'table-name' = 'user_log'
 ,'server-id' = '5400-5440'
 ,'scan.startup.mode' = 'initial'
--  ,'scan.startup.mode' = 'specific-offset'
--  ,'scan.startup.specific-offset.file' = 'mysql-bin.000001'
--  ,'scan.startup.specific-offset.pos' = '1'
);

-- kafka sink
drop table if exists t_user_log_sink;
CREATE TABLE t_user_log_sink (
  id bigint
  ,user_id bigint
  ,item_id bigint
  ,category_id bigint
  ,behavior varchar
  ,ts timestamp(3)
  ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
    'connector' = 'print'
--    'connector' = 'upsert-kafka'
--   ,'topic' = 'user_log_sink'
--   ,'properties.bootstrap.servers' = 'localhost:9092'
--   ,'properties.group.id' = 'user_log'
--   ,'key.format' = 'json'
--   ,'key.json.ignore-parse-errors' = 'true'
--   ,'value.format' = 'json'
--   ,'value.json.fail-on-missing-field' = 'false'
--   ,'value.fields-include' = 'ALL'
);

-- insert into t_user_log_sink
-- select id, user_id, item_id, category_id, behavior, ts
-- from t_user_log;

create table t_user_log_sink_2(
    ts timestamp(3)
    ,min_id bigint
    ,max_id bigint
    ,coun bigint
)with (
    'connector' = 'print'
);

insert into t_user_log_sink_2
select max(ts),min(id),max(id),count(id)
from t_user_log;

o