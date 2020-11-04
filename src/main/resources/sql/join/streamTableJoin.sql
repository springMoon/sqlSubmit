---sourceTable
CREATE TABLE user_log (
    user_id VARCHAR
    ,item_id VARCHAR
    ,category_id VARCHAR
    ,behavior VARCHAR
    ,ts TIMESTAMP(3)
) WITH (
    'connector.type' = 'kafka'
    ,'connector.version' = 'universal'
    ,'connector.topic' = 'user_behavior'
    ,'connector.startup-mode' = 'earliest-offset'
    ,'connector.properties.zookeeper.connect' = 'venn:2181'
    ,'connector.properties.bootstrap.servers' = 'venn:9092'
    ,'update-mode' = 'append'
    ,'format.type' = 'json'
    ,'format.derive-schema' = 'true'
);

---sinkTable
CREATE TABLE user_log_sink (
    user_id VARCHAR
    ,item_id VARCHAR
    ,category_id VARCHAR
    ,behavior VARCHAR
    ,ts TIMESTAMP(3)
    ,b_item_id VARCHAR
    ,b_category_id VARCHAR
    ,b_behavior VARCHAR
    ,b_ts VARCHAR
) WITH (
    'connector.type' = 'kafka'
    ,'connector.version' = 'universal'
    ,'connector.topic' = 'user_behavior_sink'
    ,'connector.startup-mode' = 'earliest-offset'
    ,'connector.properties.zookeeper.connect' = 'venn:2181'
    ,'connector.properties.bootstrap.servers' = 'venn:9092'
    ,'update-mode' = 'append'
    ,'format.type' = 'json'
    ,'format.derive-schema' = 'true'
);

---sql
insert into user_log_sink
select a.user_id ,a.item_id
  ,a.category_id
  ,a.behavior
  ,a.ts
  ,cf.item_id     b_item_id
  ,cf.category_id b_category_id
  ,cf.behavior    b_behavior
  ,cf.ts          b_ts
from user_log a  -- inner join
  ,lateral table(joinHBaseTable(a.user_id))