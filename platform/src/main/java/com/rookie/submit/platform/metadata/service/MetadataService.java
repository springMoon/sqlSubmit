package com.rookie.submit.platform.metadata.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.rookie.submit.platform.datasource.model.DatasourceDefinition;
import com.rookie.submit.platform.datasource.model.DatasourceType;
import com.rookie.submit.platform.datasource.service.DatasourceService;
import com.rookie.submit.platform.metadata.dto.MetadataSyncResponse;
import com.rookie.submit.platform.metadata.entity.SyncColumnMetadataEntity;
import com.rookie.submit.platform.metadata.entity.SyncTableMetadataEntity;
import com.rookie.submit.platform.metadata.mapper.SyncColumnMetadataMapper;
import com.rookie.submit.platform.metadata.mapper.SyncTableMetadataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MetadataService {

    private static final Logger LOG = LoggerFactory.getLogger(MetadataService.class);

    private final DatasourceService datasourceService;
    private final SyncTableMetadataMapper tableMetadataMapper;
    private final SyncColumnMetadataMapper columnMetadataMapper;

    public MetadataService(
            DatasourceService datasourceService,
            SyncTableMetadataMapper tableMetadataMapper,
            SyncColumnMetadataMapper columnMetadataMapper) {
        this.datasourceService = datasourceService;
        this.tableMetadataMapper = tableMetadataMapper;
        this.columnMetadataMapper = columnMetadataMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public MetadataSyncResponse syncMysqlMetadata(Long datasourceId) throws Exception {
        DatasourceDefinition datasource = datasourceService.get(datasourceId);
        if (datasource.getType() != DatasourceType.MYSQL) {
            throw new IllegalArgumentException("仅支持同步 MySQL 数据源元数据");
        }

        JsonNode config = datasource.getConfig();
        String url = required(config, "url");
        String username = required(config, "username");
        String password = required(config, "password");

        int tableCount = 0;
        int columnCount = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            DatabaseMetaData metaData = connection.getMetaData();
            String catalog = connection.getCatalog();
            try (ResultSet tables = metaData.getTables(catalog, null, "%", new String[]{"TABLE"})) {
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    String comment = tables.getString("REMARKS");
                    SyncTableMetadataEntity tableEntity = upsertTable(datasourceId, tableName, comment);
                    columnCount += refreshColumns(metaData, catalog, tableName, tableEntity.getId());
                    tableCount++;
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(toMysqlMetadataError(url, e), e);
        }
        LOG.info("synced mysql metadata, datasourceId: {}, tableCount: {}, columnCount: {}",
                datasourceId, tableCount, columnCount);
        return new MetadataSyncResponse(datasourceId, tableCount, columnCount);
    }

    public List<SyncTableMetadataEntity> listTables(Long datasourceId) {
        datasourceService.get(datasourceId);
        LambdaQueryWrapper<SyncTableMetadataEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SyncTableMetadataEntity::getDatasourceId, datasourceId)
                .orderByAsc(SyncTableMetadataEntity::getTableName);
        return tableMetadataMapper.selectList(wrapper);
    }

    public List<SyncColumnMetadataEntity> listColumns(Long tableId) {
        if (tableMetadataMapper.selectById(tableId) == null) {
            throw new IllegalArgumentException("表元数据不存在: " + tableId);
        }
        LambdaQueryWrapper<SyncColumnMetadataEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SyncColumnMetadataEntity::getTableId, tableId)
                .orderByAsc(SyncColumnMetadataEntity::getOrdinalPosition);
        return columnMetadataMapper.selectList(wrapper);
    }

    private SyncTableMetadataEntity upsertTable(Long datasourceId, String tableName, String comment) {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<SyncTableMetadataEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SyncTableMetadataEntity::getDatasourceId, datasourceId)
                .eq(SyncTableMetadataEntity::getTableName, tableName);
        SyncTableMetadataEntity entity = tableMetadataMapper.selectOne(wrapper);
        if (entity == null) {
            entity = new SyncTableMetadataEntity();
            entity.setDatasourceId(datasourceId);
            entity.setTableName(tableName);
            entity.setTableType("TABLE");
            entity.setComment(comment);
            entity.setLastSyncTime(now);
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);
            tableMetadataMapper.insert(entity);
            return entity;
        }

        entity.setComment(comment);
        entity.setLastSyncTime(now);
        entity.setUpdatedAt(now);
        tableMetadataMapper.updateById(entity);
        return entity;
    }

    private int refreshColumns(DatabaseMetaData metaData, String catalog, String tableName, Long tableId) throws Exception {
        LambdaQueryWrapper<SyncColumnMetadataEntity> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SyncColumnMetadataEntity::getTableId, tableId);
        columnMetadataMapper.delete(deleteWrapper);

        Set<String> primaryKeys = readPrimaryKeys(metaData, catalog, tableName);
        int count = 0;
        try (ResultSet columns = metaData.getColumns(catalog, null, tableName, "%")) {
            while (columns.next()) {
                SyncColumnMetadataEntity entity = new SyncColumnMetadataEntity();
                entity.setTableId(tableId);
                entity.setColumnName(columns.getString("COLUMN_NAME"));
                entity.setSourceType(columns.getString("TYPE_NAME"));
                entity.setFlinkType(toFlinkType(columns));
                entity.setNullable(columns.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
                entity.setPrimaryKey(primaryKeys.contains(entity.getColumnName()));
                entity.setOrdinalPosition(columns.getInt("ORDINAL_POSITION"));
                entity.setComment(columns.getString("REMARKS"));
                entity.setCreatedAt(LocalDateTime.now());
                entity.setUpdatedAt(LocalDateTime.now());
                columnMetadataMapper.insert(entity);
                count++;
            }
        }
        return count;
    }

    private Set<String> readPrimaryKeys(DatabaseMetaData metaData, String catalog, String tableName) throws Exception {
        Set<String> primaryKeys = new HashSet<>();
        try (ResultSet resultSet = metaData.getPrimaryKeys(catalog, null, tableName)) {
            while (resultSet.next()) {
                primaryKeys.add(resultSet.getString("COLUMN_NAME"));
            }
        }
        return primaryKeys;
    }

    private String toFlinkType(ResultSet columns) throws Exception {
        int jdbcType = columns.getInt("DATA_TYPE");
        int precision = columns.getInt("COLUMN_SIZE");
        int scale = columns.getInt("DECIMAL_DIGITS");
        switch (jdbcType) {
            case Types.TINYINT:
                return "TINYINT";
            case Types.SMALLINT:
                return "SMALLINT";
            case Types.INTEGER:
                return "INT";
            case Types.BIGINT:
                return "BIGINT";
            case Types.FLOAT:
            case Types.REAL:
                return "FLOAT";
            case Types.DOUBLE:
                return "DOUBLE";
            case Types.NUMERIC:
            case Types.DECIMAL:
                return "DECIMAL(" + precision + "," + scale + ")";
            case Types.DATE:
                return "DATE";
            case Types.TIME:
                return "TIME";
            case Types.TIMESTAMP:
            case -101:
            case -102:
                return "TIMESTAMP(3)";
            case Types.BIT:
            case Types.BOOLEAN:
                return "BOOLEAN";
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.LONGNVARCHAR:
            default:
                return "STRING";
        }
    }

    private String required(JsonNode config, String key) {
        JsonNode value = config == null ? null : config.get(key);
        if (value == null || value.isNull() || value.asText().trim().isEmpty()) {
            throw new IllegalArgumentException("缺少数据源配置: " + key);
        }
        return value.asText().trim();
    }

    private String toMysqlMetadataError(String url, SQLException e) {
        String message = e.getMessage() == null ? "" : e.getMessage();
        if (message.contains("Unknown database")) {
            return "MySQL 数据库不存在，请检查数据源 JDBC URL 中的数据库名: " + extractDatabaseName(url);
        }
        if (message.contains("Public Key Retrieval is not allowed")) {
            return "MySQL 认证失败，请在 JDBC URL 增加 allowPublicKeyRetrieval=true";
        }
        return "同步 MySQL 元数据失败: " + message;
    }

    private String extractDatabaseName(String url) {
        int slashIndex = url.lastIndexOf('/');
        if (slashIndex < 0 || slashIndex + 1 >= url.length()) {
            return url;
        }
        String databaseAndQuery = url.substring(slashIndex + 1);
        int queryIndex = databaseAndQuery.indexOf('?');
        return queryIndex < 0 ? databaseAndQuery : databaseAndQuery.substring(0, queryIndex);
    }
}
