package com.rookie.submit.platform.job.dto;

import javax.validation.constraints.NotBlank;

public class FieldMappingRequest {

    @NotBlank(message = "源字段不能为空")
    private String sourceField;

    @NotBlank(message = "目标字段不能为空")
    private String sinkField;

    public String getSourceField() {
        return sourceField;
    }

    public void setSourceField(String sourceField) {
        this.sourceField = sourceField;
    }

    public String getSinkField() {
        return sinkField;
    }

    public void setSinkField(String sinkField) {
        this.sinkField = sinkField;
    }
}
