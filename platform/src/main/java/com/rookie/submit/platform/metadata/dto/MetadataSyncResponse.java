package com.rookie.submit.platform.metadata.dto;

public class MetadataSyncResponse {

    private Long datasourceId;
    private int tableCount;
    private int columnCount;

    public MetadataSyncResponse() {
    }

    public MetadataSyncResponse(Long datasourceId, int tableCount, int columnCount) {
        this.datasourceId = datasourceId;
        this.tableCount = tableCount;
        this.columnCount = columnCount;
    }

    public Long getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
}
