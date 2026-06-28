package com.rookie.submit.platform.job.dto;

import com.rookie.submit.platform.job.entity.SyncJobEntity;

import java.time.LocalDateTime;

public class JobResponse {

    private Long id;
    private String jobName;
    private Long sourceDatasourceId;
    private Long sourceTableId;
    private String sourceTableName;
    private Long sinkDatasourceId;
    private String sinkTableName;
    private String status;
    private Integer currentVersion;
    private String generatedSql;
    private String fieldMappingJson;
    private String runtimeConfigJson;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static JobResponse from(SyncJobEntity entity) {
        JobResponse response = new JobResponse();
        response.setId(entity.getId());
        response.setJobName(entity.getJobName());
        response.setSourceDatasourceId(entity.getSourceDatasourceId());
        response.setSourceTableId(entity.getSourceTableId());
        response.setSourceTableName(entity.getSourceTableName());
        response.setSinkDatasourceId(entity.getSinkDatasourceId());
        response.setSinkTableName(entity.getSinkTableName());
        response.setStatus(entity.getStatus());
        response.setCurrentVersion(entity.getCurrentVersion());
        response.setGeneratedSql(entity.getGeneratedSql());
        response.setFieldMappingJson(entity.getFieldMappingJson());
        response.setRuntimeConfigJson(entity.getRuntimeConfigJson());
        response.setRemark(entity.getRemark());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Integer currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getGeneratedSql() {
        return generatedSql;
    }

    public void setGeneratedSql(String generatedSql) {
        this.generatedSql = generatedSql;
    }

    public String getFieldMappingJson() {
        return fieldMappingJson;
    }

    public void setFieldMappingJson(String fieldMappingJson) {
        this.fieldMappingJson = fieldMappingJson;
    }

    public String getRuntimeConfigJson() {
        return runtimeConfigJson;
    }

    public void setRuntimeConfigJson(String runtimeConfigJson) {
        this.runtimeConfigJson = runtimeConfigJson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
