# sqlSubmit
SQL submission program base on flink
Now just support flink 1.10.1
[https://flink.apache.org/][Learn more about Flink]

## Features
* submit flink sql to cluster

## Example
sql flink ***demo.sql*** like : 
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
```Bash
sh sqlSubmit.sh sql/demo.sql # submit demo.sql to flink cluster

```

## Building 

* Git
* Maven (we recommend version 3.2.5 and require at least 3.1.1)
* Java 8 or 11 (Java 9 or 10 may work)

```Bash
git clone https://github.com/springMoon/sqlSubmit.git
cd sqlSubmit
mvn clean package -DskipTests # this will take up to 10 minutes

```

## Support

## About
