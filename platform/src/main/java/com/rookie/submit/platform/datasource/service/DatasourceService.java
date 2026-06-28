package com.rookie.submit.platform.datasource.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rookie.submit.platform.datasource.dto.CreateDatasourceRequest;
import com.rookie.submit.platform.datasource.dto.TestConnectionResponse;
import com.rookie.submit.platform.datasource.entity.SyncDatasourceEntity;
import com.rookie.submit.platform.datasource.mapper.SyncDatasourceMapper;
import com.rookie.submit.platform.datasource.model.DatasourceDefinition;
import com.rookie.submit.platform.datasource.model.DatasourceType;
import com.rookie.submit.platform.job.entity.SyncJobEntity;
import com.rookie.submit.platform.job.mapper.SyncJobMapper;
import com.rookie.submit.platform.metadata.entity.SyncColumnMetadataEntity;
import com.rookie.submit.platform.metadata.entity.SyncTableMetadataEntity;
import com.rookie.submit.platform.metadata.mapper.SyncColumnMetadataMapper;
import com.rookie.submit.platform.metadata.mapper.SyncTableMetadataMapper;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DatasourceService {

    private static final Logger LOG = LoggerFactory.getLogger(DatasourceService.class);

    private final ObjectMapper objectMapper;
    private final SyncDatasourceMapper datasourceMapper;
    private final SyncTableMetadataMapper tableMetadataMapper;
    private final SyncColumnMetadataMapper columnMetadataMapper;
    private final SyncJobMapper jobMapper;

    public DatasourceService(
            ObjectMapper objectMapper,
            SyncDatasourceMapper datasourceMapper,
            SyncTableMetadataMapper tableMetadataMapper,
            SyncColumnMetadataMapper columnMetadataMapper,
            SyncJobMapper jobMapper) {
        this.objectMapper = objectMapper;
        this.datasourceMapper = datasourceMapper;
        this.tableMetadataMapper = tableMetadataMapper;
        this.columnMetadataMapper = columnMetadataMapper;
        this.jobMapper = jobMapper;
    }

    @PostConstruct
    public void initBuiltinDatasources() {
        saveBuiltin("datagen", DatasourceType.DATAGEN);
        saveBuiltin("print", DatasourceType.PRINT);
    }

    public DatasourceDefinition create(CreateDatasourceRequest request) {
        ensureNameNotExists(request.getName());
        validateConfig(request.getType(), request.getConfig());

        LocalDateTime now = LocalDateTime.now();
        SyncDatasourceEntity entity = new SyncDatasourceEntity();
        entity.setName(request.getName());
        entity.setType(request.getType().name());
        entity.setConfigJson(toJson(request.getConfig()));
        entity.setEnabled(request.getEnabled() == null ? Boolean.TRUE : request.getEnabled());
        entity.setRemark(request.getRemark());
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        datasourceMapper.insert(entity);
        LOG.info("created datasource, id: {}, name: {}, type: {}", entity.getId(), entity.getName(), entity.getType());
        return toDefinition(entity);
    }

    public DatasourceDefinition update(Long id, CreateDatasourceRequest request) {
        SyncDatasourceEntity entity = requiredEntity(id);
        ensureNotBuiltin(entity);
        ensureNameNotExists(request.getName(), id);
        JsonNode config = normalizeUpdateConfig(entity, request);
        validateConfig(request.getType(), config);

        entity.setName(request.getName().trim());
        entity.setType(request.getType().name());
        entity.setConfigJson(toJson(config));
        entity.setEnabled(request.getEnabled() == null ? Boolean.TRUE : request.getEnabled());
        entity.setRemark(request.getRemark());
        entity.setUpdatedAt(LocalDateTime.now());
        datasourceMapper.updateById(entity);
        LOG.info("updated datasource, id: {}, name: {}, type: {}", entity.getId(), entity.getName(), entity.getType());
        return toDefinition(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        SyncDatasourceEntity entity = requiredEntity(id);
        ensureNotBuiltin(entity);
        ensureDatasourceNotUsedByJob(id);

        LambdaQueryWrapper<SyncTableMetadataEntity> tableWrapper = new LambdaQueryWrapper<>();
        tableWrapper.eq(SyncTableMetadataEntity::getDatasourceId, id);
        List<SyncTableMetadataEntity> tables = tableMetadataMapper.selectList(tableWrapper);
        List<Long> tableIds = tables.stream().map(SyncTableMetadataEntity::getId).collect(Collectors.toList());
        if (!tableIds.isEmpty()) {
            LambdaQueryWrapper<SyncColumnMetadataEntity> columnWrapper = new LambdaQueryWrapper<>();
            columnWrapper.in(SyncColumnMetadataEntity::getTableId, tableIds);
            columnMetadataMapper.delete(columnWrapper);
        }
        tableMetadataMapper.delete(tableWrapper);
        datasourceMapper.deleteById(id);
        LOG.info("deleted datasource, id: {}, name: {}", entity.getId(), entity.getName());
    }

    public List<DatasourceDefinition> list(DatasourceType type) {
        LambdaQueryWrapper<SyncDatasourceEntity> wrapper = new LambdaQueryWrapper<>();
        if (type != null) {
            wrapper.eq(SyncDatasourceEntity::getType, type.name());
        }
        wrapper.orderByAsc(SyncDatasourceEntity::getId);
        return datasourceMapper.selectList(wrapper).stream()
                .map(this::toDefinition)
                .collect(Collectors.toList());
    }

    public DatasourceDefinition get(Long id) {
        SyncDatasourceEntity entity = datasourceMapper.selectById(id);
        if (entity == null) {
            throw new IllegalArgumentException("数据源不存在: " + id);
        }
        return toDefinition(entity);
    }

    public TestConnectionResponse testConnection(Long id) {
        DatasourceDefinition datasource = get(id);
        try {
            switch (datasource.getType()) {
                case MYSQL:
                    testMysql(datasource.getConfig());
                    break;
                case KAFKA:
                    testKafka(datasource.getConfig());
                    break;
                case DATAGEN:
                case PRINT:
                    return new TestConnectionResponse(true, "内置数据源无需连接测试");
                default:
                    throw new IllegalArgumentException("不支持的数据源类型: " + datasource.getType());
            }
            return new TestConnectionResponse(true, "连接成功");
        } catch (Exception e) {
            LOG.warn("datasource connection test failed, id: {}, type: {}", id, datasource.getType(), e);
            return new TestConnectionResponse(false, e.getMessage());
        }
    }

    public List<String> listKafkaTopics(Long id) throws Exception {
        DatasourceDefinition datasource = get(id);
        if (datasource.getType() != DatasourceType.KAFKA) {
            throw new IllegalArgumentException("仅支持读取 Kafka 数据源 Topic");
        }
        String bootstrapServers = required(datasource.getConfig(), "bootstrapServers");
        Properties properties = kafkaAdminProperties(bootstrapServers);
        try (AdminClient adminClient = AdminClient.create(properties)) {
            ListTopicsResult result = adminClient.listTopics();
            List<String> topics = new ArrayList<>(result.names().get(5, TimeUnit.SECONDS));
            Collections.sort(topics);
            LOG.info("loaded kafka topics, datasourceId: {}, topicCount: {}", id, topics.size());
            return topics;
        }
    }

    private void saveBuiltin(String name, DatasourceType type) {
        LambdaQueryWrapper<SyncDatasourceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SyncDatasourceEntity::getName, name);
        if (datasourceMapper.selectCount(wrapper) > 0) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        SyncDatasourceEntity entity = new SyncDatasourceEntity();
        entity.setName(name);
        entity.setType(type.name());
        entity.setConfigJson(toJson(objectMapper.createObjectNode().put("builtin", true)));
        entity.setEnabled(Boolean.TRUE);
        entity.setRemark("内置数据源");
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        datasourceMapper.insert(entity);
        LOG.info("created builtin datasource, id: {}, name: {}, type: {}", entity.getId(), entity.getName(), entity.getType());
    }

    private void ensureNameNotExists(String name) {
        ensureNameNotExists(name, null);
    }

    private void ensureNameNotExists(String name, Long selfId) {
        LambdaQueryWrapper<SyncDatasourceEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SyncDatasourceEntity::getName, name.trim());
        if (selfId != null) {
            wrapper.ne(SyncDatasourceEntity::getId, selfId);
        }
        if (datasourceMapper.selectCount(wrapper) > 0) {
            throw new IllegalArgumentException("数据源名称已存在: " + name);
        }
    }

    private SyncDatasourceEntity requiredEntity(Long id) {
        SyncDatasourceEntity entity = datasourceMapper.selectById(id);
        if (entity == null) {
            throw new IllegalArgumentException("数据源不存在: " + id);
        }
        return entity;
    }

    private void ensureNotBuiltin(SyncDatasourceEntity entity) {
        if ("datagen".equals(entity.getName()) || "print".equals(entity.getName())) {
            throw new IllegalArgumentException("内置数据源不允许修改或删除: " + entity.getName());
        }
    }

    private void ensureDatasourceNotUsedByJob(Long id) {
        LambdaQueryWrapper<SyncJobEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SyncJobEntity::getSourceDatasourceId, id)
                .or()
                .eq(SyncJobEntity::getSinkDatasourceId, id);
        if (jobMapper.selectCount(wrapper) > 0) {
            throw new IllegalArgumentException("数据源已被任务引用，不能删除");
        }
    }

    private void validateConfig(DatasourceType type, JsonNode config) {
        switch (type) {
            case MYSQL:
                required(config, "url");
                required(config, "username");
                required(config, "password");
                break;
            case KAFKA:
                required(config, "bootstrapServers");
                break;
            case DATAGEN:
            case PRINT:
                break;
            default:
                throw new IllegalArgumentException("不支持的数据源类型: " + type);
        }
    }

    private void testMysql(JsonNode config) throws Exception {
        String url = required(config, "url");
        String username = required(config, "username");
        String password = required(config, "password");
        try (Connection ignored = DriverManager.getConnection(url, username, password)) {
            // Opening and closing the connection is enough for first-phase validation.
        }
    }

    private void testKafka(JsonNode config) throws Exception {
        String bootstrapServers = required(config, "bootstrapServers");
        String topic = text(config, "topic");

        Properties properties = kafkaAdminProperties(bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(properties)) {
            if (topic == null || topic.trim().isEmpty()) {
                ListTopicsResult result = adminClient.listTopics();
                result.names().get(5, TimeUnit.SECONDS);
            } else {
                DescribeTopicsResult result = adminClient.describeTopics(Collections.singletonList(topic));
                result.all().get(5, TimeUnit.SECONDS);
            }
        }
    }

    private Properties kafkaAdminProperties(String bootstrapServers) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("request.timeout.ms", "5000");
        properties.put("default.api.timeout.ms", "5000");
        return properties;
    }

    private String required(JsonNode config, String key) {
        String value = text(config, key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("缺少数据源配置: " + key);
        }
        return value.trim();
    }

    private String text(JsonNode config, String key) {
        JsonNode value = config == null ? null : config.get(key);
        return value == null || value.isNull() ? null : value.asText();
    }

    private JsonNode normalizeUpdateConfig(SyncDatasourceEntity entity, CreateDatasourceRequest request) {
        JsonNode config = request.getConfig();
        if (request.getType() != DatasourceType.MYSQL || config == null || !"******".equals(text(config, "password"))) {
            return config;
        }
        JsonNode oldConfig = parseJson(entity.getConfigJson());
        ObjectNode normalized = objectMapper.createObjectNode();
        Iterator<Map.Entry<String, JsonNode>> fields = config.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            normalized.set(field.getKey(), field.getValue());
        }
        normalized.put("password", text(oldConfig, "password"));
        return normalized;
    }

    private DatasourceDefinition toDefinition(SyncDatasourceEntity entity) {
        DatasourceDefinition datasource = new DatasourceDefinition();
        datasource.setId(entity.getId());
        datasource.setName(entity.getName());
        datasource.setType(DatasourceType.valueOf(entity.getType()));
        datasource.setConfig(parseJson(entity.getConfigJson()));
        datasource.setEnabled(entity.getEnabled());
        datasource.setRemark(entity.getRemark());
        datasource.setCreatedAt(entity.getCreatedAt());
        datasource.setUpdatedAt(entity.getUpdatedAt());
        return datasource;
    }

    private String toJson(JsonNode jsonNode) {
        try {
            return objectMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            throw new IllegalArgumentException("数据源配置 JSON 序列化失败", e);
        }
    }

    private JsonNode parseJson(String value) {
        try {
            return objectMapper.readTree(value);
        } catch (Exception e) {
            throw new IllegalArgumentException("数据源配置 JSON 解析失败", e);
        }
    }
}
