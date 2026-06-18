package com.rookie.submit.platform.job.dto;

import javax.validation.constraints.NotBlank;

public class CreateJobRequest extends SqlPreviewRequest {

    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    private String remark;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
