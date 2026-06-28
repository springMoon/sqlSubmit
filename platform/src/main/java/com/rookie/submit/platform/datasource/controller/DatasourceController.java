package com.rookie.submit.platform.datasource.controller;

import com.rookie.submit.platform.common.ApiResponse;
import com.rookie.submit.platform.datasource.dto.CreateDatasourceRequest;
import com.rookie.submit.platform.datasource.dto.DatasourceResponse;
import com.rookie.submit.platform.datasource.dto.TestConnectionResponse;
import com.rookie.submit.platform.datasource.model.DatasourceType;
import com.rookie.submit.platform.datasource.service.DatasourceService;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/v1/datasources")
public class DatasourceController {

    private final DatasourceService datasourceService;

    public DatasourceController(DatasourceService datasourceService) {
        this.datasourceService = datasourceService;
    }

    @PostMapping
    public ApiResponse<DatasourceResponse> create(@Valid @RequestBody CreateDatasourceRequest request) {
        return ApiResponse.ok(DatasourceResponse.from(datasourceService.create(request)));
    }

    @PutMapping("/{id}")
    public ApiResponse<DatasourceResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateDatasourceRequest request) {
        return ApiResponse.ok(DatasourceResponse.from(datasourceService.update(id, request)));
    }

    @GetMapping
    public ApiResponse<List<DatasourceResponse>> list(@RequestParam(required = false) DatasourceType type) {
        List<DatasourceResponse> responses = datasourceService.list(type).stream()
                .map(DatasourceResponse::from)
                .collect(Collectors.toList());
        return ApiResponse.ok(responses);
    }

    @GetMapping("/{id}")
    public ApiResponse<DatasourceResponse> get(@PathVariable Long id) {
        return ApiResponse.ok(DatasourceResponse.from(datasourceService.get(id)));
    }

    @PostMapping("/{id}/test")
    public ApiResponse<TestConnectionResponse> test(@PathVariable Long id) {
        return ApiResponse.ok(datasourceService.testConnection(id));
    }

    @GetMapping("/{id}/live/topics")
    public ApiResponse<List<String>> listKafkaTopics(@PathVariable Long id) throws Exception {
        return ApiResponse.ok(datasourceService.listKafkaTopics(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        datasourceService.delete(id);
        return ApiResponse.ok(null);
    }
}
