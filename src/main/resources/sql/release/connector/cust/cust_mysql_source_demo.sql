create table cust_mysql_user_log
(
    user_id STRING,
    sex                      STRING,
    age                      INTEGER,
    degree                   STRING,
    address                  STRING,
    work_address             STRING,
    income_range             STRING,
    default_shipping_address STRING,
    register_date            TIMESTAMP(3)
) WITH (
      'connector' = 'cust-mysql'
      ,'url' = 'jdbc:mysql://10.201.0.166:3306/shell1?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true'
      ,'username' = 'root'
      ,'password' = 'daas2020'
      ,'database' = 'shell1'
      ,'table' = 'user_info'
      ,'key' = 'id'
      ,'batch.size' = '10000'
      )
;

create table cust_mysql_user_log_sink
(
    user_id STRING,
    sex                      STRING,
    age                      INTEGER,
    degree                   STRING,
    address                  STRING,
    work_address             STRING,
    income_range             STRING,
    default_shipping_address STRING,
    register_date            TIMESTAMP(3)
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log_sink'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
)
;

insert into cust_mysql_user_log_sink
select user_id, sex, age, degree, address, work_address, income_range,default_shipping_address, register_date
from cust_mysql_user_log;
