-- mysql cdc to print
-- creates a mysql table source
drop table if exists t_feature;
CREATE TABLE t_feature (
  id varchar
  ,code VARCHAR
  ,send_time VARCHAR
  ,rms VARCHAR
  ,mean VARCHAR
  ,peak varchar
  ,kurtosis varchar
  ,skewness varchar
  ,proc_time as PROCTIME()
  ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
 'connector' = 'mysql-cdc',
 'hostname' = '10.201.1.132',
 'port' = '13306',
 'username' = 'root',
 'password' = '123456',
 'database-name' = 'test_db',
 'table-name' = 't_feature'
);

-- kafka sink
drop table if exists t_feature_sink;
CREATE TABLE t_feature_sink (
  id varchar
  ,code VARCHAR
  ,send_time VARCHAR
  ,rms VARCHAR
  ,mean VARCHAR
  ,peak varchar
  ,kurtosis varchar
  ,skewness varchar
) WITH (
  'connector' = 'print'
);

-- sink to kafka
insert into t_feature_sink
select id, code, send_time, rms, mean, peak, kurtosis, skewness
from t_feature;
