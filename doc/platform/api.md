# REST API 初稿

统一前缀：

```text
/api/v1
```

## 数据源接口

### 创建数据源

```http
POST /api/v1/datasources
```

MySQL 请求示例：

```json
{
  "name": "mysql_test",
  "type": "MYSQL",
  "config": {
    "url": "jdbc:mysql://localhost:3306/venn",
    "username": "root",
    "password": "******",
    "database": "venn"
  },
  "remark": "测试 MySQL 数据源"
}
```

Kafka 请求示例：

```json
{
  "name": "kafka_test",
  "type": "KAFKA",
  "config": {
    "bootstrapServers": "localhost:9092",
    "topic": "user_log",
    "format": "json"
  }
}
```

### 查询数据源列表

```http
GET /api/v1/datasources?type=MYSQL
```

### 查询数据源详情

```http
GET /api/v1/datasources/{id}
```

### 测试数据源连接

```http
POST /api/v1/datasources/{id}/test
```

预期行为：

- MySQL：打开 JDBC 连接验证。
- Kafka：验证 bootstrap server 可连接；如果配置了 topic，则验证 topic 元数据。
- Datagen/print：内置数据源，直接返回成功。

## 元数据接口

### 同步 MySQL 元数据

```http
POST /api/v1/datasources/{id}/metadata/sync
```

### 查询表列表

```http
GET /api/v1/datasources/{id}/tables
```

该接口查询平台缓存的元数据。当前页面主流程推荐使用实时接口：

```http
GET /api/v1/datasources/{id}/live/tables
```

### 查询表字段

```http
GET /api/v1/tables/{tableId}/columns
```

该接口查询平台缓存的字段。当前页面主流程推荐使用实时接口：

```http
GET /api/v1/datasources/{id}/live/columns?tableName=sync_datasource
```

### 查询 Kafka Topic

```http
GET /api/v1/datasources/{id}/live/topics
```

用于目标数据源选择 Kafka 时动态加载 Topic 列表。若本地 Kafka 不可用，接口会返回连接错误，页面仍保留手动输入兜底。

## 任务接口

### 生成 SQL 预览

```http
POST /api/v1/jobs/sql/preview
```

请求示例：

```json
{
  "sourceDatasourceId": 1,
  "sourceTableName": "user",
  "sinkDatasourceId": 2,
  "sinkTableName": "user_log",
  "fieldMapping": [
    {
      "sourceField": "id",
      "sinkField": "id"
    },
    {
      "sourceField": "name",
      "sinkField": "username"
    }
  ],
  "runtimeConfig": {
    "rowsPerSecond": 10
  }
}
```

`datagen` 作为源表时没有真实元数据，可以在请求里传字段定义：

```json
{
  "sourceDatasourceId": 3,
  "sinkDatasourceId": 4,
  "datagenColumns": [
    {
      "name": "id",
      "flinkType": "BIGINT"
    },
    {
      "name": "name",
      "flinkType": "STRING"
    }
  ],
  "runtimeConfig": {
    "rowsPerSecond": 5
  }
}
```

响应示例：

```json
{
  "sourceTableName": "source_preview",
  "sinkTableName": "sink_preview",
  "sourceDdl": "CREATE TEMPORARY TABLE ...",
  "sinkDdl": "CREATE TEMPORARY TABLE ...",
  "insertSql": "INSERT INTO ...",
  "sql": "CREATE TEMPORARY TABLE ...;\n\nCREATE TEMPORARY TABLE ...;\n\nINSERT INTO ...;",
  "warnings": []
}
```

当前支持的链路：

- `MYSQL -> PRINT`
- `MYSQL -> KAFKA`
- `MYSQL -> MYSQL`
- `DATAGEN -> PRINT`
- `DATAGEN -> KAFKA`

预览接口当前返回可执行 SQL，会包含 JDBC/Kafka connector 运行所需配置。后续任务保存和提交阶段需要增加脱敏展示 SQL，并在运行文件中注入真实配置。

### 模拟生成 SQL

```http
POST /api/v1/jobs/sql/simulate
```

请求体与 `POST /api/v1/jobs/sql/preview` 完全一致。

该接口只根据当前配置生成 SQL：

- 不保存 `sync_job`。
- 不保存 `sync_job_version`。
- 不生成 SQL/properties 文件。
- 不提交 Yarn。

适合本地联调页面、验证字段映射和 SQL 生成规则。

### 创建草稿任务

```http
POST /api/v1/jobs
```

请求示例：

```json
{
  "jobName": "mysql_user_to_kafka",
  "sourceDatasourceId": 1,
  "sourceTableName": "user",
  "sinkDatasourceId": 2,
  "sinkTableName": "user_log",
  "fieldMapping": [
    {
      "sourceField": "id",
      "sinkField": "id"
    },
    {
      "sourceField": "name",
      "sinkField": "username"
    }
  ],
  "runtimeConfig": {
    "parallelism": 1,
    "checkpointInterval": 60,
    "checkpointTimeout": 600,
    "stateBackend": "hashmap",
    "checkpointDir": "file:///tmp/flink-checkpoints/mysql_user_to_kafka"
  },
  "remark": "MySQL 用户表写入 Kafka"
}
```

创建草稿时会同步生成当前 SQL，并写入 `sync_job.generated_sql`。任务初始状态为 `DRAFT`，还不会提交 Yarn。

### 查询任务列表

```http
GET /api/v1/jobs?status=DRAFT
```

`status` 可选。

### 查询任务详情

```http
GET /api/v1/jobs/{id}
```

### 保存生成后的 SQL 版本

```http
POST /api/v1/jobs/{id}/versions
```

保存版本时会：

- 将 `sync_job.generated_sql` 写入 `sync_job_version.generated_sql`。
- 生成默认运行 properties，包含 checkpoint、并行度和 `mysql.catalog.enable=false`。
- 将任务 `current_version` 加 1。
- 将任务状态从 `DRAFT` 更新为 `READY`。

### 查询任务版本

```http
GET /api/v1/jobs/{id}/versions
```

### 提交任务

```http
POST /api/v1/jobs/{id}/submit
```

请求示例：

```json
{
  "version": 1
}
```

`version` 可选。不传时使用任务当前版本 `current_version`。

响应示例：

```json
{
  "jobInstanceId": 1001,
  "status": "SUBMITTED",
  "sqlPath": "/opt/sqlsubmit/generated/sql/job_1001_v1.sql",
  "propPath": "/opt/sqlsubmit/generated/prop/job_1001_v1.properties"
}
```

平台默认配置为 `platform.flink.submit-enabled=false`，因此第一次调用会进入 dry-run：

- 生成 SQL 文件和 properties 文件。
- 生成并保存提交命令。
- 写入 `sync_job_instance`，状态为 `DRY_RUN`。
- 不真正执行 `flink run`。

确认平台机器具备 Flink/Yarn 环境后，将 `platform.flink.submit-enabled=true` 才会真正执行外部提交命令。

### 取消任务实例

```http
POST /api/v1/job-instances/{id}/cancel
```

### 查询任务实例详情

```http
GET /api/v1/job-instances/{id}
```

### 查询任务实例列表

```http
GET /api/v1/job-instances?jobId=1
```

### 刷新任务实例状态

```http
POST /api/v1/job-instances/{id}/refresh
```

后端应通过 Flink REST API 查询真实状态，并更新 `sync_job_instance`。
