-- scan source : bounded 一次执行，读完就任务结束
-- mysql source
drop table if exists mysql_user_log ;
CREATE TABLE mysql_user_log (
    id int
  ,user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
  ,create_time TIMESTAMP(3)
  ,insert_time TIMESTAMP(3)
  ,primary key (id) not enforced
) WITH (
   'connector' = 'jdbc'
   ,'url' = 'jdbc:mysql://venn:3306/venn'
   ,'table-name' = 'user_log'
   ,'username' = 'root'
   ,'password' = '123456'
);

-- kafka sink
drop table if exists user_log_sink ;
CREATE TABLE user_log_sink (
  id int
  ,user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ts TIMESTAMP(3)
  ,create_time TIMESTAMP(3)
  ,insert_time TIMESTAMP(3)
) WITH (
 'connector' = 'kafka'
  ,'topic' = 'user_behavior_sink'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

-- insert
insert into user_log_sink
select id, user_id, item_id, category_id, behavior, ts, create_time, insert_time
from mysql_user_log;
