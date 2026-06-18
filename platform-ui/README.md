# sqlsubmit-platform-ui

Vue 3 + Vite 前端，服务于当前 repo 内的 `platform` 后端。

## 本地启动

先启动后端：

```bash
mvn -f platform/pom.xml spring-boot:run
```

再启动前端：

```bash
cd platform-ui
npm install
npm run dev
```

前端地址：

```text
http://localhost:5173/
```

Vite 已将 `/api` 代理到：

```text
http://localhost:18080
```

## 已接入流程

- 创建 MySQL/Kafka 数据源。
- 测试数据源连接。
- 同步 MySQL 元数据。
- 选择源表和目标数据源。
- 配置字段映射和运行参数。
- 预览 Flink SQL。
- 模拟生成 SQL，不保存、不提交。
- 保存任务草稿。
- 保存 SQL 版本。
- dry-run 提交并查询提交实例。

## 构建

```bash
npm run build
```
