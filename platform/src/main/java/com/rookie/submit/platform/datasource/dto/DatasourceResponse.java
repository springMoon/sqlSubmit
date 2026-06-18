package com.rookie.submit.platform.datasource.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rookie.submit.platform.datasource.model.DatasourceDefinition;
import com.rookie.submit.platform.datasource.model.DatasourceType;

import java.time.LocalDateTime;

public class DatasourceResponse {

    private Long id;
    private String name;
    private DatasourceType type;
    private JsonNode config;
    private Boolean enabled;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DatasourceResponse from(DatasourceDefinition datasource) {
        DatasourceResponse response = new DatasourceResponse();
        response.id = datasource.getId();
        response.name = datasource.getName();
        response.type = datasource.getType();
        response.config = maskSensitiveConfig(datasource.getConfig());
        response.enabled = datasource.getEnabled();
        response.remark = datasource.getRemark();
        response.createdAt = datasource.getCreatedAt();
        response.updatedAt = datasource.getUpdatedAt();
        return response;
    }

    private static JsonNode maskSensitiveConfig(JsonNode config) {
        if (config == null || !config.isObject()) {
            return config;
        }
        ObjectNode copied = config.deepCopy();
        if (copied.has("password")) {
            copied.put("password", "******");
        }
        return copied;
    }

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
