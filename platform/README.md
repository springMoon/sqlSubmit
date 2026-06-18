# sqlsubmit-platform

`sqlsubmit-platform` 是当前 repo 内的 Spring Boot 平台后端骨架。

第一阶段目标：

- 数据源管理。
- MySQL/Kafka 连接测试。
- MySQL 元数据采集。
- Flink SQL 生成。
- Yarn 提交当前 `sqlSubmit` jar。

当前数据源管理已接入 `sync_datasource` 表。启动前需要先初始化平台元数据库。

## 初始化数据库

默认连接配置在：

```text
platform/src/main/resources/application.yml
```

默认数据库：

```text
sqlsubmit_platform
```

初始化步骤：

```sql
CREATE DATABASE IF NOT EXISTS sqlsubmit_platform DEFAULT CHARACTER SET utf8mb4;
```

然后执行：

```text
doc/platform/schema.sql
```

## 本地启动

```bash
mvn -f platform/pom.xml spring-boot:run
```

默认端口：

```text
18080
```

## 数据源接口

```http
GET  /api/v1/datasources
POST /api/v1/datasources
GET  /api/v1/datasources/{id}
POST /api/v1/datasources/{id}/test
```

## 元数据接口

```http
POST /api/v1/datasources/{id}/metadata/sync
GET  /api/v1/datasources/{id}/tables
GET  /api/v1/tables/{tableId}/columns
```

当前仅支持同步 MySQL 数据源元数据。

## SQL 生成接口

```http
POST /api/v1/jobs/sql/preview
POST /api/v1/jobs/sql/simulate
```

MySQL 表同步到 Kafka 的请求示例：

```json
{
  "sourceDatasourceId": 1,
  "sourceTableId": 10,
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
  ]
}
```

如果不传 `fieldMapping`，会默认使用源表全部字段并保持字段名不变。

`/sql/simulate` 是本地测试入口，只生成 SQL，不保存任务、不创建版本、不提交 Yarn。

## 任务接口

```http
POST /api/v1/jobs
GET  /api/v1/jobs
GET  /api/v1/jobs/{id}
POST /api/v1/jobs/{id}/versions
GET  /api/v1/jobs/{id}/versions
POST /api/v1/jobs/{id}/submit
GET  /api/v1/job-instances
GET  /api/v1/job-instances/{id}
```

`POST /api/v1/jobs` 会创建任务草稿并生成当前 SQL；`POST /api/v1/jobs/{id}/versions` 会把当前 SQL 固化为一个不可变版本，并生成默认 properties。

`POST /api/v1/jobs/{id}/submit` 默认是 dry-run，因为 `platform.flink.submit-enabled=false`。它会生成 SQL/properties 文件并保存提交命令，但不会真正执行 `flink run`。确认部署机器具备 Flink/Yarn 环境后再打开该配置。
