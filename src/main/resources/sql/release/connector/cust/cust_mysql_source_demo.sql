create table cust_mysql_user_log
(
    id          bigint,
    user_id     bigint,
    item_id     bigint,
    category_id bigint,
    behavior    string,
    ts          timestamp(3),
    create_time timestamp(3)
) WITH (
      'connector' = 'cust-mysql'
      ,'url' = 'jdbc:mysql://localhost:3306/venn?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true'
      ,'username' = 'root'
      ,'password' = '123456'
      ,'database' = 'venn'
      ,'table' = 'user_log'
      ,'key' = 'id'
      ,'batch.size' = '100000'
--  ,'format' = 'csv'
--       ,'format' = 'json'
      )
;

create table cust_mysql_user_log_sink
(
    id          bigint,
    user_id     bigint,
    item_id     bigint,
    category_id bigint,
    behavior    string,
    ts          timestamp(3),
    create_time timestamp(3)
) WITH (
      'connector' = 'blackhole'
)
;

insert into cust_mysql_user_log_sink
select id, user_id,item_id, category_id, behavior, ts, create_time
from cust_mysql_user_log;