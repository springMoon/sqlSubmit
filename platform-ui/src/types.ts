export type DatasourceType = 'MYSQL' | 'KAFKA' | 'DATAGEN' | 'PRINT';

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

export interface Datasource {
  id: number;
  name: string;
  type: DatasourceType;
  config: Record<string, unknown>;
  enabled: boolean;
  remark?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface TableMetadata {
  id: number;
  datasourceId: number;
  tableName: string;
  tableType: string;
  comment?: string;
  lastSyncTime?: string;
}

export interface ColumnMetadata {
  id?: number;
  tableId?: number;
  columnName: string;
  sourceType?: string;
  flinkType: string;
  nullable?: boolean;
  primaryKey?: boolean;
  ordinalPosition?: number;
  comment?: string;
}

export interface FieldMapping {
  sourceField: string;
  sinkField: string;
}

export interface DatagenColumn {
  name: string;
  flinkType: string;
  primaryKey?: boolean;
}

export interface RuntimeConfig {
  parallelism: number;
  checkpointInterval: number;
  checkpointTimeout: number;
  stateBackend: string;
  checkpointDir: string;
  rowsPerSecond: number;
}

export interface SqlPreviewRequest {
  sourceDatasourceId: number;
  sourceTableId?: number;
  sinkDatasourceId: number;
  sinkTableName?: string;
  fieldMapping?: FieldMapping[];
  datagenColumns?: DatagenColumn[];
  runtimeConfig?: Partial<RuntimeConfig>;
}

export interface SqlPreviewResponse {
  sourceTableName: string;
  sinkTableName: string;
  sourceDdl: string;
  sinkDdl: string;
  insertSql: string;
  sql: string;
  warnings: string[];
}

export interface Job {
  id: number;
  jobName: string;
  sourceDatasourceId: number;
  sourceTableId?: number;
  sinkDatasourceId: number;
  sinkTableName: string;
  status: string;
  currentVersion: number;
  generatedSql?: string;
  remark?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface JobVersion {
  id: number;
  jobId: number;
  version: number;
  generatedSql: string;
  generatedProperties: string;
  createdAt?: string;
}

export interface JobInstance {
  id: number;
  jobId: number;
  jobVersion: number;
  flinkJobId?: string;
  yarnApplicationId?: string;
  submitCommand: string;
  sqlPath: string;
  propPath: string;
  status: string;
  startTime?: string;
  endTime?: string;
  errorMessage?: string;
  createdAt?: string;
  updatedAt?: string;
}
