package com.rookie.submit.platform.submit.controller;

import com.rookie.submit.platform.common.ApiResponse;
import com.rookie.submit.platform.submit.dto.JobInstanceResponse;
import com.rookie.submit.platform.submit.dto.JobLogResponse;
import com.rookie.submit.platform.submit.service.YarnSubmitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/job-instances")
public class JobInstanceController {

    private final YarnSubmitService yarnSubmitService;

    public JobInstanceController(YarnSubmitService yarnSubmitService) {
        this.yarnSubmitService = yarnSubmitService;
    }

    @GetMapping
    public ApiResponse<List<JobInstanceResponse>> list(@RequestParam(required = false) Long jobId) {
        List<JobInstanceResponse> responses = yarnSubmitService.listInstances(jobId).stream()
                .map(JobInstanceResponse::from)
                .collect(Collectors.toList());
        return ApiResponse.ok(responses);
    }

    @GetMapping("/{id}")
    public ApiResponse<JobInstanceResponse> get(@PathVariable Long id) {
        return ApiResponse.ok(JobInstanceResponse.from(yarnSubmitService.getInstance(id)));
    }

    @GetMapping("/{id}/logs")
    public ApiResponse<List<JobLogResponse>> listLogs(@PathVariable Long id) {
        List<JobLogResponse> responses = yarnSubmitService.listLogs(id).stream()
                .map(JobLogResponse::from)
                .collect(Collectors.toList());
        return ApiResponse.ok(responses);
    }
}
