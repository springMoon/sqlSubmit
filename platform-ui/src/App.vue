<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue';
import {
  Activity,
  CheckCircle2,
  Database,
  FileCode2,
  GitBranch,
  Menu,
  Play,
  RefreshCw,
  Save,
  Search,
  Send,
  Settings,
  Table2
} from '@lucide/vue';
import {
  createDatasource,
  createJob,
  createJobVersion,
  deleteDatasource,
  listDatasources,
  listInstanceLogs,
  listInstances,
  listJobs,
  listJobVersions,
  listLiveColumns,
  listLiveTables,
  listLiveTopics,
  previewSql,
  simulateSql,
  submitJob,
  testDatasource,
  updateDatasource,
  updateJob
} from './api';
import type {
  ColumnMetadata,
  DatagenColumn,
  Datasource,
  DatasourceType,
  FieldMapping,
  Job,
  JobInstance,
  JobLog,
  JobVersion,
  RuntimeConfig,
  SqlPreviewResponse,
  TableMetadata
} from './types';

type PageKey = 'datasources' | 'simulate' | 'jobs' | 'instances' | 'settings';
type DatasourceTypeFilter = 'ALL' | 'MYSQL' | 'KAFKA' | 'BUILTIN';

const datasourceTypes: DatasourceType[] = ['MYSQL', 'KAFKA'];
const stateBackendOptions = ['hashmap', 'rocksdb'];
const datasourceTypeFilters: Array<{ key: DatasourceTypeFilter; label: string }> = [
  { key: 'ALL', label: '全部' },
  { key: 'MYSQL', label: 'MySQL' },
  { key: 'KAFKA', label: 'Kafka' },
  { key: 'BUILTIN', label: '内置' }
];

const activePage = ref<PageKey>('datasources');
const datasources = ref<Datasource[]>([]);
const tables = ref<TableMetadata[]>([]);
const columns = ref<ColumnMetadata[]>([]);
const sinkOptions = ref<string[]>([]);
const jobs = ref<Job[]>([]);
const versions = ref<JobVersion[]>([]);
const instances = ref<JobInstance[]>([]);
const logs = ref<JobLog[]>([]);
const selectedJobId = ref<number | ''>('');
const selectedInstance = ref<JobInstance | null>(null);
const editingDatasourceId = ref<number | null>(null);
const editingJobId = ref<number | null>(null);
const showDatasourceDialog = ref(false);

const sourceDatasourceId = ref<number | ''>('');
const sourceTableName = ref('');
const sinkDatasourceId = ref<number | ''>('');
const sinkTableName = ref('');
const jobName = ref('');
const remark = ref('');
const fieldMappings = ref<FieldMapping[]>([]);
const sqlPreview = ref<SqlPreviewResponse | null>(null);
const activeSqlTab = ref<'full' | 'source' | 'sink' | 'insert'>('full');
const datasourceKeyword = ref('');
const datasourceTypeFilter = ref<DatasourceTypeFilter>('ALL');
const jobKeyword = ref('');

const loading = reactive<Record<string, boolean>>({});
const toast = reactive({ type: 'info', message: '' });

const datasourceForm = reactive({
  name: '',
  type: 'MYSQL' as DatasourceType,
  url: 'jdbc:mysql://localhost:3306/sqlsubmit_platform?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai',
  username: 'root',
  password: '',
  bootstrapServers: 'localhost:9092',
  topic: '',
  format: 'json',
  remark: ''
});

const datagenColumns = ref<DatagenColumn[]>([
  { name: 'id', flinkType: 'BIGINT' },
  { name: 'name', flinkType: 'STRING' },
  { name: 'create_time', flinkType: 'TIMESTAMP(3)' }
]);

const runtime = reactive<RuntimeConfig>({
  parallelism: 1,
  checkpointInterval: 60,
  checkpointTimeout: 600,
  stateBackend: 'hashmap',
  checkpointDir: 'file:///tmp/flink-checkpoints/sqlsubmit',
  rowsPerSecond: 10
});

const pageItems = [
  { key: 'datasources' as PageKey, label: '数据源管理', icon: Database },
  { key: 'simulate' as PageKey, label: 'SQL模拟生成', icon: FileCode2 },
  { key: 'jobs' as PageKey, label: '任务管理', icon: GitBranch },
  { key: 'instances' as PageKey, label: '提交实例', icon: Activity },
  { key: 'settings' as PageKey, label: '系统设置', icon: Settings }
];

const sourceDatasources = computed(() =>
  datasources.value.filter((item) => item.type === 'MYSQL' || item.type === 'DATAGEN')
);
const sinkDatasources = computed(() =>
  datasources.value.filter((item) => item.type === 'MYSQL' || item.type === 'KAFKA' || item.type === 'PRINT')
);
const selectedSource = computed(() => datasources.value.find((item) => item.id === sourceDatasourceId.value));
const selectedSink = computed(() => datasources.value.find((item) => item.id === sinkDatasourceId.value));
const currentPage = computed(() => pageItems.find((item) => item.key === activePage.value) || pageItems[0]);
const filteredDatasources = computed(() => {
  const keyword = datasourceKeyword.value.trim().toLowerCase();
  return datasources.value.filter((item) => {
    const matchesType =
      datasourceTypeFilter.value === 'ALL'
      || item.type === datasourceTypeFilter.value
      || (datasourceTypeFilter.value === 'BUILTIN' && (item.type === 'DATAGEN' || item.type === 'PRINT'));
    const matchesKeyword =
      !keyword || `${item.name} ${item.type} ${item.remark || ''}`.toLowerCase().includes(keyword);
    return matchesType && matchesKeyword;
  });
});
const filteredJobs = computed(() => {
  const keyword = jobKeyword.value.trim().toLowerCase();
  if (!keyword) return jobs.value;
  return jobs.value.filter((item) => `${item.jobName} ${item.status}`.toLowerCase().includes(keyword));
});
const activeTopNav = computed(() => {
  if (activePage.value === 'instances') return 'ops';
  if (activePage.value === 'datasources') return 'prepare';
  return 'sync';
});
const sinkTargetPlaceholder = computed(() => {
  if (selectedSink.value?.type === 'MYSQL') return '请选择或输入目标表名';
  if (selectedSink.value?.type === 'KAFKA') return '请选择或输入 Topic';
  return 'print';
});
const sqlText = computed(() => {
  if (!sqlPreview.value) return '';
  if (activeSqlTab.value === 'source') return sqlPreview.value.sourceDdl;
  if (activeSqlTab.value === 'sink') return sqlPreview.value.sinkDdl;
  if (activeSqlTab.value === 'insert') return sqlPreview.value.insertSql;
  return sqlPreview.value.sql;
});

function setToast(message: string, type: 'info' | 'success' | 'error' = 'info') {
  toast.message = message;
  toast.type = type;
  window.setTimeout(() => {
    if (toast.message === message) toast.message = '';
  }, 3600);
}

async function withLoading(key: string, action: () => Promise<void>) {
  loading[key] = true;
  try {
    await action();
  } catch (error) {
    setToast(error instanceof Error ? error.message : String(error), 'error');
  } finally {
    loading[key] = false;
  }
}

async function refreshAll() {
  await withLoading('refresh', async () => {
    datasources.value = await listDatasources();
    jobs.value = await listJobs();
    instances.value = selectedJobId.value ? await listInstances(Number(selectedJobId.value)) : await listInstances();
    await ensureDefaultSelection();
  });
}

function goPage(key: PageKey) {
  activePage.value = key;
}

async function ensureDefaultSelection() {
  if (!sourceDatasourceId.value && sourceDatasources.value.length > 0) {
    sourceDatasourceId.value = sourceDatasources.value[0].id;
  }
  if (!sinkDatasourceId.value && sinkDatasources.value.length > 0) {
    sinkDatasourceId.value = sinkDatasources.value[0].id;
  }
  await loadSinkOptions(true);
}

async function submitDatasource() {
  await withLoading('createDatasource', async () => {
    const wasEditing = Boolean(editingDatasourceId.value);
    const config =
      datasourceForm.type === 'MYSQL'
        ? {
            url: datasourceForm.url,
            username: datasourceForm.username,
            password: datasourceForm.password
          }
        : {
            bootstrapServers: datasourceForm.bootstrapServers,
            topic: datasourceForm.topic || undefined,
            format: datasourceForm.format || 'json'
          };
    const payload = {
      name: datasourceForm.name,
      type: datasourceForm.type,
      config,
      enabled: true,
      remark: datasourceForm.remark
    };
    if (editingDatasourceId.value) {
      await updateDatasource(editingDatasourceId.value, payload);
    } else {
      await createDatasource(payload);
    }
    resetDatasourceForm();
    showDatasourceDialog.value = false;
    await refreshAll();
    setToast(wasEditing ? '数据源已更新' : '数据源已创建', 'success');
  });
}

function openCreateDatasource() {
  resetDatasourceForm();
  showDatasourceDialog.value = true;
}

function closeDatasourceDialog() {
  resetDatasourceForm();
  showDatasourceDialog.value = false;
}

function resetDatasourceForm() {
  editingDatasourceId.value = null;
  datasourceForm.name = '';
  datasourceForm.type = 'MYSQL';
  datasourceForm.url =
    'jdbc:mysql://localhost:3306/sqlsubmit_platform?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai';
  datasourceForm.username = 'root';
  datasourceForm.password = '';
  datasourceForm.bootstrapServers = 'localhost:9092';
  datasourceForm.topic = '';
  datasourceForm.format = 'json';
  datasourceForm.remark = '';
}

function editDatasource(datasource: Datasource) {
  if (datasource.type === 'DATAGEN' || datasource.type === 'PRINT') {
    setToast('内置数据源不需要编辑', 'info');
    return;
  }
  editingDatasourceId.value = datasource.id;
  datasourceForm.name = datasource.name;
  datasourceForm.type = datasource.type;
  datasourceForm.remark = datasource.remark || '';
  if (datasource.type === 'MYSQL') {
    datasourceForm.url = String(datasource.config.url || '');
    datasourceForm.username = String(datasource.config.username || '');
    datasourceForm.password = String(datasource.config.password || '');
  } else {
    datasourceForm.bootstrapServers = String(datasource.config.bootstrapServers || '');
    datasourceForm.topic = String(datasource.config.topic || '');
    datasourceForm.format = String(datasource.config.format || 'json');
  }
  activePage.value = 'datasources';
  showDatasourceDialog.value = true;
}

async function removeDatasource(datasource: Datasource) {
  if (datasource.type === 'DATAGEN' || datasource.type === 'PRINT') {
    setToast('内置数据源不能删除', 'info');
    return;
  }
  if (!window.confirm(`确认删除数据源 ${datasource.name}？`)) {
    return;
  }
  await withLoading(`delete-ds-${datasource.id}`, async () => {
    await deleteDatasource(datasource.id);
    if (editingDatasourceId.value === datasource.id) {
      resetDatasourceForm();
    }
    await refreshAll();
    setToast('数据源已删除', 'success');
  });
}

async function runConnectionTest(datasource: Datasource) {
  await withLoading(`test-${datasource.id}`, async () => {
    const result = await testDatasource(datasource.id);
    setToast(`${datasource.name}: ${result.message}`, result.success ? 'success' : 'error');
  });
}

async function onSourceChange() {
  sourceTableName.value = '';
  columns.value = [];
  fieldMappings.value = [];
  sqlPreview.value = null;
  if (selectedSource.value?.type === 'MYSQL') {
    await loadTables();
  } else {
    tables.value = [];
    applyDatagenMappings();
  }
}

async function loadTables() {
  if (!sourceDatasourceId.value || selectedSource.value?.type !== 'MYSQL') return;
  await withLoading('tables', async () => {
    tables.value = await listLiveTables(Number(sourceDatasourceId.value));
  });
}

async function onTableChange() {
  if (!sourceDatasourceId.value || !sourceTableName.value) return;
  await withLoading('columns', async () => {
    columns.value = await listLiveColumns(Number(sourceDatasourceId.value), sourceTableName.value);
    fieldMappings.value = columns.value.map((column) => ({
      sourceField: column.columnName,
      sinkField: column.columnName
    }));
    sqlPreview.value = null;
  });
}

async function onSinkChange() {
  await loadSinkOptions(false);
  sqlPreview.value = null;
}

async function loadSinkOptions(preserveSelection: boolean) {
  const currentTableName = sinkTableName.value;
  sinkOptions.value = [];
  if (!preserveSelection) {
    sinkTableName.value = '';
  }
  if (!sinkDatasourceId.value || !selectedSink.value) {
    return;
  }
  if (selectedSink.value.type === 'PRINT') {
    sinkTableName.value = 'print';
    return;
  }
  await withLoading('sinkOptions', async () => {
    if (selectedSink.value?.type === 'MYSQL') {
      sinkOptions.value = (await listLiveTables(Number(sinkDatasourceId.value))).map((table) => table.tableName);
    } else if (selectedSink.value?.type === 'KAFKA') {
      sinkOptions.value = await listLiveTopics(Number(sinkDatasourceId.value));
    }
    if (preserveSelection && currentTableName) {
      sinkTableName.value = currentTableName;
    }
  });
}

function applyDatagenMappings() {
  fieldMappings.value = datagenColumns.value.map((column) => ({
    sourceField: column.name,
    sinkField: column.name
  }));
}

function addDatagenColumn() {
  datagenColumns.value.push({ name: `col_${datagenColumns.value.length + 1}`, flinkType: 'STRING' });
  if (selectedSource.value?.type === 'DATAGEN') applyDatagenMappings();
}

function removeDatagenColumn(index: number) {
  datagenColumns.value.splice(index, 1);
  if (selectedSource.value?.type === 'DATAGEN') applyDatagenMappings();
}

function removeMapping(index: number) {
  fieldMappings.value.splice(index, 1);
}

function buildRequest() {
  if (!sourceDatasourceId.value || !sinkDatasourceId.value) {
    throw new Error('请选择源和目标数据源');
  }
  if (selectedSource.value?.type === 'MYSQL' && !sourceTableName.value) {
    throw new Error('请选择 MySQL 源表');
  }
  if (selectedSink.value?.type !== 'PRINT' && !sinkTableName.value) {
    throw new Error('请选择或输入目标表 / Topic');
  }
  return {
    sourceDatasourceId: Number(sourceDatasourceId.value),
    sourceTableName: sourceTableName.value || undefined,
    sinkDatasourceId: Number(sinkDatasourceId.value),
    sinkTableName: sinkTableName.value || undefined,
    fieldMapping: fieldMappings.value,
    datagenColumns: selectedSource.value?.type === 'DATAGEN' ? datagenColumns.value : undefined,
    runtimeConfig: { ...runtime }
  };
}

async function runPreview() {
  await withLoading('preview', async () => {
    sqlPreview.value = await previewSql(buildRequest());
    activeSqlTab.value = 'full';
    setToast('SQL 已生成', 'success');
  });
}

async function runSimulation() {
  await withLoading('simulate', async () => {
    sqlPreview.value = await simulateSql(buildRequest());
    activeSqlTab.value = 'full';
    setToast('模拟生成完成：只生成 SQL，不保存、不提交', 'success');
  });
}

async function saveJob() {
  await withLoading('createJob', async () => {
    if (!jobName.value.trim()) throw new Error('请输入任务名称');
    const wasEditing = Boolean(editingJobId.value);
    const payload = {
      ...buildRequest(),
      jobName: jobName.value.trim(),
      remark: remark.value
    };
    const job = editingJobId.value ? await updateJob(editingJobId.value, payload) : await createJob(payload);
    editingJobId.value = job.id;
    await refreshJobs(job.id);
    activePage.value = 'jobs';
    setToast(`${wasEditing ? '任务已更新' : '任务已保存'}：${job.jobName}`, 'success');
  });
}

async function refreshJobs(selectJobId?: number) {
  jobs.value = await listJobs();
  if (selectJobId) {
    selectedJobId.value = selectJobId;
    await loadVersions(selectJobId);
  }
  instances.value = selectedJobId.value ? await listInstances(Number(selectedJobId.value)) : await listInstances();
}

async function loadVersions(jobId: number) {
  selectedJobId.value = jobId;
  versions.value = await listJobVersions(jobId);
  instances.value = await listInstances(jobId);
}

async function saveVersion(job: Job) {
  await withLoading(`version-${job.id}`, async () => {
    await createJobVersion(job.id);
    await refreshJobs(job.id);
    setToast('版本已保存', 'success');
  });
}

async function runSubmit(job: Job) {
  await withLoading(`submit-${job.id}`, async () => {
    const instance = await submitJob(job.id);
    await refreshJobs(job.id);
    activePage.value = 'instances';
    setToast(`提交实例已创建：${instance.status}`, 'success');
  });
}

function pickJob(job: Job) {
  editingJobId.value = job.id;
  jobName.value = job.jobName;
  sourceDatasourceId.value = job.sourceDatasourceId;
  sourceTableName.value = job.sourceTableName || '';
  sinkDatasourceId.value = job.sinkDatasourceId;
  sinkTableName.value = job.sinkTableName;
  remark.value = job.remark || '';
  fieldMappings.value = parseJson<FieldMapping[]>(job.fieldMappingJson, []);
  Object.assign(runtime, parseJson<Partial<RuntimeConfig>>(job.runtimeConfigJson, {}));
  sqlPreview.value = job.generatedSql
    ? {
        sourceTableName: 'source_preview',
        sinkTableName: 'sink_preview',
        sourceDdl: '',
        sinkDdl: '',
        insertSql: '',
        sql: job.generatedSql,
        warnings: []
      }
    : null;
  void loadVersions(job.id);
}

async function editJob(job: Job) {
  pickJob(job);
  if (job.sourceDatasourceId) {
    await onSourceChange();
  }
  if (job.sourceTableName) {
    sourceTableName.value = job.sourceTableName;
    await onTableChange();
  }
  await loadSinkOptions(true);
  fieldMappings.value = parseJson<FieldMapping[]>(job.fieldMappingJson, fieldMappings.value);
  Object.assign(runtime, parseJson<Partial<RuntimeConfig>>(job.runtimeConfigJson, {}));
  activePage.value = 'simulate';
}

async function viewInstance(instance: JobInstance) {
  selectedInstance.value = instance;
  await withLoading(`logs-${instance.id}`, async () => {
    logs.value = await listInstanceLogs(instance.id);
  });
}

function parseJson<T>(value: string | undefined, fallback: T): T {
  if (!value) return fallback;
  try {
    return JSON.parse(value) as T;
  } catch {
    return fallback;
  }
}

onMounted(refreshAll);
</script>

<template>
  <div class="app-frame">
    <header class="topbar">
      <div class="brand-row">
        <button class="ghost-icon" title="菜单">
          <Menu :size="22" />
        </button>
        <div class="brand">sqlSubmit</div>
        <nav class="top-nav">
          <button :class="{ active: activeTopNav === 'sync' }" @click="goPage('simulate')">同步中心</button>
          <button :class="{ active: activeTopNav === 'ops' }" @click="goPage('instances')">任务运维</button>
          <button :class="{ active: activeTopNav === 'prepare' }" @click="goPage('datasources')">数据准备</button>
        </nav>
      </div>
      <div class="top-actions">
        <button class="tenant-button">
          <Activity :size="17" />
          <span>本地MVP</span>
        </button>
        <button class="top-link">管理中心</button>
        <span class="divider"></span>
        <button class="top-link">zh</button>
        <div class="avatar">S</div>
        <span class="user-name">sqlsubmit</span>
      </div>
    </header>

    <aside class="sidebar">
      <div class="side-menu">
        <button
          v-for="item in pageItems"
          :key="item.key"
          class="side-item"
          :class="{ active: activePage === item.key }"
          @click="goPage(item.key)"
        >
          <component :is="item.icon" :size="19" />
          <span>{{ item.label }}</span>
        </button>
      </div>
      <div class="side-bottom">
        <button class="side-item muted" @click="setToast('API 访问页面还未接入，当前可直接调用 /api/v1 接口', 'info')">
          <FileCode2 :size="18" />
          <span>API访问</span>
        </button>
        <button class="side-item muted" :class="{ active: activePage === 'settings' }" @click="goPage('settings')">
          <Settings :size="18" />
          <span>设置</span>
        </button>
      </div>
    </aside>

    <main class="content">
      <div class="breadcrumb">同步中心 / <strong>{{ currentPage.label }}</strong></div>

      <div v-if="toast.message" class="toast" :class="toast.type">
        <CheckCircle2 v-if="toast.type === 'success'" :size="18" />
        <span>{{ toast.message }}</span>
      </div>

      <section v-if="activePage === 'datasources'" class="page-stack">
        <div class="page-toolbar">
          <div class="search-box">
            <Search :size="19" />
            <input v-model.trim="datasourceKeyword" placeholder="输入数据源名称或类型" />
          </div>
          <div class="filter-tabs">
            <button
              v-for="filter in datasourceTypeFilters"
              :key="filter.key"
              :class="{ active: datasourceTypeFilter === filter.key }"
              @click="datasourceTypeFilter = filter.key"
            >
              {{ filter.label }}
            </button>
          </div>
          <div class="toolbar-actions">
            <button class="icon-button primary" @click="openCreateDatasource">
              <Database :size="17" />
              <span>新建数据源</span>
            </button>
            <button class="icon-button" :disabled="loading.refresh" @click="refreshAll">
              <RefreshCw :size="17" />
              <span>刷新</span>
            </button>
          </div>
        </div>

        <div class="datasource-layout">
          <article class="panel datasource-list-panel">
            <div class="panel-title">
              <Database :size="18" />
              <h2>数据源列表</h2>
            </div>
            <div class="card-grid">
              <div v-for="item in filteredDatasources" :key="item.id" class="resource-card">
                <div>
                  <strong>{{ item.name }}</strong>
                  <p>{{ item.remark || '-' }}</p>
                  <small>{{ item.type }} · {{ item.enabled ? '已启用' : '已停用' }}</small>
                </div>
                <div class="card-actions">
                  <button class="icon-button" @click="runConnectionTest(item)">
                    <Play :size="16" />
                    <span>测试</span>
                  </button>
                  <button
                    class="icon-button"
                    :disabled="item.type === 'DATAGEN' || item.type === 'PRINT'"
                    @click="editDatasource(item)"
                  >
                    <span>编辑</span>
                  </button>
                  <button
                    class="icon-button danger"
                    :disabled="item.type === 'DATAGEN' || item.type === 'PRINT'"
                    @click="removeDatasource(item)"
                  >
                    <span>删除</span>
                  </button>
                </div>
              </div>
              <div v-if="filteredDatasources.length === 0" class="empty-card">暂无数据源</div>
            </div>
          </article>
        </div>

        <div v-if="showDatasourceDialog" class="modal-backdrop" @click.self="closeDatasourceDialog">
          <article class="modal-panel datasource-modal">
            <div class="modal-header">
              <div class="panel-title">
                <Database :size="18" />
                <h2>{{ editingDatasourceId ? '编辑数据源' : '创建数据源' }}</h2>
              </div>
              <button class="square-button" title="关闭" type="button" @click="closeDatasourceDialog">×</button>
            </div>

            <form class="form-grid" @submit.prevent="submitDatasource">
              <label>
                名称
                <input v-model.trim="datasourceForm.name" required />
              </label>
              <label>
                类型
                <select v-model="datasourceForm.type">
                  <option v-for="type in datasourceTypes" :key="type" :value="type">{{ type }}</option>
                </select>
              </label>

              <template v-if="datasourceForm.type === 'MYSQL'">
                <label class="full">
                  JDBC URL
                  <input v-model.trim="datasourceForm.url" required />
                </label>
                <label>
                  用户名
                  <input v-model.trim="datasourceForm.username" required />
                </label>
                <label>
                  密码
                  <input v-model="datasourceForm.password" type="password" required />
                </label>
              </template>

              <template v-else>
                <label class="full">
                  Bootstrap Servers
                  <input v-model.trim="datasourceForm.bootstrapServers" required />
                </label>
                <label>
                  Topic
                  <input v-model.trim="datasourceForm.topic" placeholder="可在任务目标表中指定" />
                </label>
                <label>
                  Format
                  <input v-model.trim="datasourceForm.format" required />
                </label>
              </template>

              <label class="full">
                备注
                <input v-model.trim="datasourceForm.remark" />
              </label>
              <div class="modal-actions full">
                <button class="icon-button" type="button" @click="closeDatasourceDialog">
                  <span>取消</span>
                </button>
                <button class="icon-button primary" :disabled="loading.createDatasource">
                  <Save :size="17" />
                  <span>{{ editingDatasourceId ? '更新数据源' : '保存数据源' }}</span>
                </button>
              </div>
            </form>
          </article>
        </div>
      </section>

      <section v-else-if="activePage === 'simulate'" class="page-stack">
        <div class="three-column simulate-workbench">
          <article class="panel config-panel">
            <div class="panel-title">
              <GitBranch :size="18" />
              <h2>任务配置</h2>
            </div>
            <div class="form-grid">
              <label>
                任务名称
                <input v-model.trim="jobName" />
              </label>
              <label>
                源数据源
                <select v-model="sourceDatasourceId" @change="onSourceChange">
                  <option disabled value="">请选择</option>
                  <option v-for="item in sourceDatasources" :key="item.id" :value="item.id">
                    {{ item.name }} / {{ item.type }}
                  </option>
                </select>
              </label>
              <label class="full">
                源表
                <select v-model="sourceTableName" :disabled="selectedSource?.type !== 'MYSQL'" @change="onTableChange">
                  <option disabled value="">请选择</option>
                  <option v-for="table in tables" :key="table.tableName" :value="table.tableName">
                    {{ table.tableName }}
                  </option>
                </select>
              </label>
              <label>
                目标数据源
                <select v-model="sinkDatasourceId" @change="onSinkChange">
                  <option disabled value="">请选择</option>
                  <option v-for="item in sinkDatasources" :key="item.id" :value="item.id">
                    {{ item.name }} / {{ item.type }}
                  </option>
                </select>
              </label>
              <label>
                目标表 / Topic
                <select v-if="sinkOptions.length > 0" v-model="sinkTableName" @change="sqlPreview = null">
                  <option disabled value="">请选择</option>
                  <option v-for="option in sinkOptions" :key="option" :value="option">{{ option }}</option>
                </select>
                <input
                  v-else
                  v-model.trim="sinkTableName"
                  :disabled="selectedSink?.type === 'PRINT'"
                  :placeholder="sinkTargetPlaceholder"
                  @input="sqlPreview = null"
                />
              </label>
              <label>
                并行度
                <input v-model.number="runtime.parallelism" min="1" type="number" />
              </label>
              <label>
                Checkpoint间隔
                <input v-model.number="runtime.checkpointInterval" min="1" type="number" />
              </label>
              <label>
                State Backend
                <select v-model="runtime.stateBackend">
                  <option v-for="option in stateBackendOptions" :key="option" :value="option">{{ option }}</option>
                </select>
              </label>
              <label>
                Checkpoint超时
                <input v-model.number="runtime.checkpointTimeout" min="1" type="number" />
              </label>
              <label class="full">
                Checkpoint目录
                <input v-model.trim="runtime.checkpointDir" />
              </label>
              <label class="full">
                备注
                <input v-model.trim="remark" />
              </label>
            </div>

            <div class="action-row">
              <button class="icon-button" :disabled="loading.simulate" @click="runSimulation">
                <Play :size="17" />
                <span>模拟生成</span>
              </button>
              <button class="icon-button" :disabled="loading.preview" @click="runPreview">
                <FileCode2 :size="17" />
                <span>预览SQL</span>
              </button>
              <button class="icon-button primary" :disabled="loading.createJob" @click="saveJob">
                <Save :size="17" />
                <span>保存任务</span>
              </button>
            </div>
          </article>

          <article class="panel mapping-panel">
            <div class="panel-title">
              <Table2 :size="18" />
              <h2>字段映射</h2>
            </div>

            <div v-if="selectedSource?.type === 'DATAGEN'" class="sub-panel first">
              <div class="sub-header">
                <strong>Datagen字段</strong>
                <button class="square-button" title="添加字段" @click="addDatagenColumn">+</button>
              </div>
              <div v-for="(column, index) in datagenColumns" :key="index" class="mapping-grid datagen-grid">
                <input v-model.trim="column.name" @change="applyDatagenMappings" />
                <input v-model.trim="column.flinkType" @change="applyDatagenMappings" />
                <label class="check-label">
                  <input v-model="column.primaryKey" type="checkbox" />
                  PK
                </label>
                <button class="square-button danger" title="删除字段" @click="removeDatagenColumn(index)">×</button>
              </div>
              <label>
                Rows / Second
                <input v-model.number="runtime.rowsPerSecond" min="1" type="number" />
              </label>
            </div>

            <div class="mapping-list">
              <div v-for="(mapping, index) in fieldMappings" :key="index" class="mapping-grid">
                <input v-model.trim="mapping.sourceField" />
                <span>→</span>
                <input v-model.trim="mapping.sinkField" />
                <button class="square-button danger" title="删除映射" @click="removeMapping(index)">×</button>
              </div>
              <div v-if="fieldMappings.length === 0" class="empty-card">选择源表后生成字段映射</div>
            </div>
          </article>

          <article class="panel sql-panel">
            <div class="panel-title">
              <FileCode2 :size="18" />
              <h2>生成SQL</h2>
            </div>
            <div class="tab-row">
              <button :class="{ active: activeSqlTab === 'full' }" @click="activeSqlTab = 'full'">完整</button>
              <button :class="{ active: activeSqlTab === 'source' }" @click="activeSqlTab = 'source'">Source</button>
              <button :class="{ active: activeSqlTab === 'sink' }" @click="activeSqlTab = 'sink'">Sink</button>
              <button :class="{ active: activeSqlTab === 'insert' }" @click="activeSqlTab = 'insert'">Insert</button>
            </div>
            <pre :class="{ placeholder: !sqlText }">{{ sqlText || '选择源表后点击“模拟生成”或“预览SQL”查看结果' }}</pre>
          </article>
        </div>
      </section>

      <section v-else-if="activePage === 'jobs'" class="page-stack">
        <div class="page-toolbar">
          <div class="search-box">
            <Search :size="19" />
            <input v-model.trim="jobKeyword" placeholder="输入任务名称或状态" />
          </div>
          <button class="icon-button primary" @click="refreshJobs()">
            <RefreshCw :size="17" />
            <span>刷新任务</span>
          </button>
        </div>
        <article class="panel">
          <div class="table-wrap page-table">
            <table>
              <thead>
                <tr>
                  <th>任务</th>
                  <th>状态</th>
                  <th>版本</th>
                  <th>目标表</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="job in filteredJobs" :key="job.id" :class="{ selected: selectedJobId === job.id }">
                  <td>
                    <button class="text-button" @click="editJob(job)">{{ job.jobName }}</button>
                  </td>
                  <td><span class="status">{{ job.status }}</span></td>
                  <td>{{ job.currentVersion }}</td>
                  <td>{{ job.sinkTableName }}</td>
                  <td class="button-cell">
                    <button class="icon-button" @click="saveVersion(job)">
                      <Save :size="15" />
                      <span>保存版本</span>
                    </button>
                    <button
                      class="icon-button primary"
                      :disabled="!job.currentVersion || job.currentVersion <= 0 || loading[`submit-${job.id}`]"
                      title="需要先保存版本后才能提交"
                      @click="runSubmit(job)"
                    >
                      <Send :size="15" />
                      <span>提交</span>
                    </button>
                  </td>
                </tr>
                <tr v-if="filteredJobs.length === 0">
                  <td colspan="5" class="empty">暂无任务</td>
                </tr>
              </tbody>
            </table>
          </div>
        </article>
      </section>

      <section v-else-if="activePage === 'instances'" class="page-stack">
        <div class="two-column">
          <article class="panel">
            <div class="panel-title">
              <GitBranch :size="18" />
              <h2>版本列表</h2>
            </div>
            <div class="card-list">
              <div v-for="version in versions" :key="version.id" class="compact-card">
                <strong>v{{ version.version }}</strong>
                <small>{{ version.createdAt || '-' }}</small>
              </div>
              <div v-if="versions.length === 0" class="empty-card">请选择任务查看版本</div>
            </div>
          </article>

          <article class="panel">
            <div class="panel-title">
              <Activity :size="18" />
              <h2>提交实例</h2>
            </div>
            <div class="table-wrap page-table">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>任务</th>
                    <th>版本</th>
                    <th>状态</th>
                    <th>SQL文件</th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="item in instances"
                    :key="item.id"
                    :class="{ selected: selectedInstance?.id === item.id }"
                  >
                    <td>#{{ item.id }}</td>
                    <td>{{ item.jobId }}</td>
                    <td>v{{ item.jobVersion }}</td>
                    <td><span class="status">{{ item.status }}</span></td>
                    <td class="path-cell">
                      <button class="text-button" @click="viewInstance(item)">查看详情</button>
                    </td>
                  </tr>
                  <tr v-if="instances.length === 0">
                    <td colspan="5" class="empty">暂无提交实例</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </article>
        </div>

        <article class="panel instance-detail-panel">
          <div class="panel-title">
            <FileCode2 :size="18" />
            <h2>实例详情</h2>
          </div>
          <template v-if="selectedInstance">
            <div class="detail-grid">
              <div>
                <strong>提交命令</strong>
                <pre>{{ selectedInstance.submitCommand }}</pre>
              </div>
              <div>
                <strong>SQL 文件</strong>
                <span>{{ selectedInstance.sqlPath }}</span>
              </div>
              <div>
                <strong>Properties 文件</strong>
                <span>{{ selectedInstance.propPath }}</span>
              </div>
            </div>
            <div class="log-list">
              <div v-for="log in logs" :key="log.id" class="log-line" :class="log.level.toLowerCase()">
                <span>{{ log.level }}</span>
                <pre>{{ log.message }}</pre>
              </div>
              <div v-if="logs.length === 0" class="empty-card">暂无日志</div>
            </div>
          </template>
          <div v-else class="empty-card">点击提交实例查看命令和日志</div>
        </article>
      </section>

      <section v-else class="page-stack">
        <article class="panel settings-panel">
          <div class="panel-title">
            <Settings :size="18" />
            <h2>系统设置</h2>
          </div>
          <div class="settings-grid">
            <div class="setting-item">
              <strong>后端地址</strong>
              <span>/api 代理到 http://localhost:18080</span>
            </div>
            <div class="setting-item">
              <strong>提交模式</strong>
              <span>默认 dry-run，只生成文件和命令</span>
            </div>
            <div class="setting-item">
              <strong>当前能力</strong>
              <span>数据源、元数据、SQL模拟、任务版本、提交实例</span>
            </div>
          </div>
        </article>
      </section>
    </main>
  </div>
</template>
