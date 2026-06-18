# Flink 同步平台设计

这个目录用于记录基于当前 `sqlSubmit` 项目建设 Flink 同步平台的第一阶段设计。

## 目录边界

这里仅保存可随项目提交的平台设计文档，例如架构、接口、表结构和 SQL 生成规则。

公众号文章、配图、草稿和发布素材放到本地文档目录：

```text
/Users/venn/Documents/DOC/ai_flink_sql/
```

项目内的 `.local/` 和 `doc/platform/wechat-*` 已加入 `.gitignore`，避免把个人发布素材误提交到仓库。

## 第一阶段范围

第一版聚焦“提交到 Yarn 的批式同步任务”，支持以下链路：

- `datagen -> print`
- `datagen -> kafka`
- `mysql -> print`
- `mysql -> kafka`
- `mysql -> mysql`

Kafka 第一阶段只作为目标表，不作为来源表，因此暂不需要录入 Kafka source 字段结构。

暂缓到后续阶段的能力：

- MySQL CDC
- Kafka source schema 推断
- 权限和多租户
- 数据血缘
- 可视化拖拽编排

## 文档列表

- [architecture.md](architecture.md)：平台模块、运行流程和 MVP 边界。
- [schema.sql](schema.sql)：平台元数据库 DDL。
- [api.md](api.md)：后端 REST API 初稿。
- [sql-generator.md](sql-generator.md)：Flink SQL 生成规则和模板。

## 推荐技术栈

- 后端：Spring Boot、MyBatis Plus、MySQL。
- 前端：Vue。
- 执行器：复用当前 `sqlSubmit` jar，由平台侧提交到 Yarn。
- 前端：当前 repo 内的 `platform-ui`，基于 Vue 3 + Vite。
- 状态查询：Flink REST API。
