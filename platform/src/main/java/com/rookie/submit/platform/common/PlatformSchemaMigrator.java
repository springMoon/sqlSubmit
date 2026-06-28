package com.rookie.submit.platform.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PlatformSchemaMigrator implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(PlatformSchemaMigrator.class);

    private final JdbcTemplate jdbcTemplate;

    public PlatformSchemaMigrator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        addColumnIfMissing(
                "sync_job",
                "source_table_name",
                "ALTER TABLE sync_job ADD COLUMN source_table_name VARCHAR(256) NULL AFTER source_table_id");
    }

    private void addColumnIfMissing(String tableName, String columnName, String ddl) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM information_schema.COLUMNS "
                        + "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ? AND COLUMN_NAME = ?",
                Integer.class,
                tableName,
                columnName);
        if (count != null && count > 0) {
            return;
        }
        jdbcTemplate.execute(ddl);
        LOG.info("applied platform schema migration, table: {}, column: {}", tableName, columnName);
    }
}
