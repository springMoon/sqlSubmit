-- Lookup Source: Sync Mode
-- kafka source
CREATE TABLE user_log
(
    user_id     STRING,
    item_id     STRING,
    category_id STRING,
    behavior    STRING,
    page        STRING,
    `position`    STRING,
    sort        STRING,
    last_page   STRING,
    next_page   STRING,
    ts          TIMESTAMP(3),
    process_time as proctime(),
    WATERMARK FOR ts AS ts - INTERVAL '5' SECOND
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log'
      ,'properties.bootstrap.servers' = 'dcmp10:9092,dcmp11:9092,dcmp12:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );

drop table if exists hbase_behavior_conf;
CREATE
TEMPORARY TABLE hbase_behavior_conf (
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
   ,'lookup.cache.max-rows' = '100000'
   ,'lookup.cache.ttl' = '10 minute' -- ttl time 超过这么长时间无数据才行
   ,'lookup.async' = 'true'
);

---sinkTable
CREATE TABLE user_log_sink
(
    user_id                  STRING,
    item_id                  STRING,
    category_id              STRING,
    behavior                 STRING,
    page                     STRING,
    `position`                 STRING,
    sort                     STRING,
    last_page                STRING,
    next_page                STRING,
    ts                       TIMESTAMP(3),
    sex                      STRING,
    age                      INTEGER,
    degree                   STRING,
    address                  STRING,
    work_address             STRING,
    income_range             STRING,
    default_shipping_address STRING,
    register_date            TIMESTAMP(3),
    udpate_date              TIMESTAMP(3)
--   ,primary key (user_id) not enforced
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log_sink'
      ,'properties.bootstrap.servers' = 'dcmp10:9092,dcmp11:9092,dcmp12:9092'
      ,'properties.group.id' = 'user_log'
      ,'scan.startup.mode' = 'group-offsets'
      ,'format' = 'json'
      );

INSERT INTO user_log_sink
SELECT a.user_id
     ,a.item_id
     ,a.category_id
     ,a.behavior
     ,a.page
     ,a.`position`
     ,a.sort
     ,a.last_page
     ,a.next_page
     ,a.ts
     ,b.sex
     ,b.age
     ,b.degree
     ,b.address
     ,b.work_address
     ,b.income_range
     ,b.default_shipping_address
     ,b.register_date
     ,b.udpate_date
FROM user_log a
         left join hbase_behavior_conf FOR SYSTEM_TIME AS OF a.process_time AS b
                   ON a.user_id = b.user_id
where a.behavior is not null;
