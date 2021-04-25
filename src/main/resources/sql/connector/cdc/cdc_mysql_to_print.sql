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
 'table-name' = 'user_log'
);

-- kafka sink
drop table if exists cdc_mysql_user_log_sink;
CREATE TABLE cdc_mysql_user_log_sink (
  id varchar
  ,user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
) WITH (
  'connector' = 'print'
);

-- sink to kafka
insert into cdc_mysql_user_log_sink
select id, user_id, item_id, category_id, behavior, ts
from cdc_mysql_venn_user_log;
