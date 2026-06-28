package com.rookie.submit.platform.submit.dto;

import com.rookie.submit.platform.submit.entity.SyncJobLogEntity;

import java.time.LocalDateTime;

public class JobLogResponse {

    private Long id;
    private Long jobInstanceId;
    private String level;
    private String message;
    private LocalDateTime createdAt;

    public static JobLogResponse from(SyncJobLogEntity entity) {
        JobLogResponse response = new JobLogResponse();
        response.setId(entity.getId());
        response.setJobInstanceId(entity.getJobInstanceId());
        response.setLevel(entity.getLevel());
        response.setMessage(entity.getMessage());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
