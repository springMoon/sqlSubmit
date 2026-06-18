package com.rookie.submit.platform.job.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.rookie.submit.platform.datasource.model.DatasourceDefinition;
import com.rookie.submit.platform.datasource.model.DatasourceType;
import com.rookie.submit.platform.datasource.service.DatasourceService;
import com.rookie.submit.platform.job.dto.ColumnDefinitionRequest;
import com.rookie.submit.platform.job.dto.FieldMappingRequest;
import com.rookie.submit.platform.job.dto.SqlPreviewRequest;
import com.rookie.submit.platform.job.dto.SqlPreviewResponse;
import com.rookie.submit.platform.metadata.entity.SyncColumnMetadataEntity;
import com.rookie.submit.platform.metadata.entity.SyncTableMetadataEntity;
import com.rookie.submit.platform.metadata.mapper.SyncColumnMetadataMapper;
import com.rookie.submit.platform.metadata.mapper.SyncTableMetadataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SqlGeneratorService {

    private static final Logger LOG = LoggerFactory.getLogger(SqlGeneratorService.class);

    private final DatasourceService datasourceService;
    private final SyncTableMetadataMapper tableMetadataMapper;
    private final SyncColumnMetadataMapper columnMetadataMapper;

    public SqlGeneratorService(
            DatasourceService datasourceService,
            SyncTableMetadataMapper tableMetadataMapper,
            SyncColumnMetadataMapper columnMetadataMapper) {
        this.datasourceService = datasourceService;
        this.tableMetadataMapper = tableMetadataMapper;
        this.columnMetadataMapper = columnMetadataMapper;
    }

    public SqlPreviewResponse preview(SqlPreviewRequest request) {
        DatasourceDefinition source = datasourceService.get(request.getSourceDatasourceId());
        DatasourceDefinition sink = datasourceService.get(request.getSinkDatasourceId());
        validateRoute(source.getType(), sink.getType());

        SourcePlan sourcePlan = buildSourcePlan(source, request);
        List<FieldPlan> fieldPlans = buildFieldPlans(sourcePlan.columns, request.getFieldMapping());
        String sinkSqlTableName = "sink_preview";

        String sourceDdl = buildSourceDdl(source, sourcePlan, request.getRuntimeConfig());
        String sinkDdl = buildSinkDdl(sink, request, sinkSqlTableName, fieldPlans);
        String insertSql = buildInsertSql(sourcePlan.sqlTableName, sinkSqlTableName, fieldPlans);
        String sql = sourceDdl + "\n\n" + sinkDdl + "\n\n" + insertSql;

        SqlPreviewResponse response = new SqlPreviewResponse();
        response.setSourceTableName(sourcePlan.sqlTableName);
        response.setSinkTableName(sinkSqlTableName);
        response.setSourceDdl(sourceDdl);
        response.setSinkDdl(sinkDdl);
        response.setInsertSql(insertSql);
        response.setSql(sql);
        response.setWarnings(sourcePlan.warnings);
        LOG.info("generated flink sql preview, sourceType: {}, sinkType: {}, sourceColumns: {}, sinkColumns: {}",
                source.getType(), sink.getType(), sourcePlan.columns.size(), fieldPlans.size());
        return response;
    }

    private void validateRoute(DatasourceType sourceType, DatasourceType sinkType) {
        if (sourceType != DatasourceType.MYSQL && sourceType != DatasourceType.DATAGEN) {
            throw new IllegalArgumentException("第一阶段仅支持 MYSQL/DATAGEN 作为源表");
        }
        if (sinkType != DatasourceType.MYSQL && sinkType != DatasourceType.KAFKA && sinkType != DatasourceType.PRINT) {
            throw new IllegalArgumentException("第一阶段仅支持 MYSQL/KAFKA/PRINT 作为目标表");
        }
        if (sourceType == DatasourceType.DATAGEN && sinkType == DatasourceType.MYSQL) {
            throw new IllegalArgumentException("datagen -> mysql 容易产生无限写入，第一阶段暂不开放");
        }
    }

    private SourcePlan buildSourcePlan(DatasourceDefinition source, SqlPreviewRequest request) {
        if (source.getType() == DatasourceType.MYSQL) {
            return buildMysqlSourcePlan(source, request.getSourceTableId());
        }
        return buildDatagenSourcePlan(request);
    }

    private SourcePlan buildMysqlSourcePlan(DatasourceDefinition source, Long sourceTableId) {
        if (sourceTableId == null) {
            throw new IllegalArgumentException("MySQL 源表必须选择 sourceTableId");
        }
        SyncTableMetadataEntity table = tableMetadataMapper.selectById(sourceTableId);
        if (table == null || !source.getId().equals(table.getDatasourceId())) {
            throw new IllegalArgumentException("源表元数据不存在或不属于当前数据源: " + sourceTableId);
        }

        LambdaQueryWrapper<SyncColumnMetadataEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SyncColumnMetadataEntity::getTableId, sourceTableId)
                .orderByAsc(SyncColumnMetadataEntity::getOrdinalPosition);
        List<SyncColumnMetadataEntity> columns = columnMetadataMapper.selectList(wrapper);
        if (columns.isEmpty()) {
            throw new IllegalArgumentException("源表没有字段元数据，请先同步元数据: " + table.getTableName());
        }

        SourcePlan sourcePlan = new SourcePlan();
        sourcePlan.sqlTableName = "source_preview";
        sourcePlan.physicalTableName = table.getTableName();
        sourcePlan.columns = columns.stream()
                .map(column -> new ColumnPlan(
                        column.getColumnName(),
                        column.getFlinkType(),
                        Boolean.TRUE.equals(column.getPrimaryKey())))
                .collect(Collectors.toList());
        sourcePlan.warnings = Collections.emptyList();
        return sourcePlan;
    }

    private SourcePlan buildDatagenSourcePlan(SqlPreviewRequest request) {
        List<ColumnPlan> columns = new ArrayList<>();
        List<String> warnings = new ArrayList<>();
        if (request.getDatagenColumns() == null || request.getDatagenColumns().isEmpty()) {
            columns.add(new ColumnPlan("id", "BIGINT", false));
            columns.add(new ColumnPlan("name", "STRING", false));
            columns.add(new ColumnPlan("create_time", "TIMESTAMP(3)", false));
            warnings.add("未传 datagenColumns，已使用默认字段 id/name/create_time");
        } else {
            for (ColumnDefinitionRequest column : request.getDatagenColumns()) {
                columns.add(new ColumnPlan(
                        column.getName().trim(),
                        column.getFlinkType().trim(),
                        Boolean.TRUE.equals(column.getPrimaryKey())));
            }
        }

        SourcePlan sourcePlan = new SourcePlan();
        sourcePlan.sqlTableName = "source_preview";
        sourcePlan.physicalTableName = "datagen";
        sourcePlan.columns = columns;
        sourcePlan.warnings = warnings;
        return sourcePlan;
    }

    private List<FieldPlan> buildFieldPlans(List<ColumnPlan> sourceColumns, List<FieldMappingRequest> mappings) {
        Map<String, ColumnPlan> sourceColumnMap = new LinkedHashMap<>();
        for (ColumnPlan column : sourceColumns) {
            sourceColumnMap.put(column.name, column);
        }

        if (mappings == null || mappings.isEmpty()) {
            List<FieldPlan> defaultFields = new ArrayList<>();
            for (ColumnPlan column : sourceColumns) {
                defaultFields.add(new FieldPlan(column, column.name));
            }
            return defaultFields;
        }

        List<FieldPlan> fieldPlans = new ArrayList<>();
        for (FieldMappingRequest mapping : mappings) {
            String sourceField = mapping.getSourceField().trim();
            ColumnPlan sourceColumn = sourceColumnMap.get(sourceField);
            if (sourceColumn == null) {
                throw new IllegalArgumentException("字段映射引用了不存在的源字段: " + sourceField);
            }
            fieldPlans.add(new FieldPlan(sourceColumn, mapping.getSinkField().trim()));
        }
        return fieldPlans;
    }

    private String buildSourceDdl(DatasourceDefinition source, SourcePlan sourcePlan, JsonNode runtimeConfig) {
        if (source.getType() == DatasourceType.MYSQL) {
            Map<String, String> options = new LinkedHashMap<>();
            JsonNode config = source.getConfig();
            options.put("connector", "jdbc");
            options.put("url", required(config, "url"));
            options.put("table-name", sourcePlan.physicalTableName);
            options.put("username", required(config, "username"));
            options.put("password", required(config, "password"));
            return buildCreateTable(sourcePlan.sqlTableName, sourcePlan.columns, true, options);
        }

        Map<String, String> options = new LinkedHashMap<>();
        options.put("connector", "datagen");
        options.put("rows-per-second", runtimeText(runtimeConfig, "rowsPerSecond", "10"));
        return buildCreateTable(sourcePlan.sqlTableName, sourcePlan.columns, false, options);
    }

    private String buildSinkDdl(
            DatasourceDefinition sink,
            SqlPreviewRequest request,
            String sinkSqlTableName,
            List<FieldPlan> fields) {
        List<ColumnPlan> sinkColumns = new ArrayList<>();
        for (FieldPlan field : fields) {
            sinkColumns.add(new ColumnPlan(field.sinkField, field.sourceColumn.flinkType, field.sourceColumn.primaryKey));
        }

        Map<String, String> options = new LinkedHashMap<>();
        if (sink.getType() == DatasourceType.MYSQL) {
            JsonNode config = sink.getConfig();
            String sinkTableName = requiredSinkTableName(request, sink, "MySQL 目标表名不能为空");
            options.put("connector", "jdbc");
            options.put("url", required(config, "url"));
            options.put("table-name", sinkTableName);
            options.put("username", required(config, "username"));
            options.put("password", required(config, "password"));
            return buildCreateTable(sinkSqlTableName, sinkColumns, true, options);
        }

        if (sink.getType() == DatasourceType.KAFKA) {
            JsonNode config = sink.getConfig();
            String topic = trimToNull(request.getSinkTableName());
            if (topic == null) {
                topic = required(config, "topic");
            }
            options.put("connector", "kafka");
            options.put("topic", topic);
            options.put("properties.bootstrap.servers", required(config, "bootstrapServers"));
            options.put("format", text(config, "format", "json"));
            return buildCreateTable(sinkSqlTableName, sinkColumns, false, options);
        }

        options.put("connector", "print");
        return buildCreateTable(sinkSqlTableName, sinkColumns, false, options);
    }

    private String buildInsertSql(String sourceTableName, String sinkTableName, List<FieldPlan> fields) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(quoteIdentifier(sinkTableName)).append("\n");
        builder.append("SELECT\n");
        for (int i = 0; i < fields.size(); i++) {
            FieldPlan field = fields.get(i);
            builder.append("    ").append(quoteIdentifier(field.sourceColumn.name));
            if (!field.sourceColumn.name.equals(field.sinkField)) {
                builder.append(" AS ").append(quoteIdentifier(field.sinkField));
            }
            if (i + 1 < fields.size()) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append("FROM ").append(quoteIdentifier(sourceTableName)).append(";");
        return builder.toString();
    }

    private String buildCreateTable(
            String tableName,
            List<ColumnPlan> columns,
            boolean includePrimaryKey,
            Map<String, String> options) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TEMPORARY TABLE ").append(quoteIdentifier(tableName)).append(" (\n");
        List<String> primaryKeys = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            ColumnPlan column = columns.get(i);
            builder.append("    ")
                    .append(quoteIdentifier(column.name))
                    .append(" ")
                    .append(column.flinkType);
            if (i + 1 < columns.size() || hasPrimaryKey(columns, includePrimaryKey)) {
                builder.append(",");
            }
            builder.append("\n");
            if (column.primaryKey) {
                primaryKeys.add(column.name);
            }
        }
        if (includePrimaryKey && !primaryKeys.isEmpty()) {
            builder.append("    PRIMARY KEY (");
            for (int i = 0; i < primaryKeys.size(); i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append(quoteIdentifier(primaryKeys.get(i)));
            }
            builder.append(") NOT ENFORCED\n");
        }
        builder.append(") WITH (\n");
        List<Map.Entry<String, String>> entries = new ArrayList<>(options.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            Map.Entry<String, String> entry = entries.get(i);
            builder.append("    '")
                    .append(escapeSqlString(entry.getKey()))
                    .append("' = '")
                    .append(escapeSqlString(entry.getValue()))
                    .append("'");
            if (i + 1 < entries.size()) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append(");");
        return builder.toString();
    }

    private boolean hasPrimaryKey(List<ColumnPlan> columns, boolean includePrimaryKey) {
        if (!includePrimaryKey) {
            return false;
        }
        for (ColumnPlan column : columns) {
            if (column.primaryKey) {
                return true;
            }
        }
        return false;
    }

    private String requiredSinkTableName(SqlPreviewRequest request, DatasourceDefinition sink, String message) {
        String tableName = trimToNull(request.getSinkTableName());
        if (tableName == null && sink.getConfig() != null) {
            tableName = trimToNull(sink.getConfig().path("tableName").asText(null));
        }
        if (tableName == null) {
            throw new IllegalArgumentException(message);
        }
        return tableName;
    }

    private String required(JsonNode config, String key) {
        String value = text(config, key, null);
        if (value == null) {
            throw new IllegalArgumentException("缺少数据源配置: " + key);
        }
        return value;
    }

    private String text(JsonNode config, String key, String defaultValue) {
        JsonNode value = config == null ? null : config.get(key);
        String text = value == null || value.isNull() ? null : value.asText();
        text = trimToNull(text);
        return text == null ? defaultValue : text;
    }

    private String runtimeText(JsonNode config, String key, String defaultValue) {
        return text(config, key, defaultValue);
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String quoteIdentifier(String value) {
        return "`" + value.replace("`", "``") + "`";
    }

    private String escapeSqlString(String value) {
        return value.replace("'", "''");
    }

    private static class SourcePlan {
        private String sqlTableName;
        private String physicalTableName;
        private List<ColumnPlan> columns;
        private List<String> warnings;
    }

    private static class ColumnPlan {
        private final String name;
        private final String flinkType;
        private final boolean primaryKey;

        private ColumnPlan(String name, String flinkType, boolean primaryKey) {
            this.name = name;
            this.flinkType = flinkType.toUpperCase(Locale.ROOT);
            this.primaryKey = primaryKey;
        }
    }

    private static class FieldPlan {
        private final ColumnPlan sourceColumn;
        private final String sinkField;

        private FieldPlan(ColumnPlan sourceColumn, String sinkField) {
            this.sourceColumn = sourceColumn;
            this.sinkField = sinkField;
        }
    }
}
