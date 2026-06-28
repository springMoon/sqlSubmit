package com.rookie.submit.platform.job.dto;

import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SqlPreviewRequest {

    @NotNull(message = "源数据源 ID 不能为空")
    private Long sourceDatasourceId;

    private Long sourceTableId;

    private String sourceTableName;

    @NotNull(message = "目标数据源 ID 不能为空")
    private Long sinkDatasourceId;

    private String sinkTableName;

    @Valid
    private List<FieldMappingRequest> fieldMapping;

    @Valid
    private List<ColumnDefinitionRequest> datagenColumns;

    private JsonNode runtimeConfig;

    public Long getSourceDatasourceId() {
        return sourceDatasourceId;
    }

    public void setSourceDatasourceId(Long sourceDatasourceId) {
        this.sourceDatasourceId = sourceDatasourceId;
    }

    public Long getSourceTableId() {
        return sourceTableId;
    }

    public void setSourceTableId(Long sourceTableId) {
        this.sourceTableId = sourceTableId;
    }

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public Long getSinkDatasourceId() {
        return sinkDatasourceId;
    }

    public void setSinkDatasourceId(Long sinkDatasourceId) {
        this.sinkDatasourceId = sinkDatasourceId;
    }

    public String getSinkTableName() {
        return sinkTableName;
    }

    public void setSinkTableName(String sinkTableName) {
        this.sinkTableName = sinkTableName;
    }

    public List<FieldMappingRequest> getFieldMapping() {
        return fieldMapping;
    }

    public void setFieldMapping(List<FieldMappingRequest> fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    public List<ColumnDefinitionRequest> getDatagenColumns() {
        return datagenColumns;
    }

    public void setDatagenColumns(List<ColumnDefinitionRequest> datagenColumns) {
        this.datagenColumns = datagenColumns;
    }

    public JsonNode getRuntimeConfig() {
        return runtimeConfig;
    }

    public void setRuntimeConfig(JsonNode runtimeConfig) {
        this.runtimeConfig = runtimeConfig;
    }
}
