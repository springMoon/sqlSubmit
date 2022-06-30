drop table if exists user_log ;
CREATE TABLE user_log (
  coun          bigint
) WITH (
   'connector' = 'cust-starrocks'
   ,'url' = 'jdbc:mysql://10.201.0.230:19030/hive'
   ,'sql' = 'select count(1) coun from hive.user_log'
   ,'username' = 'root'
   ,'password' = '123456'
);

create table user_log_sink(
    coun bigint
)with (
   'connector' = 'print'
)
;

insert into user_log_sink
select coun from user_log;
