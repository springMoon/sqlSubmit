package com.rookie.submit.platform.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("sync_job_version")
public class SyncJobVersionEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long jobId;
    private Integer version;
    private String generatedSql;
    private String generatedProperties;
    private String generatorConfigJson;
    private LocalDateTime createdAt;

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

    public String getGeneratorConfigJson() {
        return generatorConfigJson;
    }

    public void setGeneratorConfigJson(String generatorConfigJson) {
        this.generatorConfigJson = generatorConfigJson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
