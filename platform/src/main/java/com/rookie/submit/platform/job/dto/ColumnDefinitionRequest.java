package com.rookie.submit.platform.job.dto;

import javax.validation.constraints.NotBlank;

public class ColumnDefinitionRequest {

    @NotBlank(message = "字段名不能为空")
    private String name;

    @NotBlank(message = "Flink 字段类型不能为空")
    private String flinkType;

    private Boolean primaryKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlinkType() {
        return flinkType;
    }

    public void setFlinkType(String flinkType) {
        this.flinkType = flinkType;
    }

    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
}
