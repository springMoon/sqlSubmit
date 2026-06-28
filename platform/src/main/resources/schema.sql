-- Flink 同步平台第一阶段元数据库表结构。
-- 目标数据库：MySQL 8.x

CREATE TABLE IF NOT EXISTS sync_datasource (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    type VARCHAR(32) NOT NULL COMMENT '数据源类型：MYSQL/KAFKA/DATAGEN/PRINT',
    config_json JSON NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1,
    remark VARCHAR(512) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_sync_datasource_name (name),
    KEY idx_sync_datasource_type (type)
) COMMENT='数据源定义';

CREATE TABLE IF NOT EXISTS sync_table_metadata (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    datasource_id BIGINT NOT NULL,
    table_name VARCHAR(256) NOT NULL,
    table_type VARCHAR(32) NOT NULL DEFAULT 'TABLE' COMMENT '表类型：TABLE/TOPIC/VIRTUAL',
    comment VARCHAR(1024) NULL,
    last_sync_time DATETIME NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_sync_table_ds_name (datasource_id, table_name),
    KEY idx_sync_table_datasource (datasource_id)
) COMMENT='来源表或目标表元数据';

CREATE TABLE IF NOT EXISTS sync_column_metadata (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    table_id BIGINT NOT NULL,
    column_name VARCHAR(256) NOT NULL,
    source_type VARCHAR(128) NOT NULL,
    flink_type VARCHAR(128) NOT NULL,
    nullable TINYINT NOT NULL DEFAULT 1,
    primary_key TINYINT NOT NULL DEFAULT 0,
    ordinal_position INT NOT NULL,
    comment VARCHAR(1024) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_sync_column_table_name (table_id, column_name),
    KEY idx_sync_column_table (table_id)
) COMMENT='字段元数据及 Flink SQL 类型映射';

CREATE TABLE IF NOT EXISTS sync_job (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_name VARCHAR(256) NOT NULL,
    source_datasource_id BIGINT NOT NULL,
    source_table_id BIGINT NULL,
    source_table_name VARCHAR(256) NULL,
    sink_datasource_id BIGINT NOT NULL,
    sink_table_name VARCHAR(256) NOT NULL,
    field_mapping_json JSON NOT NULL,
    runtime_config_json JSON NULL,
    generated_sql MEDIUMTEXT NULL,
    status VARCHAR(32) NOT NULL DEFAULT 'DRAFT' COMMENT '任务状态：DRAFT/READY/RUNNING/FAILED/CANCELED/FINISHED',
    current_version INT NOT NULL DEFAULT 0,
    remark VARCHAR(1024) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_sync_job_name (job_name),
    KEY idx_sync_job_status (status),
    KEY idx_sync_job_source (source_datasource_id),
    KEY idx_sync_job_sink (sink_datasource_id)
) COMMENT='同步任务定义';

CREATE TABLE IF NOT EXISTS sync_job_version (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_id BIGINT NOT NULL,
    version INT NOT NULL,
    generated_sql MEDIUMTEXT NOT NULL,
    generated_properties TEXT NULL,
    generator_config_json JSON NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_sync_job_version (job_id, version),
    KEY idx_sync_job_version_job (job_id)
) COMMENT='不可变的生成 SQL 版本';

CREATE TABLE IF NOT EXISTS sync_job_instance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_id BIGINT NOT NULL,
    job_version INT NOT NULL,
    flink_job_id VARCHAR(128) NULL,
    yarn_application_id VARCHAR(128) NULL,
    submit_command TEXT NULL,
    sql_path VARCHAR(1024) NOT NULL,
    prop_path VARCHAR(1024) NULL,
    status VARCHAR(32) NOT NULL DEFAULT 'SUBMITTED' COMMENT '任务实例状态：DRY_RUN/SUBMITTED/RUNNING/FAILED/CANCELED/FINISHED',
    start_time DATETIME NULL,
    end_time DATETIME NULL,
    error_message TEXT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_sync_job_instance_job (job_id),
    KEY idx_sync_job_instance_status (status),
    KEY idx_sync_job_instance_flink_job (flink_job_id),
    KEY idx_sync_job_instance_yarn_app (yarn_application_id)
) COMMENT='任务提交实例';

CREATE TABLE IF NOT EXISTS sync_job_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    job_instance_id BIGINT NOT NULL,
    level VARCHAR(16) NOT NULL DEFAULT 'INFO',
    message TEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_sync_job_log_instance (job_instance_id),
    KEY idx_sync_job_log_level (level)
) COMMENT='任务实例日志和异常摘要';

-- 内置数据源。用户创建的数据源密码需要加密保存。
INSERT IGNORE INTO sync_datasource (name, type, config_json, enabled, remark)
VALUES
('datagen', 'DATAGEN', JSON_OBJECT('builtin', true), 1, '内置 datagen source'),
('print', 'PRINT', JSON_OBJECT('builtin', true), 1, '内置 print sink');
