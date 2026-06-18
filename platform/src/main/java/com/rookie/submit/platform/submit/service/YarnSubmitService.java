package com.rookie.submit.platform.submit.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rookie.submit.platform.config.PlatformFlinkProperties;
import com.rookie.submit.platform.job.entity.SyncJobEntity;
import com.rookie.submit.platform.job.entity.SyncJobVersionEntity;
import com.rookie.submit.platform.job.mapper.SyncJobMapper;
import com.rookie.submit.platform.job.mapper.SyncJobVersionMapper;
import com.rookie.submit.platform.submit.dto.SubmitJobRequest;
import com.rookie.submit.platform.submit.entity.SyncJobInstanceEntity;
import com.rookie.submit.platform.submit.entity.SyncJobLogEntity;
import com.rookie.submit.platform.submit.mapper.SyncJobInstanceMapper;
import com.rookie.submit.platform.submit.mapper.SyncJobLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YarnSubmitService {

    private static final Logger LOG = LoggerFactory.getLogger(YarnSubmitService.class);
    private static final Pattern YARN_APP_PATTERN = Pattern.compile("(application_\\d+_\\d+)");
    private static final Pattern FLINK_JOB_PATTERN = Pattern.compile("JobID\\s+([a-fA-F0-9]{32})");

    private final PlatformFlinkProperties flinkProperties;
    private final SyncJobMapper jobMapper;
    private final SyncJobVersionMapper jobVersionMapper;
    private final SyncJobInstanceMapper jobInstanceMapper;
    private final SyncJobLogMapper jobLogMapper;

    public YarnSubmitService(
            PlatformFlinkProperties flinkProperties,
            SyncJobMapper jobMapper,
            SyncJobVersionMapper jobVersionMapper,
            SyncJobInstanceMapper jobInstanceMapper,
            SyncJobLogMapper jobLogMapper) {
        this.flinkProperties = flinkProperties;
        this.jobMapper = jobMapper;
        this.jobVersionMapper = jobVersionMapper;
        this.jobInstanceMapper = jobInstanceMapper;
        this.jobLogMapper = jobLogMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public SyncJobInstanceEntity submit(Long jobId, SubmitJobRequest request) {
        SyncJobEntity job = requiredJob(jobId);
        SyncJobVersionEntity version = requiredVersion(job, request == null ? null : request.getVersion());
        GeneratedFiles files = writeJobFiles(job, version);
        List<String> command = buildCommand(job, files);

        LocalDateTime now = LocalDateTime.now();
        SyncJobInstanceEntity instance = new SyncJobInstanceEntity();
        instance.setJobId(job.getId());
        instance.setJobVersion(version.getVersion());
        instance.setSubmitCommand(toDisplayCommand(command));
        instance.setSqlPath(files.sqlPath.toString());
        instance.setPropPath(files.propPath.toString());
        instance.setStatus(flinkProperties.isSubmitEnabled() ? "SUBMITTED" : "DRY_RUN");
        instance.setStartTime(now);
        instance.setCreatedAt(now);
        instance.setUpdatedAt(now);
        jobInstanceMapper.insert(instance);

        saveLog(instance.getId(), "INFO", "generated sql file: " + files.sqlPath);
        saveLog(instance.getId(), "INFO", "generated properties file: " + files.propPath);
        saveLog(instance.getId(), "INFO", "submit command: " + instance.getSubmitCommand());

        if (!flinkProperties.isSubmitEnabled()) {
            saveLog(instance.getId(), "INFO", "platform.flink.submit-enabled=false, skip external flink command");
            LOG.info("created dry-run job submit instance, jobId: {}, version: {}, instanceId: {}",
                    jobId, version.getVersion(), instance.getId());
            return instance;
        }

        executeCommand(instance, command);
        updateJobStatusAfterSubmit(job, instance);
        return instance;
    }

    public SyncJobInstanceEntity getInstance(Long id) {
        SyncJobInstanceEntity entity = jobInstanceMapper.selectById(id);
        if (entity == null) {
            throw new IllegalArgumentException("任务实例不存在: " + id);
        }
        return entity;
    }

    public List<SyncJobInstanceEntity> listInstances(Long jobId) {
        LambdaQueryWrapper<SyncJobInstanceEntity> wrapper = new LambdaQueryWrapper<>();
        if (jobId != null) {
            wrapper.eq(SyncJobInstanceEntity::getJobId, jobId);
        }
        wrapper.orderByDesc(SyncJobInstanceEntity::getId);
        return jobInstanceMapper.selectList(wrapper);
    }

    private SyncJobEntity requiredJob(Long jobId) {
        SyncJobEntity job = jobMapper.selectById(jobId);
        if (job == null) {
            throw new IllegalArgumentException("任务不存在: " + jobId);
        }
        if (job.getCurrentVersion() == null || job.getCurrentVersion() <= 0) {
            throw new IllegalArgumentException("任务还没有可提交版本，请先创建 SQL 版本: " + jobId);
        }
        return job;
    }

    private SyncJobVersionEntity requiredVersion(SyncJobEntity job, Integer requestVersion) {
        int version = requestVersion == null ? job.getCurrentVersion() : requestVersion;
        LambdaQueryWrapper<SyncJobVersionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SyncJobVersionEntity::getJobId, job.getId())
                .eq(SyncJobVersionEntity::getVersion, version);
        SyncJobVersionEntity entity = jobVersionMapper.selectOne(wrapper);
        if (entity == null) {
            throw new IllegalArgumentException("任务版本不存在: jobId=" + job.getId() + ", version=" + version);
        }
        return entity;
    }

    private GeneratedFiles writeJobFiles(SyncJobEntity job, SyncJobVersionEntity version) {
        try {
            Path sqlDir = Paths.get(required(flinkProperties.getGeneratedSqlDir(), "platform.flink.generated-sql-dir"));
            Path propDir = Paths.get(required(flinkProperties.getGeneratedPropDir(), "platform.flink.generated-prop-dir"));
            Files.createDirectories(sqlDir);
            Files.createDirectories(propDir);

            String baseName = "job_" + job.getId() + "_v" + version.getVersion();
            Path sqlPath = sqlDir.resolve(baseName + ".sql");
            Path propPath = propDir.resolve(baseName + ".properties");
            Files.write(sqlPath, version.getGeneratedSql().getBytes(StandardCharsets.UTF_8));
            Files.write(propPath, safeString(version.getGeneratedProperties()).getBytes(StandardCharsets.UTF_8));
            return new GeneratedFiles(sqlPath, propPath);
        } catch (Exception e) {
            throw new IllegalArgumentException("生成任务文件失败: " + e.getMessage(), e);
        }
    }

    private List<String> buildCommand(SyncJobEntity job, GeneratedFiles files) {
        String flinkExecutable = Paths.get(required(flinkProperties.getHome(), "platform.flink.home"), "bin", "flink").toString();
        List<String> command = new ArrayList<>();
        command.add(flinkExecutable);
        command.add("run");
        command.add("-m");
        command.add("yarn-cluster");
        command.add("-ynm");
        command.add(job.getJobName());
        String queue = trimToNull(flinkProperties.getYarnQueue());
        if (queue != null) {
            command.add("-yqu");
            command.add(queue);
        }
        command.add("-c");
        command.add(required(flinkProperties.getMainClass(), "platform.flink.main-class"));
        command.add(required(flinkProperties.getSqlsubmitJarPath(), "platform.flink.sqlsubmit-jar-path"));
        command.add("--sql");
        command.add(files.sqlPath.toString());
        command.add("--job.prop.file");
        command.add(files.propPath.toString());
        return command;
    }

    private void executeCommand(SyncJobInstanceEntity instance, List<String> command) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> outputFuture = null;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            outputFuture = executorService.submit(() -> readProcessOutput(process));

            boolean finished = process.waitFor(flinkProperties.getSubmitTimeoutSeconds(), TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                String output = readFutureOutput(outputFuture);
                markFailed(instance, "Flink submit timeout after " + flinkProperties.getSubmitTimeoutSeconds() + " seconds", output);
                return;
            }

            String outputText = readFutureOutput(outputFuture);
            saveLog(instance.getId(), "INFO", outputText);
            if (process.exitValue() == 0) {
                instance.setStatus("SUBMITTED");
                instance.setFlinkJobId(extractFirst(FLINK_JOB_PATTERN, outputText));
                instance.setYarnApplicationId(extractFirst(YARN_APP_PATTERN, outputText));
                instance.setUpdatedAt(LocalDateTime.now());
                jobInstanceMapper.updateById(instance);
                LOG.info("submitted flink job, instanceId: {}, flinkJobId: {}, yarnApplicationId: {}",
                        instance.getId(), instance.getFlinkJobId(), instance.getYarnApplicationId());
            } else {
                markFailed(instance, "Flink submit failed, exitCode=" + process.exitValue(), outputText);
            }
        } catch (Exception e) {
            markFailed(instance, e.getMessage(), readFutureOutput(outputFuture));
        } finally {
            executorService.shutdownNow();
        }
    }

    private String readProcessOutput(Process process) throws Exception {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append('\n');
            }
        }
        return output.toString();
    }

    private String readFutureOutput(Future<String> future) {
        if (future == null) {
            return "";
        }
        try {
            return future.get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            return "";
        }
    }

    private void markFailed(SyncJobInstanceEntity instance, String errorMessage, String output) {
        instance.setStatus("FAILED");
        instance.setEndTime(LocalDateTime.now());
        instance.setErrorMessage(limit(errorMessage, 2000));
        instance.setUpdatedAt(LocalDateTime.now());
        jobInstanceMapper.updateById(instance);
        saveLog(instance.getId(), "ERROR", safeString(errorMessage));
        if (output != null && !output.trim().isEmpty()) {
            saveLog(instance.getId(), "ERROR", output);
        }
        LOG.warn("flink submit failed, instanceId: {}, message: {}", instance.getId(), errorMessage);
    }

    private void updateJobStatusAfterSubmit(SyncJobEntity job, SyncJobInstanceEntity instance) {
        if ("SUBMITTED".equals(instance.getStatus())) {
            job.setStatus("RUNNING");
        } else if ("FAILED".equals(instance.getStatus())) {
            job.setStatus("FAILED");
        }
        job.setUpdatedAt(LocalDateTime.now());
        jobMapper.updateById(job);
    }

    private void saveLog(Long instanceId, String level, String message) {
        SyncJobLogEntity log = new SyncJobLogEntity();
        log.setJobInstanceId(instanceId);
        log.setLevel(level);
        log.setMessage(limit(safeString(message), 60000));
        log.setCreatedAt(LocalDateTime.now());
        jobLogMapper.insert(log);
    }

    private String extractFirst(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(safeString(text));
        return matcher.find() ? matcher.group(1) : null;
    }

    private String toDisplayCommand(List<String> command) {
        List<String> display = new ArrayList<>();
        for (String item : command) {
            display.add(quoteShellArg(item));
        }
        return String.join(" ", display);
    }

    private String quoteShellArg(String value) {
        if (value.matches("[A-Za-z0-9_./:=\\-]+")) {
            return value;
        }
        return "'" + value.replace("'", "'\"'\"'") + "'";
    }

    private String required(String value, String key) {
        String text = trimToNull(value);
        if (text == null) {
            throw new IllegalArgumentException("缺少平台配置: " + key);
        }
        return text;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String safeString(String value) {
        return value == null ? "" : value;
    }

    private String limit(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength);
    }

    private static class GeneratedFiles {
        private final Path sqlPath;
        private final Path propPath;

        private GeneratedFiles(Path sqlPath, Path propPath) {
            this.sqlPath = sqlPath;
            this.propPath = propPath;
        }
    }
}
