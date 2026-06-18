package com.rookie.submit.platform.job.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rookie.submit.platform.datasource.model.DatasourceDefinition;
import com.rookie.submit.platform.datasource.model.DatasourceType;
import com.rookie.submit.platform.datasource.service.DatasourceService;
import com.rookie.submit.platform.job.dto.CreateJobRequest;
import com.rookie.submit.platform.job.dto.SqlPreviewResponse;
import com.rookie.submit.platform.job.entity.SyncJobEntity;
import com.rookie.submit.platform.job.entity.SyncJobVersionEntity;
import com.rookie.submit.platform.job.mapper.SyncJobMapper;
import com.rookie.submit.platform.job.mapper.SyncJobVersionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class JobService {

    private static final Logger LOG = LoggerFactory.getLogger(JobService.class);

    private final ObjectMapper objectMapper;
    private final DatasourceService datasourceService;
    private final SqlGeneratorService sqlGeneratorService;
    private final SyncJobMapper jobMapper;
    private final SyncJobVersionMapper jobVersionMapper;

    public JobService(
            ObjectMapper objectMapper,
            DatasourceService datasourceService,
            SqlGeneratorService sqlGeneratorService,
            SyncJobMapper jobMapper,
            SyncJobVersionMapper jobVersionMapper) {
        this.objectMapper = objectMapper;
        this.datasourceService = datasourceService;
        this.sqlGeneratorService = sqlGeneratorService;
        this.jobMapper = jobMapper;
        this.jobVersionMapper = jobVersionMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public SyncJobEntity create(CreateJobRequest request) {
        ensureJobNameNotExists(request.getJobName());
        SqlPreviewResponse preview = sqlGeneratorService.preview(request);
        DatasourceDefinition sink = datasourceService.get(request.getSinkDatasourceId());

        LocalDateTime now = LocalDateTime.now();
        SyncJobEntity entity = new SyncJobEntity();
        entity.setJobName(request.getJobName().trim());
        entity.setSourceDatasourceId(request.getSourceDatasourceId());
        entity.setSourceTableId(request.getSourceTableId());
        entity.setSinkDatasourceId(request.getSinkDatasourceId());
        entity.setSinkTableName(resolveSinkTableName(request.getSinkTableName(), sink));
        entity.setFieldMappingJson(toJson(request.getFieldMapping() == null ? Collections.emptyList() : request.getFieldMapping()));
        entity.setRuntimeConfigJson(request.getRuntimeConfig() == null ? null : toJson(request.getRuntimeConfig()));
        entity.setGeneratedSql(preview.getSql());
        entity.setStatus("DRAFT");
        entity.setCurrentVersion(0);
        entity.setRemark(request.getRemark());
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        jobMapper.insert(entity);
        LOG.info("created sync job draft, id: {}, name: {}", entity.getId(), entity.getJobName());
        return entity;
    }

    public List<SyncJobEntity> list(String status) {
        LambdaQueryWrapper<SyncJobEntity> wrapper = new LambdaQueryWrapper<>();
        if (status != null && !status.trim().isEmpty()) {
            wrapper.eq(SyncJobEntity::getStatus, status.trim());
        }
        wrapper.orderByDesc(SyncJobEntity::getId);
        return jobMapper.selectList(wrapper);
    }

    public SyncJobEntity get(Long id) {
        SyncJobEntity entity = jobMapper.selectById(id);
        if (entity == null) {
            throw new IllegalArgumentException("任务不存在: " + id);
        }
        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    public SyncJobVersionEntity createVersion(Long jobId) {
        SyncJobEntity job = get(jobId);
        if (job.getGeneratedSql() == null || job.getGeneratedSql().trim().isEmpty()) {
            throw new IllegalArgumentException("任务没有已生成 SQL，无法创建版本: " + jobId);
        }

        int nextVersion = job.getCurrentVersion() == null ? 1 : job.getCurrentVersion() + 1;
        SyncJobVersionEntity version = new SyncJobVersionEntity();
        version.setJobId(job.getId());
        version.setVersion(nextVersion);
        version.setGeneratedSql(job.getGeneratedSql());
        version.setGeneratedProperties(buildDefaultProperties(job));
        version.setGeneratorConfigJson(buildGeneratorConfig(job));
        version.setCreatedAt(LocalDateTime.now());
        jobVersionMapper.insert(version);

        job.setCurrentVersion(nextVersion);
        job.setStatus("READY");
        job.setUpdatedAt(LocalDateTime.now());
        jobMapper.updateById(job);
        LOG.info("created sync job version, jobId: {}, version: {}", jobId, nextVersion);
        return version;
    }

    public List<SyncJobVersionEntity> listVersions(Long jobId) {
        get(jobId);
        LambdaQueryWrapper<SyncJobVersionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SyncJobVersionEntity::getJobId, jobId)
                .orderByDesc(SyncJobVersionEntity::getVersion);
        return jobVersionMapper.selectList(wrapper);
    }

    private void ensureJobNameNotExists(String jobName) {
        LambdaQueryWrapper<SyncJobEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SyncJobEntity::getJobName, jobName.trim());
        if (jobMapper.selectCount(wrapper) > 0) {
            throw new IllegalArgumentException("任务名称已存在: " + jobName);
        }
    }

    private String resolveSinkTableName(String requestSinkTableName, DatasourceDefinition sink) {
        String tableName = trimToNull(requestSinkTableName);
        if (tableName != null) {
            return tableName;
        }
        if (sink.getType() == DatasourceType.KAFKA) {
            return required(sink.getConfig(), "topic");
        }
        if (sink.getType() == DatasourceType.PRINT) {
            return "print";
        }
        throw new IllegalArgumentException("目标表名不能为空");
    }

    private String buildDefaultProperties(SyncJobEntity job) {
        JsonNode runtimeConfig = parseJson(job.getRuntimeConfigJson());
        String parallelism = text(runtimeConfig, "parallelism", "1");
        String checkpointInterval = text(runtimeConfig, "checkpointInterval", "60");
        String checkpointTimeout = text(runtimeConfig, "checkpointTimeout", "600");
        String stateBackend = text(runtimeConfig, "stateBackend", "hashmap");
        String checkpointDir = text(runtimeConfig, "checkpointDir", "file:///tmp/flink-checkpoints/" + job.getJobName());

        return "job.name=" + job.getJobName() + "\n"
                + "table.exec.resource.default-parallelism=" + parallelism + "\n"
                + "checkpoint.interval=" + checkpointInterval + "\n"
                + "checkpoint.timeout=" + checkpointTimeout + "\n"
                + "state.backend=" + stateBackend + "\n"
                + "checkpoint.dir=" + checkpointDir + "\n"
                + "mysql.catalog.enable=false\n";
    }

    private String buildGeneratorConfig(SyncJobEntity job) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("sourceDatasourceId", job.getSourceDatasourceId());
        if (job.getSourceTableId() != null) {
            node.put("sourceTableId", job.getSourceTableId());
        }
        node.put("sinkDatasourceId", job.getSinkDatasourceId());
        node.put("sinkTableName", job.getSinkTableName());
        node.set("fieldMapping", parseJson(job.getFieldMappingJson()));
        node.set("runtimeConfig", parseJson(job.getRuntimeConfigJson()));
        return toJson(node);
    }

    private String required(JsonNode config, String key) {
        String value = text(config, key, null);
        if (value == null) {
            throw new IllegalArgumentException("缺少数据源配置: " + key);
        }
        return value;
    }

    private String text(JsonNode config, String key, String defaultValue) {
        JsonNode value = config == null ? null : config.get(key);
        String text = value == null || value.isNull() ? null : value.asText();
        text = trimToNull(text);
        return text == null ? defaultValue : text;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new IllegalArgumentException("任务配置 JSON 序列化失败", e);
        }
    }

    private JsonNode parseJson(String value) {
        if (value == null || value.trim().isEmpty()) {
            return objectMapper.createObjectNode();
        }
        try {
            return objectMapper.readTree(value);
        } catch (Exception e) {
            throw new IllegalArgumentException("任务配置 JSON 解析失败", e);
        }
    }
}
