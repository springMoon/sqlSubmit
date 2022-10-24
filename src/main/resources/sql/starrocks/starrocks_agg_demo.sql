-- starrocks to print
-- exception： Exception in thread "main" java.lang.NoSuchMethodError:
--              org.apache.flink.table.utils.TableSchemaUtils.projectSchema(Lorg/apache/flink/table/api/TableSchema;[[I)Lorg/apache/flink/table/api/TableSchema;
-- fix: forget save a flink-connector_2.11-1.14.4.jar to project lib
CREATE TABLE user_log
(
    `col1` string
    ,`col2` string
    ,`col3` string
    ,`col4` string
    ,proc_time as PROCTIME()
) WITH (
--       'connector' = 'datagen'
--       ,'rows-per-second' = '20000'
--       ,'number-of-rows' = '100000000'
--       ,'fields.col1.kind' = 'random'
--       ,'fields.col2.kind' = 'random'
--       ,'fields.col3.kind' = 'random'
--       ,'fields.col4.kind' = 'random'
--       ,'fields.col1.length' = '20'
--       ,'fields.col2.length' = '10'
--       ,'fields.col3.length' = '10'
--       ,'fields.col4.length' = '10'
      'connector' = 'jdbc'
      ,'url' = 'jdbc:mysql://10.201.0.230:29030/shell'
      ,'table-name' = 'datagen_key'
--       ,'url' = 'jdbc:mysql://localhost:3306/venn'
--       ,'table-name' = 'user_log_datagen'
      ,'username' = 'root'
      ,'password' = '123456'
      );
CREATE TABLE user_log_sink
(
    `col1` string
    ,`col2` string
    ,`col3` string
    ,`col4` string
    ,cnt  bigint
) WITH (
    'connector' = 'print'
--       'connector'='starrocks',
--       'load-url'='10.201.0.230:28030',
--       'jdbc-url'='jdbc:mysql://10.201.0.230:29030',
--       'username'='root',
--       'password'='123456',
--       'database-name'='test',
--       'table-name'='datagen_key',
--       'sink.buffer-flush.max-rows' = '1000000',
--       'sink.buffer-flush.max-bytes' = '300000000',
--       'sink.buffer-flush.interval-ms' = '5000'
--       ,'sink.properties.format' = 'json'
--       ,'sink.properties.strip_outer_array' = 'true'
);

-- insert into user_log_sink
-- select substring(col1,1,5),'' col2,'' col3,'' col4,  count(1)
-- from user_log
-- group by substring(col1,1,5)

insert into user_log_sink
select date_format(window_start,'yyyy-MM-dd HH:mm:ss'),date_format(window_end,'yyyy-MM-dd HH:mm:ss'),substring(col1,1,5),'' col4,  count(1)
from TABLE(TUMBLE(TABLE user_log, DESCRIPTOR(proc_time), INTERVAL '2' second)
    )
group by window_start,window_end,substring(col1,1,5)