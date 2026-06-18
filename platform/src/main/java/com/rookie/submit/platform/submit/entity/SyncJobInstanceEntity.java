package com.rookie.submit.platform.submit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("sync_job_instance")
public class SyncJobInstanceEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long jobId;
    private Integer jobVersion;
    private String flinkJobId;
    private String yarnApplicationId;
    private String submitCommand;
    private String sqlPath;
    private String propPath;
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public Integer getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(Integer jobVersion) {
        this.jobVersion = jobVersion;
    }

    public String getFlinkJobId() {
        return flinkJobId;
    }

    public void setFlinkJobId(String flinkJobId) {
        this.flinkJobId = flinkJobId;
    }

    public String getYarnApplicationId() {
        return yarnApplicationId;
    }

    public void setYarnApplicationId(String yarnApplicationId) {
        this.yarnApplicationId = yarnApplicationId;
    }

    public String getSubmitCommand() {
        return submitCommand;
    }

    public void setSubmitCommand(String submitCommand) {
        this.submitCommand = submitCommand;
    }

    public String getSqlPath() {
        return sqlPath;
    }

    public void setSqlPath(String sqlPath) {
        this.sqlPath = sqlPath;
    }

    public String getPropPath() {
        return propPath;
    }

    public void setPropPath(String propPath) {
        this.propPath = propPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
