package com.rookie.submit.platform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "platform.flink")
public class PlatformFlinkProperties {

    private String home;
    private String sqlsubmitJarPath;
    private String generatedSqlDir;
    private String generatedPropDir;
    private String yarnQueue;
    private String restUrl;
    private String mainClass = "com.rookie.submit.main.SqlSubmit";
    private boolean submitEnabled = false;
    private long submitTimeoutSeconds = 120;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getSqlsubmitJarPath() {
        return sqlsubmitJarPath;
    }

    public void setSqlsubmitJarPath(String sqlsubmitJarPath) {
        this.sqlsubmitJarPath = sqlsubmitJarPath;
    }

    public String getGeneratedSqlDir() {
        return generatedSqlDir;
    }

    public void setGeneratedSqlDir(String generatedSqlDir) {
        this.generatedSqlDir = generatedSqlDir;
    }

    public String getGeneratedPropDir() {
        return generatedPropDir;
    }

    public void setGeneratedPropDir(String generatedPropDir) {
        this.generatedPropDir = generatedPropDir;
    }

    public String getYarnQueue() {
        return yarnQueue;
    }

    public void setYarnQueue(String yarnQueue) {
        this.yarnQueue = yarnQueue;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public boolean isSubmitEnabled() {
        return submitEnabled;
    }

    public void setSubmitEnabled(boolean submitEnabled) {
        this.submitEnabled = submitEnabled;
    }

    public long getSubmitTimeoutSeconds() {
        return submitTimeoutSeconds;
    }

    public void setSubmitTimeoutSeconds(long submitTimeoutSeconds) {
        this.submitTimeoutSeconds = submitTimeoutSeconds;
    }
}
