-- mysql cdc to print
-- creates a mysql table source
drop table if exists cdc_mysql_venn_user_log;
CREATE TABLE cdc_mysql_venn_user_log (
  id bigint
  ,code VARCHAR
  ,rms DOUBLE
  ,mean DOUBLE
  ,peak DOUBLE
  ,kurtosis DOUBLE
  ,skewness DOUBLE
  ,send_time TIMESTAMP(3)
  ,proc_time as PROCTIME()
  ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
 'connector' = 'mysql-cdc',
 'hostname' = '10.201.0.168',
 'port' = '3306',
 'username' = 'root',
 'password' = 'daas2020',
 'database-name' = 'stepping_stone',
 'table-name' = 't_feature'
);

-- kafka sink
drop table if exists cdc_mysql_user_log_sink;
CREATE TABLE cdc_mysql_user_log_sink (
  id bigint
  ,code VARCHAR
  ,rms DOUBLE
  ,mean DOUBLE
  ,peak DOUBLE
  ,kurtosis DOUBLE
  ,skewness DOUBLE
  ,send_time TIMESTAMP(3)
) WITH (
  'connector' = 'print'
);

-- sink to kafka
insert into cdc_mysql_user_log_sink
select id, code, rms, mean, peak, kurtosis, skewness, send_time
from cdc_mysql_venn_user_log;
