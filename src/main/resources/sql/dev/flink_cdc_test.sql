-- mysql cdc to print
-- creates a mysql table source
CREATE TABLE t_user_log (
                            category_id varchar(20),
                            behavior    varchar(20),
                            cnt         bigint
                                ,db_name STRING METADATA FROM 'database_name' VIRTUAL
  ,table_name STRING METADATA  FROM 'table_name' VIRTUAL
  ,operation_ts TIMESTAMP_LTZ(3) METADATA FROM 'op_ts' VIRTUAL
  ,proc_time as PROCTIME()
  ,PRIMARY KEY (category_id, behavior) NOT ENFORCED
) WITH (
 'connector' = 'mysql-cdc'
 ,'hostname' = 'localhost'
 ,'port' = '3306'
 ,'username' = 'root'
 ,'password' = '123456'
 ,'database-name' = 'venn'
 ,'table-name' = 'user_view'
 ,'server-id' = '5400-5440'
 ,'scan.startup.mode' = 'initial'
--  ,'scan.startup.mode' = 'specific-offset'
--  ,'scan.startup.specific-offset.file' = 'mysql-bin.000001'
--  ,'scan.startup.specific-offset.pos' = '1'
);

-- kafka sink
drop table if exists t_user_log_sink;
CREATE TABLE t_user_log_sink (
                                 category_id varchar(20),
                                 behavior    varchar(20),
                                 cnt         bigint
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

insert into t_user_log_sink
select category_id, behavior, cnt
from t_user_log;