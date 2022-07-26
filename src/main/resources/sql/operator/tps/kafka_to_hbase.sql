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

drop table if exists hbase_user_info_sink;
CREATE TABLE hbase_user_info_sink
(
    user_id STRING,
    f      ROW(sex                      STRING,
        age                      INTEGER,
        degree                   STRING,
        address                  STRING,
        work_address             STRING,
        income_range             STRING,
        default_shipping_address STRING,
        register_date            TIMESTAMP(3),
        udpate_date              TIMESTAMP(3))
) WITH (
      'connector' = 'hbase-2.2'
      ,'zookeeper.quorum' = 'dcmp10:2181,dcmp11:2181,dcmp12:2181'
      ,'zookeeper.znode.parent' = '/hbase'
      ,'table-name' = 'user_info'
    ,'sink.buffer-flush.max-size' = '10mb'
    ,'sink.buffer-flush.max-rows' = '2000'
      );

insert into hbase_user_info_sink
select user_id, row(sex, age, degree, address, work_address, income_range,default_shipping_address, register_date, udpate_date)
from user_info;

