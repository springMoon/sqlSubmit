import type {
  ApiResponse,
  ColumnMetadata,
  Datasource,
  DatasourceType,
  Job,
  JobInstance,
  JobLog,
  JobVersion,
  SqlPreviewRequest,
  SqlPreviewResponse,
  TableMetadata
} from './types';

const apiBase = import.meta.env.VITE_API_BASE_URL || '';

async function request<T>(path: string, options: RequestInit = {}): Promise<T> {
  let response: Response;
  try {
    response = await fetch(`${apiBase}${path}`, {
      headers: {
        'Content-Type': 'application/json',
        ...(options.headers || {})
      },
      ...options
    });
  } catch (error) {
    throw new Error(`请求后端失败，请确认 platform 后端已启动：${error instanceof Error ? error.message : String(error)}`);
  }

  const text = await response.text();
  if (response.status === 204 && !text.trim()) {
    return undefined as T;
  }
  if (!text.trim()) {
    throw new Error(`后端返回空响应：HTTP ${response.status} ${response.statusText || ''}`.trim());
  }

  let payload: ApiResponse<T>;
  try {
    payload = JSON.parse(text) as ApiResponse<T>;
  } catch (error) {
    throw new Error(`后端返回非 JSON 响应：HTTP ${response.status} ${text.slice(0, 200)}`);
  }
  if (!response.ok || !payload.success) {
    throw new Error(payload.message || `HTTP ${response.status}`);
  }
  return payload.data;
}

export function listDatasources(type?: DatasourceType) {
  return request<Datasource[]>(`/api/v1/datasources${type ? `?type=${type}` : ''}`);
}

export function createDatasource(body: {
  name: string;
  type: DatasourceType;
  config: Record<string, unknown>;
  enabled?: boolean;
  remark?: string;
}) {
  return request<Datasource>('/api/v1/datasources', {
    method: 'POST',
    body: JSON.stringify(body)
  });
}

export function updateDatasource(
  id: number,
  body: {
    name: string;
    type: DatasourceType;
    config: Record<string, unknown>;
    enabled?: boolean;
    remark?: string;
  }
) {
  return request<Datasource>(`/api/v1/datasources/${id}`, {
    method: 'PUT',
    body: JSON.stringify(body)
  });
}

export function deleteDatasource(id: number) {
  return request<void>(`/api/v1/datasources/${id}`, {
    method: 'DELETE'
  });
}

export function testDatasource(id: number) {
  return request<{ success: boolean; message: string }>(`/api/v1/datasources/${id}/test`, {
    method: 'POST'
  });
}

export function syncMetadata(id: number) {
  return request<{ datasourceId: number; tableCount: number; columnCount: number }>(
    `/api/v1/datasources/${id}/metadata/sync`,
    { method: 'POST' }
  );
}

export function listTables(datasourceId: number) {
  return request<TableMetadata[]>(`/api/v1/datasources/${datasourceId}/tables`);
}

export function listLiveTables(datasourceId: number) {
  return request<TableMetadata[]>(`/api/v1/datasources/${datasourceId}/live/tables`);
}

export function listLiveTopics(datasourceId: number) {
  return request<string[]>(`/api/v1/datasources/${datasourceId}/live/topics`);
}

export function listColumns(tableId: number) {
  return request<ColumnMetadata[]>(`/api/v1/tables/${tableId}/columns`);
}

export function listLiveColumns(datasourceId: number, tableName: string) {
  return request<ColumnMetadata[]>(
    `/api/v1/datasources/${datasourceId}/live/columns?tableName=${encodeURIComponent(tableName)}`
  );
}

export function previewSql(body: SqlPreviewRequest) {
  return request<SqlPreviewResponse>('/api/v1/jobs/sql/preview', {
    method: 'POST',
    body: JSON.stringify(body)
  });
}

export function simulateSql(body: SqlPreviewRequest) {
  return request<SqlPreviewResponse>('/api/v1/jobs/sql/simulate', {
    method: 'POST',
    body: JSON.stringify(body)
  });
}

export function createJob(body: SqlPreviewRequest & { jobName: string; remark?: string }) {
  return request<Job>('/api/v1/jobs', {
    method: 'POST',
    body: JSON.stringify(body)
  });
}

export function updateJob(id: number, body: SqlPreviewRequest & { jobName: string; remark?: string }) {
  return request<Job>(`/api/v1/jobs/${id}`, {
    method: 'PUT',
    body: JSON.stringify(body)
  });
}

export function listJobs(status?: string) {
  return request<Job[]>(`/api/v1/jobs${status ? `?status=${status}` : ''}`);
}

export function createJobVersion(jobId: number) {
  return request<JobVersion>(`/api/v1/jobs/${jobId}/versions`, { method: 'POST' });
}

export function listJobVersions(jobId: number) {
  return request<JobVersion[]>(`/api/v1/jobs/${jobId}/versions`);
}

export function submitJob(jobId: number, version?: number) {
  return request<JobInstance>(`/api/v1/jobs/${jobId}/submit`, {
    method: 'POST',
    body: JSON.stringify(version ? { version } : {})
  });
}

export function listInstances(jobId?: number) {
  return request<JobInstance[]>(`/api/v1/job-instances${jobId ? `?jobId=${jobId}` : ''}`);
}

export function listInstanceLogs(instanceId: number) {
  return request<JobLog[]>(`/api/v1/job-instances/${instanceId}/logs`);
}
