-- Lookup Source: Sync Mode
-- kafka source
CREATE TABLE user_info
(
    user_id                  STRING,
    sex                      STRING,
    age                      INTEGER,
    degree                   STRING,
    address                  STRING,
    work_address             STRING,
    income_range             STRING,
    default_shipping_address STRING,
    register_date            TIMESTAMP(3),
    udpate_date              TIMESTAMP(3)
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_info'
      ,'properties.bootstrap.servers' = 'dcmp10:9092,dcmp11:9092,dcmp12:9092'
      ,'properties.group.id' = 'user_info'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );

drop table if exists mysql_user_info_sink;
CREATE TABLE mysql_user_info_sink
(
    user_id STRING,
    sex                      STRING,
        age                      INTEGER,
        degree                   STRING,
        address                  STRING,
        work_address             STRING,
        income_range             STRING,
        default_shipping_address STRING,
        register_date            TIMESTAMP(3),
        udpate_date              TIMESTAMP(3)
) WITH (
      'connector' = 'jdbc'
      ,'url' = 'jdbc:mysql://10.201.0.166:3306/shell1'
      ,'table-name' = 'user_info'
      ,'username' = 'root'
      ,'password' = 'daas2020'
      ,'sink.buffer-flush.max-rows' = '1000' -- default
      ,'sink.buffer-flush.interval' = '10s'
      ,'sink.max-retries' = '3'
      );

insert into mysql_user_info_sink
select user_id, sex, age, degree, address, work_address, income_range,default_shipping_address, register_date, udpate_date
from user_info;

