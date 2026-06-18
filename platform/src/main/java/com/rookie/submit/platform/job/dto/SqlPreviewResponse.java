package com.rookie.submit.platform.job.dto;

import java.util.List;

public class SqlPreviewResponse {

    private String sourceTableName;
    private String sinkTableName;
    private String sourceDdl;
    private String sinkDdl;
    private String insertSql;
    private String sql;
    private List<String> warnings;

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getSinkTableName() {
        return sinkTableName;
    }

    public void setSinkTableName(String sinkTableName) {
        this.sinkTableName = sinkTableName;
    }

    public String getSourceDdl() {
        return sourceDdl;
    }

    public void setSourceDdl(String sourceDdl) {
        this.sourceDdl = sourceDdl;
    }

    public String getSinkDdl() {
        return sinkDdl;
    }

    public void setSinkDdl(String sinkDdl) {
        this.sinkDdl = sinkDdl;
    }

    public String getInsertSql() {
        return insertSql;
    }

    public void setInsertSql(String insertSql) {
        this.insertSql = insertSql;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }
}
