# Flink SQL 生成器

## 第一阶段规则

支持的 source 类型：

- `MYSQL`
- `DATAGEN`

支持的 sink 类型：

- `MYSQL`
- `KAFKA`
- `PRINT`

Kafka 第一阶段只作为 sink。

## 类型映射

MySQL 到 Flink：

| MySQL 类型 | Flink 类型 |
|---|---|
| tinyint | TINYINT |
| smallint | SMALLINT |
| int / integer | INT |
| bigint | BIGINT |
| float | FLOAT |
| double | DOUBLE |
| decimal(p,s) | DECIMAL(p,s) |
| char / varchar / text | STRING |
| date | DATE |
| time | TIME |
| datetime / timestamp | TIMESTAMP(3) |
| bit / boolean | BOOLEAN |

当字段类型无法安全映射时，默认映射为 `STRING`，并在字段元数据中标记为需要人工确认。

## 命名规则

生成表名应保持稳定：

```text
source_${jobId}
sink_${jobId}
```

任务还未保存、没有 job id 时，预览阶段使用：

```text
source_preview
sink_preview
```

当字段名是保留字或包含特殊字符时，需要使用反引号转义。

## MySQL Source 模板

```sql
CREATE TABLE ${sourceTableName} (
${columns}
${primaryKey}
) WITH (
    'connector' = 'jdbc',
    'url' = '${mysqlUrl}',
    'table-name' = '${mysqlTableName}',
    'username' = '${username}',
    'password' = '${password}'
);
```

字段示例：

```sql
    id BIGINT,
    name STRING,
    create_time TIMESTAMP(3),
    PRIMARY KEY (id) NOT ENFORCED
```

## Datagen Source 模板

```sql
CREATE TABLE ${sourceTableName} (
${columns}
) WITH (
    'connector' = 'datagen',
    'rows-per-second' = '${rowsPerSecond}'
);
```

MVP 阶段默认字段可以是：

```sql
    id BIGINT,
    name STRING,
    create_time AS PROCTIME()
```

## MySQL Sink 模板

```sql
CREATE TABLE ${sinkTableName} (
${columns}
${primaryKey}
) WITH (
    'connector' = 'jdbc',
    'url' = '${mysqlUrl}',
    'table-name' = '${mysqlTableName}',
    'username' = '${username}',
    'password' = '${password}'
);
```

如果希望 MySQL sink 具备 upsert 语义，用户应选择主键。

## Kafka Sink 模板

```sql
CREATE TABLE ${sinkTableName} (
${columns}
) WITH (
    'connector' = 'kafka',
    'topic' = '${topic}',
    'properties.bootstrap.servers' = '${bootstrapServers}',
    'format' = '${format}'
);
```

第一阶段 Kafka 默认格式：

```text
json
```

## Print Sink 模板

```sql
CREATE TABLE ${sinkTableName} (
${columns}
) WITH (
    'connector' = 'print'
);
```

## Insert 模板

```sql
INSERT INTO ${sinkTableName}
SELECT
${selectFields}
FROM ${sourceTableName};
```

字段映射示例：

```sql
    id,
    name,
    create_time
```

当 source 和 sink 字段名不一致时：

```sql
    source_id AS id,
    source_name AS name
```

## 生成的任务配置文件

平台应为每个任务版本生成一个 properties 文件：

```properties
job.name=${jobName}
table.exec.resource.default-parallelism=${parallelism}
checkpoint.interval=${checkpointIntervalSeconds}
checkpoint.timeout=${checkpointTimeoutSeconds}
state.backend=${stateBackend}
checkpoint.dir=${checkpointDir}
mysql.catalog.enable=false
```

默认应保持 `mysql.catalog.enable=false`。只有生成的任务明确需要 MySQL catalog 时，才打开该配置。

## 已实现预览接口

```http
POST /api/v1/jobs/sql/preview
```

请求字段：

| 字段 | 说明 |
|---|---|
| sourceDatasourceId | 源数据源 ID，必填 |
| sourceTableName | MySQL 源表名，MySQL 源必填；平台会实时读取源库字段结构 |
| sourceTableId | 兼容旧元数据缓存模式，页面主流程不再使用 |
| sinkDatasourceId | 目标数据源 ID，必填 |
| sinkTableName | 目标物理表名；Kafka 时可作为 topic 覆盖数据源默认 topic |
| fieldMapping | 字段映射，不传时按源字段一一映射 |
| datagenColumns | datagen 源字段定义，不传时使用默认字段 |
| runtimeConfig.rowsPerSecond | datagen 每秒生成行数，默认 10 |

预览阶段直接返回可执行 SQL。后续实现任务版本保存时，需要同时保存：

- 脱敏 SQL：给页面展示。
- 运行 SQL：提交 Yarn 时使用。
- 生成配置快照：用于审计和版本回放。
