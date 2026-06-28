package com.rookie.submit.platform.job.controller;

import com.rookie.submit.platform.common.ApiResponse;
import com.rookie.submit.platform.job.dto.CreateJobRequest;
import com.rookie.submit.platform.job.dto.JobResponse;
import com.rookie.submit.platform.job.dto.JobVersionResponse;
import com.rookie.submit.platform.job.dto.SqlPreviewRequest;
import com.rookie.submit.platform.job.dto.SqlPreviewResponse;
import com.rookie.submit.platform.job.service.JobService;
import com.rookie.submit.platform.job.service.SqlGeneratorService;
import com.rookie.submit.platform.submit.dto.JobInstanceResponse;
import com.rookie.submit.platform.submit.dto.SubmitJobRequest;
import com.rookie.submit.platform.submit.service.YarnSubmitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {

    private final SqlGeneratorService sqlGeneratorService;
    private final JobService jobService;
    private final YarnSubmitService yarnSubmitService;

    public JobController(
            SqlGeneratorService sqlGeneratorService,
            JobService jobService,
            YarnSubmitService yarnSubmitService) {
        this.sqlGeneratorService = sqlGeneratorService;
        this.jobService = jobService;
        this.yarnSubmitService = yarnSubmitService;
    }

    @PostMapping
    public ApiResponse<JobResponse> create(@Valid @RequestBody CreateJobRequest request) {
        return ApiResponse.ok(JobResponse.from(jobService.create(request)));
    }

    @PutMapping("/{id}")
    public ApiResponse<JobResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateJobRequest request) {
        return ApiResponse.ok(JobResponse.from(jobService.update(id, request)));
    }

    @GetMapping
    public ApiResponse<List<JobResponse>> list(@RequestParam(required = false) String status) {
        List<JobResponse> responses = jobService.list(status).stream()
                .map(JobResponse::from)
                .collect(Collectors.toList());
        return ApiResponse.ok(responses);
    }

    @GetMapping("/{id}")
    public ApiResponse<JobResponse> get(@PathVariable Long id) {
        return ApiResponse.ok(JobResponse.from(jobService.get(id)));
    }

    @PostMapping("/sql/preview")
    public ApiResponse<SqlPreviewResponse> previewSql(@Valid @RequestBody SqlPreviewRequest request) {
        return ApiResponse.ok(sqlGeneratorService.preview(request));
    }

    @PostMapping("/sql/simulate")
    public ApiResponse<SqlPreviewResponse> simulateSql(@Valid @RequestBody SqlPreviewRequest request) {
        return ApiResponse.ok(sqlGeneratorService.preview(request));
    }

    @PostMapping("/{id}/versions")
    public ApiResponse<JobVersionResponse> createVersion(@PathVariable Long id) {
        return ApiResponse.ok(JobVersionResponse.from(jobService.createVersion(id)));
    }

    @GetMapping("/{id}/versions")
    public ApiResponse<List<JobVersionResponse>> listVersions(@PathVariable Long id) {
        List<JobVersionResponse> responses = jobService.listVersions(id).stream()
                .map(JobVersionResponse::from)
                .collect(Collectors.toList());
        return ApiResponse.ok(responses);
    }

    @PostMapping("/{id}/submit")
    public ApiResponse<JobInstanceResponse> submit(
            @PathVariable Long id,
            @RequestBody(required = false) SubmitJobRequest request) {
        return ApiResponse.ok(JobInstanceResponse.from(yarnSubmitService.submit(id, request)));
    }
}
