# sqlSubmit

SQL submission program base on flink

Now just support flink 1.13.0

Learn more about Flink https://flink.apache.org/

Derived from Jark's blog http://wuchong.me/blog/2019/09/02/flink-sql-1-9-read-from-kafka-write-into-mysql/

## Features

* submit flink sql to cluster

## Example

SQL file ***demo.sql*** like : 
```sql
-- source
CREATE TABLE user_log (
    user_id VARCHAR
    ,item_id VARCHAR
    ,category_id VARCHAR
    ,behavior VARCHAR
    ,ts TIMESTAMP
) WITH (
    'connector.type' = 'kafka',                                   -- 使用 kafka connector
    ,'connector.version' = 'universal',                           -- kafka 版本，universal 支持 0.11 以上的版本
    ,'connector.topic' = 'user_behavior',                         -- kafka topic
    ,'connector.startup-mode' = 'earliest-offset',                -- 从起始 offset 开始读取
    ,'connector.properties.bootstrap.servers' = 'localhost:9092', 
    ,'update-mode' = 'append',
    ,'format.type' = 'json',  -- 数据源格式为 json
    ,'format.derive-schema' = 'true' -- 从 DDL schema 确定 json 解析规则
)

-- sink
CREATE TABLE pvuv_sink (
    dt VARCHAR
    ,pv BIGINT
    ,uv BIGINT
) WITH (
    'connector.type' = 'jdbc' -- 使用 jdbc connector
    ,'connector.url' = 'jdbc:mysql://localhost:3306/flink-test' -- jdbc url
    ,'connector.table' = 'pvuv_sink' -- 表名
    ,'connector.username' = 'root' -- 用户名
    ,'connector.password' = '123456' -- 密码
    ,'connector.write.flush.max-rows' = '1' -- 默认5000条，为了演示改为1条
)

-- exec sql
INSERT INTO pvuv_sink
SELECT
  DATE_FORMAT(ts, 'yyyy-MM-dd HH:00') dt
  ,COUNT(*) AS pv
  ,COUNT(DISTINCT user_id) AS uv
FROM user_log
GROUP BY DATE_FORMAT(ts, 'yyyy-MM-dd HH:00')

```

commit to flink cluster

```bash
# --sql demo.sql                              special sql file demo.sql
# --state.backend rocksdb                     add properties state.backend as rocksdb
# --job.prop.file demoJobPropFile.properties  special job properties
# parameter priority : special parameter is hightest, next is job.prop.file, default properties [sqlSubmit.properties] last
sh start_pre_job.sh --session sqlDemo --sql demo.sql --state.backend rocksdb --job.prop.file demoJobPropFile.properties

```

## Building 

* Git
* Maven (recommend version 3.2.5 and require at least 3.1.1)
* Java 8 or 11 (Java 9 or 10 may work)

```bash
git clone https://github.com/springMoon/sqlSubmit.git
cd sqlSubmit
mvn clean package -DskipTests # this will take up to 10 minutes

```
## hive dialect

Flink create hive table need use hive dialect, just create rule when sql containes "hive_table_" means use hive dialect. such as :

```sql
-- set table.sql-dialect=hive;
-- hvie sink
drop table if exists hive_table_user_log_sink;
CREATE TABLE hive_table_user_log_sink (
  user_id STRING
  ,item_id STRING
  ,category_id STRING
  ,behavior STRING
) PARTITIONED BY (dt STRING, hr STRING) STORED AS parquet TBLPROPERTIES (
  'partition.time-extractor.timestamp-pattern'='$dt $hr:00:00',
  'sink.partition-commit.trigger'='partition-time',
  'sink.partition-commit.delay'='1 min',
  'sink.partition-commit.policy.kind'='metastore,success-file'
);

```


## Support

![image](https://github.com/springMoon/sqlSubmit/blob/master/doc/picture/gzh.png)

## Gratitude

Thanks for [JetBrains](https://www.jetbrains.com/?from=sqlSubmit) provide  opensource license.

## About
