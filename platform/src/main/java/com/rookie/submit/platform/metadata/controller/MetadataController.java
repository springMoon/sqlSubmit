package com.rookie.submit.platform.metadata.controller;

import com.rookie.submit.platform.common.ApiResponse;
import com.rookie.submit.platform.metadata.dto.ColumnMetadataResponse;
import com.rookie.submit.platform.metadata.dto.MetadataSyncResponse;
import com.rookie.submit.platform.metadata.dto.TableMetadataResponse;
import com.rookie.submit.platform.metadata.service.MetadataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class MetadataController {

    private final MetadataService metadataService;

    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @PostMapping("/datasources/{id}/metadata/sync")
    public ApiResponse<MetadataSyncResponse> syncMetadata(@PathVariable Long id) throws Exception {
        return ApiResponse.ok(metadataService.syncMysqlMetadata(id));
    }

    @GetMapping("/datasources/{id}/tables")
    public ApiResponse<List<TableMetadataResponse>> listTables(@PathVariable Long id) {
        List<TableMetadataResponse> responses = metadataService.listTables(id).stream()
                .map(TableMetadataResponse::from)
                .collect(Collectors.toList());
        return ApiResponse.ok(responses);
    }

    @GetMapping("/tables/{tableId}/columns")
    public ApiResponse<List<ColumnMetadataResponse>> listColumns(@PathVariable Long tableId) {
        List<ColumnMetadataResponse> responses = metadataService.listColumns(tableId).stream()
                .map(ColumnMetadataResponse::from)
                .collect(Collectors.toList());
        return ApiResponse.ok(responses);
    }
}
