-- mysql cdc to print
-- creates a mysql table source
drop table if exists cdc_mysql_venn_user_log;
CREATE TABLE cdc_mysql_venn_user_log (
  id varchar
  ,user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
  ,proc_time as PROCTIME()
  ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
 'connector' = 'mysql-cdc',
 'hostname' = 'localhost',
 'port' = '3306',
 'username' = 'root',
 'password' = '123456',
 'database-name' = 'venn',
 'table-name' = 'user_log_1'
);

-- kafka sink
CREATE TABLE cdc_print (
  id varchar
  ,user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
) WITH (
  'connector' = 'print'
);


-- sink to print
insert into cdc_print
select id, user_id, item_id, category_id, behavior, ts
from cdc_mysql_venn_user_log;



-- CREATE TABLE  cdc_kafka (
--     user_id VARCHAR
--     ,item_id VARCHAR
--     ,category_id VARCHAR
--     ,behavior VARCHAR
--     ,ts timestamp(3)
-- ) WITH (
--       'connector' = 'upsert-kafka'
--       ,'topic' = 'user_behavior'
--       ,'properties.bootstrap.servers' = 'localhost:9092'
--       ,'properties.group.id' = 'user_log'
--       ,'scan.startup.mode' = 'group-offsets'
--       ,'format' = 'json'
--       );
--
-- insert into cdc_kafka
-- select  user_id, item_id, category_id, behavior, ts
-- from cdc_mysql_venn_user_log;
