package com.rookie.submit.platform.metadata.dto;

import com.rookie.submit.platform.metadata.entity.SyncTableMetadataEntity;

import java.time.LocalDateTime;

public class TableMetadataResponse {

    private Long id;
    private Long datasourceId;
    private String tableName;
    private String tableType;
    private String comment;
    private LocalDateTime lastSyncTime;

    public static TableMetadataResponse from(SyncTableMetadataEntity entity) {
        TableMetadataResponse response = new TableMetadataResponse();
        response.id = entity.getId();
        response.datasourceId = entity.getDatasourceId();
        response.tableName = entity.getTableName();
        response.tableType = entity.getTableType();
        response.comment = entity.getComment();
        response.lastSyncTime = entity.getLastSyncTime();
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(LocalDateTime lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }
}
