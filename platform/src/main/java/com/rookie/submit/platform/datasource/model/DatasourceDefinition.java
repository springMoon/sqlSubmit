package com.rookie.submit.platform.datasource.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;

public class DatasourceDefinition {

    private Long id;
    private String name;
    private DatasourceType type;
    private JsonNode config;
    private Boolean enabled;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DatasourceType getType() {
        return type;
    }

    public void setType(DatasourceType type) {
        this.type = type;
    }

    public JsonNode getConfig() {
        return config;
    }

    public void setConfig(JsonNode config) {
        this.config = config;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
