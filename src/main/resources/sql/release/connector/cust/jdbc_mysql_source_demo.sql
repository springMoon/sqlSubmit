create table user_log
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
      'connector' = 'jdbc'
      ,'url' = 'jdbc:mysql://10.201.0.166:3306/shell1?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true'
      ,'username' = 'root'
      ,'password' = 'daas2020'
      ,'table-name' = 'user_info'
      ,'scan.partition.column' = 'id'
      ,'scan.partition.num' = '10000'
      ,'scan.partition.lower-bound' = '0'
      ,'scan.partition.upper-bound' = '9999999999'
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
from user_log;
