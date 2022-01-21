-- cdc cumulate : not support
-- StreamPhysicalWindowAggregate doesn't support consuming update and delete changes which is produced by node
-- TableSourceScan(table=[[default_catalog, default_database, t_user_log]], fields=[id, user_id, item_id, category_id, behavior, ts])

-- cdc source
CREATE TABLE t_user_log (
  id bigint
  ,user_id bigint
  ,item_id bigint
  ,category_id bigint
  ,behavior varchar
  ,ts timestamp(3)
  ,proc_time as PROCTIME()
  ,watermark for ts as ts  - INTERVAL '5' second
  ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
 'connector' = 'mysql-cdc'
 ,'hostname' = 'localhost'
 ,'port' = '3306'
 ,'username' = 'root'
 ,'password' = '123456'
 ,'database-name' = 'venn'
 ,'table-name' = 'user_log'
 ,'server-id' = '12345678'
 ,'scan.startup.mode' = 'latest-offset'
--  ,'scan.startup.mode' = 'initial'
--  ,'scan.startup.mode' = 'specific-offset'
--  ,'scan.startup.specific-offset.file' = 'mysql-bin.000001'
--  ,'scan.startup.specific-offset.pos' = '1'
);

-- kafka sink
drop table if exists t_user_log_sink;
CREATE TABLE t_user_log_sink (
  window_start string
  ,window_end string
  ,pv bigint
  ,uv bigint
--   ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
    'connector' = 'print'
--    'connector' = 'upsert-kafka'
--   ,'topic' = 'user_log_sink'
--   ,'properties.bootstrap.servers' = 'localhost:9092'
--   ,'properties.group.id' = 'user_log'
--   ,'key.format' = 'json'
--   ,'key.json.ignore-parse-errors' = 'true'
--   ,'value.format' = 'json'
--   ,'value.json.fail-on-missing-field' = 'false'
--   ,'value.fields-include' = 'ALL'
);

insert into t_user_log_sink
select date_format(window_start, 'HH:mm:ss')
 , date_format(window_end, 'HH:mm:ss')
 , count(id)
 , count(distinct id)
from TABLE(
    cumulate(TABLE t_user_log, DESCRIPTOR(proc_time), INTERVAL '1' MINUTE, INTERVAL '1' DAY))
group by window_start, window_end