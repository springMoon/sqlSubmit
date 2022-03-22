-- mysql cdc to print
-- creates a mysql table source
CREATE TABLE t_user_log (
                            id bigint
    ,user_id bigint
    ,item_id bigint
    ,category_id bigint
    ,behavior varchar
    ,ts timestamp(3)
    ,proc_time as PROCTIME()
    ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
      'connector' = 'mysql-cdc'
      ,'hostname' = 'localhost'
      ,'port' = '3306'
      ,'username' = 'root'
      ,'password' = '123456'
      ,'database-name' = 'venn'
      ,'table-name' = 'user_log_1'
      ,'server-id' = '5400-5440'
      ,'scan.startup.mode' = 'latest-offset'
--       ,'scan.startup.mode' = 'initial'
--  ,'scan.startup.mode' = 'specific-offset'
--  ,'scan.startup.specific-offset.file' = 'mysql-bin.000001'
--  ,'scan.startup.specific-offset.pos' = '1'
      );

-- kafka sink
drop table if exists t_user_log_sink;
CREATE TABLE t_user_log_sink (
    id bigint
    ,user_id bigint
    ,item_id bigint
    ,category_id bigint
    ,behavior varchar
    ,ts timestamp(3)
    ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
      'connector' = 'print'
      );

-- insert into t_user_log_sink
-- select id, user_id, item_id, category_id, behavior, ts
-- from t_user_log;

create table t_user_log_sink_2(
    id bigint
    ,user_id bigint
    ,item_id bigint
    ,category_id bigint
    ,behavior varchar
    ,ts timestamp(3)
    ,proc_time as PROCTIME()
    ,PRIMARY KEY (id) NOT ENFORCED
)with (
   'connector' = 'upsert-kafka'
   ,'topic' = 'user_log_sink'
   ,'properties.bootstrap.servers' = 'localhost:9092'
   ,'properties.group.id' = 'user_log'
    ,'key.format' = 'json'
    ,'value.format' = 'json'
);

create table t_user_log_sink_2_source(
                                  id bigint
    ,user_id bigint
    ,item_id bigint
    ,category_id bigint
    ,behavior varchar
    ,ts timestamp(3)
    ,proc_time as PROCTIME()
--     ,PRIMARY KEY (id) NOT ENFORCED
)with (
     'connector' = 'kafka'
     ,'topic' = 'user_log_sink'
     ,'properties.bootstrap.servers' = 'localhost:9092'
     ,'properties.group.id' = 'user_log'
     ,'format' = 'json'
    ,'scan.startup.mode' = 'latest-offset'
     );

insert into t_user_log_sink_2
select id, user_id, item_id, category_id, behavior, ts
from t_user_log;


CREATE TEMPORARY TABLE mysql_behavior_conf (
  user_id bigint
  ,order_time bigint
) WITH (
   'connector' = 'jdbc'
   ,'url' = 'jdbc:mysql://localhost:3306/venn'
   ,'table-name' = 'user_order_time'
   ,'username' = 'root'
   ,'password' = '123456'
   ,'scan.partition.column' = 'user_id'
   ,'scan.partition.num' = '5'
   ,'scan.partition.lower-bound' = '5'
   ,'scan.partition.upper-bound' = '99999'
   ,'lookup.cache.max-rows' = '28'
   ,'lookup.cache.ttl' = '5555' -- ttl time 超过这么长时间无数据才行
);



CREATE TABLE mysql_sink (
  user_id bigint
  ,order_time bigint
  ,PRIMARY KEY (user_id) NOT ENFORCED
) WITH (
   'connector' = 'jdbc'
   ,'url' = 'jdbc:mysql://localhost:3306/venn'
   ,'table-name' = 'user_order_time'
   ,'username' = 'root'
   ,'password' = '123456'
);

-- insert into mysql_sink
-- select a.user_id, if(b.user_id is null, 1, b.order_time + 1)
-- from t_user_log_sink_2 a
--     left join mysql_behavior_conf FOR SYSTEM_TIME AS OF a.proc_time AS b
--     ON a.user_id = b.user_id
-- where a.user_id is not null
-- ;


create table t_last_result(
    user_id bigint
    ,order_time bigint
    ,PRIMARY KEY (user_id) NOT ENFORCED
)with (
     'connector' = 'upsert-kafka'
     ,'topic' = 'user_log_sink'
     ,'properties.bootstrap.servers' = 'localhost:9092'
     ,'properties.group.id' = 'user_log'
     ,'key.format' = 'json'
     ,'value.format' = 'json'
     );

insert into t_last_result
select a.user_id, if(category_id = 500,if(b.order_time is null, 0, b.order_time) -1,if(b.order_time is null, 0, b.order_time) + 1)
from (
    select user_id,category_id, proc_time, num
    from (
             select user_id, category_id,proc_time, row_number() over (partition by user_id, if(category_id = 500,0,1) order by ts asc) num
             from t_user_log_sink_2_source
         )t
    where num = 1
    ) a
         left join mysql_behavior_conf FOR SYSTEM_TIME AS OF a.proc_time AS b
                   ON a.user_id = b.user_id
where a.user_id is not null
;


-- CREATE TABLE t_print_2 (
--     user_id bigint
--     ,category_id bigint
--     ,ts timestamp(3)
--     ,num bigint
-- ) WITH (
--       'connector' = 'print'
--       );
--
-- insert into t_print_2
-- select user_id,category_id, proc_time, num
-- from (
--          select user_id, category_id,proc_time, row_number() over (partition by user_id, category_id order by ts asc) num
--          from t_user_log_sink_2_source
--      )t
-- where num = 1
-- ;