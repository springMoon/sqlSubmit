package com.rookie.submit.platform.datasource.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.rookie.submit.platform.datasource.model.DatasourceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateDatasourceRequest {

    @NotBlank(message = "数据源名称不能为空")
    private String name;

    @NotNull(message = "数据源类型不能为空")
    private DatasourceType type;

    @NotNull(message = "数据源配置不能为空")
    private JsonNode config;

    private Boolean enabled = true;
    private String remark;

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
}
