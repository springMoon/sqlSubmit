-- starrocks to print
-- exception： Exception in thread "main" java.lang.NoSuchMethodError:
--              org.apache.flink.table.utils.TableSchemaUtils.projectSchema(Lorg/apache/flink/table/api/TableSchema;[[I)Lorg/apache/flink/table/api/TableSchema;
CREATE TABLE user_log
(
    `col1` string
    ,`col2` string
    ,`col3` string
    ,`col4` string
) WITH (
      'connector' = 'jdbc'
      ,'url' = 'jdbc:mysql://10.201.0.230:29030/shell'
      ,'table-name' = 'datagen_key'
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
);

insert into user_log_sink
select col1, col2, col3,'' col4, count(1)
from user_log
group by col1, col2, col3