package com.rookie.submit.platform.metadata.dto;

import com.rookie.submit.platform.metadata.entity.SyncColumnMetadataEntity;

public class ColumnMetadataResponse {

    private Long id;
    private Long tableId;
    private String columnName;
    private String sourceType;
    private String flinkType;
    private Boolean nullable;
    private Boolean primaryKey;
    private Integer ordinalPosition;
    private String comment;

    public static ColumnMetadataResponse from(SyncColumnMetadataEntity entity) {
        ColumnMetadataResponse response = new ColumnMetadataResponse();
        response.id = entity.getId();
        response.tableId = entity.getTableId();
        response.columnName = entity.getColumnName();
        response.sourceType = entity.getSourceType();
        response.flinkType = entity.getFlinkType();
        response.nullable = entity.getNullable();
        response.primaryKey = entity.getPrimaryKey();
        response.ordinalPosition = entity.getOrdinalPosition();
        response.comment = entity.getComment();
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getFlinkType() {
        return flinkType;
    }

    public void setFlinkType(String flinkType) {
        this.flinkType = flinkType;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(Integer ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
