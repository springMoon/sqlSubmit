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
  listColumns,
  listDatasources,
  listInstances,
  listJobs,
  listJobVersions,
  listTables,
  previewSql,
  simulateSql,
  submitJob,
  syncMetadata,
  testDatasource
} from './api';
import type {
  ColumnMetadata,
  DatagenColumn,
  Datasource,
  DatasourceType,
  FieldMapping,
  Job,
  JobInstance,
  JobVersion,
  RuntimeConfig,
  SqlPreviewResponse,
  TableMetadata
} from './types';

type PageKey = 'datasources' | 'metadata' | 'simulate' | 'jobs' | 'instances' | 'settings';

const datasourceTypes: DatasourceType[] = ['MYSQL', 'KAFKA'];
const stateBackendOptions = ['hashmap', 'rocksdb'];

const activePage = ref<PageKey>('datasources');
const datasources = ref<Datasource[]>([]);
const tables = ref<TableMetadata[]>([]);
const columns = ref<ColumnMetadata[]>([]);
const jobs = ref<Job[]>([]);
const versions = ref<JobVersion[]>([]);
const instances = ref<JobInstance[]>([]);
const selectedJobId = ref<number | ''>('');

const sourceDatasourceId = ref<number | ''>('');
const sourceTableId = ref<number | ''>('');
const sinkDatasourceId = ref<number | ''>('');
const sinkTableName = ref('');
const jobName = ref('');
const remark = ref('');
const fieldMappings = ref<FieldMapping[]>([]);
const sqlPreview = ref<SqlPreviewResponse | null>(null);
const activeSqlTab = ref<'full' | 'source' | 'sink' | 'insert'>('full');
const datasourceKeyword = ref('');
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
  { key: 'metadata' as PageKey, label: '元数据管理', icon: Table2 },
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
  if (!keyword) return datasources.value;
  return datasources.value.filter((item) => `${item.name} ${item.type}`.toLowerCase().includes(keyword));
});
const filteredJobs = computed(() => {
  const keyword = jobKeyword.value.trim().toLowerCase();
  if (!keyword) return jobs.value;
  return jobs.value.filter((item) => `${item.jobName} ${item.status}`.toLowerCase().includes(keyword));
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
    ensureDefaultSelection();
  });
}

function goPage(key: PageKey) {
  activePage.value = key;
}

function ensureDefaultSelection() {
  if (!sourceDatasourceId.value && sourceDatasources.value.length > 0) {
    sourceDatasourceId.value = sourceDatasources.value[0].id;
  }
  if (!sinkDatasourceId.value && sinkDatasources.value.length > 0) {
    sinkDatasourceId.value = sinkDatasources.value[0].id;
  }
}

async function submitDatasource() {
  await withLoading('createDatasource', async () => {
    const config =
      datasourceForm.type === 'MYSQL'
        ? {
            url: datasourceForm.url,
            username: datasourceForm.username,
            password: datasourceForm.password
          }
        : {
            bootstrapServers: datasourceForm.bootstrapServers,
            topic: datasourceForm.topic,
            format: datasourceForm.format || 'json'
          };
    await createDatasource({
      name: datasourceForm.name,
      type: datasourceForm.type,
      config,
      enabled: true,
      remark: datasourceForm.remark
    });
    datasourceForm.name = '';
    datasourceForm.password = '';
    datasourceForm.topic = '';
    datasourceForm.remark = '';
    await refreshAll();
    setToast('数据源已创建', 'success');
  });
}

async function runConnectionTest(datasource: Datasource) {
  await withLoading(`test-${datasource.id}`, async () => {
    const result = await testDatasource(datasource.id);
    setToast(`${datasource.name}: ${result.message}`, result.success ? 'success' : 'error');
  });
}

async function runMetadataSync() {
  if (!sourceDatasourceId.value) return;
  await withLoading('syncMetadata', async () => {
    const result = await syncMetadata(Number(sourceDatasourceId.value));
    await loadTables();
    setToast(`同步完成：${result.tableCount} 张表，${result.columnCount} 个字段`, 'success');
  });
}

async function onSourceChange() {
  sourceTableId.value = '';
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
    tables.value = await listTables(Number(sourceDatasourceId.value));
  });
}

async function onTableChange() {
  if (!sourceTableId.value) return;
  await withLoading('columns', async () => {
    columns.value = await listColumns(Number(sourceTableId.value));
    fieldMappings.value = columns.value.map((column) => ({
      sourceField: column.columnName,
      sinkField: column.columnName
    }));
    sqlPreview.value = null;
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
  if (selectedSource.value?.type === 'MYSQL' && !sourceTableId.value) {
    throw new Error('请选择 MySQL 源表');
  }
  return {
    sourceDatasourceId: Number(sourceDatasourceId.value),
    sourceTableId: sourceTableId.value ? Number(sourceTableId.value) : undefined,
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
    const job = await createJob({
      ...buildRequest(),
      jobName: jobName.value.trim(),
      remark: remark.value
    });
    await refreshJobs(job.id);
    activePage.value = 'jobs';
    setToast(`任务已保存：${job.jobName}`, 'success');
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
  jobName.value = job.jobName;
  sourceDatasourceId.value = job.sourceDatasourceId;
  sourceTableId.value = job.sourceTableId || '';
  sinkDatasourceId.value = job.sinkDatasourceId;
  sinkTableName.value = job.sinkTableName;
  remark.value = job.remark || '';
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

function editJob(job: Job) {
  pickJob(job);
  activePage.value = 'simulate';
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
          <button class="active">同步中心</button>
          <button>任务运维</button>
          <button>数据准备</button>
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
        <button class="side-item muted">
          <FileCode2 :size="18" />
          <span>API访问</span>
        </button>
        <button class="side-item muted">
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
          <button class="icon-button primary" :disabled="loading.refresh" @click="refreshAll">
            <RefreshCw :size="17" />
            <span>刷新</span>
          </button>
        </div>

        <div class="two-column">
          <article class="panel">
            <div class="panel-title">
              <Database :size="18" />
              <h2>创建数据源</h2>
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
                  <input v-model.trim="datasourceForm.topic" required />
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
              <button class="icon-button primary full" :disabled="loading.createDatasource">
                <Save :size="17" />
                <span>保存数据源</span>
              </button>
            </form>
          </article>

          <article class="panel">
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
                <button class="icon-button" @click="runConnectionTest(item)">
                  <Play :size="16" />
                  <span>测试</span>
                </button>
              </div>
              <div v-if="filteredDatasources.length === 0" class="empty-card">暂无数据源</div>
            </div>
          </article>
        </div>
      </section>

      <section v-else-if="activePage === 'metadata'" class="page-stack">
        <div class="page-toolbar">
          <div class="form-inline">
            <label>
              MySQL 数据源
              <select v-model="sourceDatasourceId" @change="onSourceChange">
                <option disabled value="">请选择</option>
                <option v-for="item in sourceDatasources" :key="item.id" :value="item.id">
                  {{ item.name }} / {{ item.type }}
                </option>
              </select>
            </label>
          </div>
          <button
            class="icon-button primary"
            :disabled="selectedSource?.type !== 'MYSQL' || loading.syncMetadata"
            @click="runMetadataSync"
          >
            <RefreshCw :size="17" />
            <span>同步元数据</span>
          </button>
        </div>

        <div class="two-column wide-left">
          <article class="panel">
            <div class="panel-title">
              <Table2 :size="18" />
              <h2>表列表</h2>
            </div>
            <div class="table-wrap tall">
              <table>
                <thead>
                  <tr>
                    <th>表名</th>
                    <th>类型</th>
                    <th>操作</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="table in tables" :key="table.id" :class="{ selected: sourceTableId === table.id }">
                    <td>{{ table.tableName }}</td>
                    <td>{{ table.tableType }}</td>
                    <td>
                      <button class="text-button" @click="sourceTableId = table.id; onTableChange()">查看字段</button>
                    </td>
                  </tr>
                  <tr v-if="tables.length === 0">
                    <td colspan="3" class="empty">暂无表，请先同步元数据</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </article>

          <article class="panel">
            <div class="panel-title">
              <Table2 :size="18" />
              <h2>字段结构</h2>
            </div>
            <div class="table-wrap tall">
              <table>
                <thead>
                  <tr>
                    <th>字段</th>
                    <th>Flink类型</th>
                    <th>主键</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="column in columns" :key="column.columnName">
                    <td>{{ column.columnName }}</td>
                    <td>{{ column.flinkType }}</td>
                    <td>{{ column.primaryKey ? 'Y' : '' }}</td>
                  </tr>
                  <tr v-if="columns.length === 0">
                    <td colspan="3" class="empty">请选择表查看字段</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </article>
        </div>
      </section>

      <section v-else-if="activePage === 'simulate'" class="page-stack">
        <div class="three-column">
          <article class="panel">
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
                <select v-model="sourceTableId" :disabled="selectedSource?.type !== 'MYSQL'" @change="onTableChange">
                  <option disabled value="">请选择</option>
                  <option v-for="table in tables" :key="table.id" :value="table.id">{{ table.tableName }}</option>
                </select>
              </label>
              <label>
                目标数据源
                <select v-model="sinkDatasourceId">
                  <option disabled value="">请选择</option>
                  <option v-for="item in sinkDatasources" :key="item.id" :value="item.id">
                    {{ item.name }} / {{ item.type }}
                  </option>
                </select>
              </label>
              <label>
                目标表 / Topic
                <input v-model.trim="sinkTableName" :placeholder="selectedSink?.type === 'PRINT' ? 'print' : ''" />
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

          <article class="panel">
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
            <pre>{{ sqlText || ' ' }}</pre>
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
                    <button class="icon-button primary" @click="runSubmit(job)">
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
                  <tr v-for="item in instances" :key="item.id">
                    <td>#{{ item.id }}</td>
                    <td>{{ item.jobId }}</td>
                    <td>v{{ item.jobVersion }}</td>
                    <td><span class="status">{{ item.status }}</span></td>
                    <td class="path-cell">{{ item.sqlPath }}</td>
                  </tr>
                  <tr v-if="instances.length === 0">
                    <td colspan="5" class="empty">暂无提交实例</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </article>
        </div>
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
