package com.rookie.submit.platform.job.dto;

import com.rookie.submit.platform.job.entity.SyncJobVersionEntity;

import java.time.LocalDateTime;

public class JobVersionResponse {

    private Long id;
    private Long jobId;
    private Integer version;
    private String generatedSql;
    private String generatedProperties;
    private LocalDateTime createdAt;

    public static JobVersionResponse from(SyncJobVersionEntity entity) {
        JobVersionResponse response = new JobVersionResponse();
        response.setId(entity.getId());
        response.setJobId(entity.getJobId());
        response.setVersion(entity.getVersion());
        response.setGeneratedSql(entity.getGeneratedSql());
        response.setGeneratedProperties(entity.getGeneratedProperties());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getGeneratedSql() {
        return generatedSql;
    }

    public void setGeneratedSql(String generatedSql) {
        this.generatedSql = generatedSql;
    }

    public String getGeneratedProperties() {
        return generatedProperties;
    }

    public void setGeneratedProperties(String generatedProperties) {
        this.generatedProperties = generatedProperties;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
