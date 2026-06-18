package com.rookie.submit.main;

import com.rookie.submit.common.Common;
import com.rookie.submit.common.Constant;
import com.rookie.submit.udf.RegisterUdf;
import com.rookie.submit.util.SqlFileUtil;
import com.rookie.submit.util.TableConfUtil;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.jdbc.catalog.MyMySqlCatalog;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.runtime.state.hashmap.HashMapStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.StatementSet;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

/**
 * sqlSubmit main class
 * input sql file name and execute sql content
 */
public class SqlSubmit {

    private static final Logger logger = LoggerFactory.getLogger("SqlSubmit");

    private SqlSubmit() {
    }

    public static void main(String[] args) throws Exception {
        // parse input parameter and load job properties
        ParameterTool paraTool = Common.init(args);
        logger.info("start sqlSubmit job, jobName: {}, sql: {}", Common.jobName, paraTool.get(Constant.INPUT_SQL_FILE_PARA));

        // parse sql file
        List<String> sqlList = SqlFileUtil.readFile(paraTool.get(Constant.INPUT_SQL_FILE_PARA));
        logger.info("loaded {} sql statements", sqlList.size());

        // StreamExecutionEnvironment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setAutoWatermarkInterval(100L);

        // state backend and checkpoint
        enableCheckpoint(env, paraTool);
        // EnvironmentSettings
        EnvironmentSettings settings = EnvironmentSettings.newInstance()
                .inStreamingMode()
                .build();
        // create table enviroment
        StreamTableEnvironment tabEnv = StreamTableEnvironment.create(env, settings);
        // table Config
        TableConfUtil.conf(tabEnv, paraTool, sqlList);
        logger.info("table environment initialized, timezone: Asia/Shanghai");

        registerMysqlCatalogIfEnabled(tabEnv, paraTool);

        // load udf
        RegisterUdf.registerUdf(tabEnv, paraTool);
        tabEnv.getConfig().setLocalTimeZone(ZoneId.of("Asia/Shanghai"));
        // execute sql
        StatementSet statement = tabEnv.createStatementSet();
        boolean hasInsert = false;
        for (String sql : sqlList) {
            try {
                String trimmedSql = sql.trim();
                if (!trimmedSql.equals("")) {
                    String lowerSql = trimmedSql.toLowerCase(Locale.ROOT);
                    // execute sql set parameter
                    if (lowerSql.startsWith("set ")) {
                        String[] tmp = trimmedSql.substring(4).split("=", 2);
                        if (tmp.length != 2) {
                            throw new IllegalArgumentException("invalid SET statement: " + sql);
                        }
                        String key = tmp[0].trim();
                        String value = tmp[1].trim();
                        logger.info("add parameter to table config: {} = {}", key, value);
                        tabEnv.getConfig().getConfiguration().setString(key, value);
                    } else if (lowerSql.startsWith("insert")) {
                        statement.addInsertSql(trimmedSql);
                        hasInsert = true;
                        logger.info("add insert sql to statement set");
                    } else {
                        logger.info("execute non-insert sql, dialect: {}", tabEnv.getConfig().getSqlDialect());
                        tabEnv.executeSql(trimmedSql);
                    }
                    logger.info("sql processed successfully: {}", trimmedSql);
                }
            } catch (Exception e) {
                logger.error("execute sql error: {}", sql, e);
                System.exit(-1);
            }
        }
        // execute sql insert
        if (hasInsert) {
            logger.info("execute statement set");
            statement.execute();
        } else {
            logger.warn("no insert sql found, statement set will not be executed");
        }
    }

    public static void enableCheckpoint(StreamExecutionEnvironment env, ParameterTool paraTool) {
        // state backend
        StateBackend stateBackend;
        if ("rocksdb".equals(paraTool.get(Constant.STATE_BACKEND))) {
            stateBackend = new EmbeddedRocksDBStateBackend(true);
        } else {
            stateBackend = new HashMapStateBackend();
        }
        env.setStateBackend(stateBackend);
        logger.info("state backend: {}", stateBackend.getClass().getSimpleName());
        // checkpoint
        long checkpointIntervalMs = paraTool.getLong(Constant.CHECKPOINT_INTERVAL) * 1000;
        long checkpointTimeoutMs = paraTool.getLong(Constant.CHECKPOINT_TIMEOUT) * 1000;
        env.enableCheckpointing(checkpointIntervalMs, CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setCheckpointTimeout(checkpointTimeoutMs);
        // Flink 1.11.0 new feature: Enables unaligned checkpoints
        env.getCheckpointConfig().enableUnalignedCheckpoints();
        // checkpoint dir
        env.getCheckpointConfig().setCheckpointStorage(paraTool.get(Constant.CHECKPOINT_DIR));
        logger.info("checkpoint enabled, interval: {} ms, timeout: {} ms, storage: {}",
                checkpointIntervalMs, checkpointTimeoutMs, paraTool.get(Constant.CHECKPOINT_DIR));
    }

    private static void registerMysqlCatalogIfEnabled(StreamTableEnvironment tabEnv, ParameterTool paraTool) {
        if (!Boolean.parseBoolean(paraTool.get(Constant.MYSQL_CATALOG_ENABLE, "false"))) {
            logger.info("mysql catalog is disabled");
            return;
        }

        String catalogName = required(paraTool, Constant.MYSQL_CATALOG_NAME);
        String defaultDatabase = required(paraTool, Constant.MYSQL_CATALOG_DEFAULT_DATABASE);
        String username = required(paraTool, Constant.MYSQL_CATALOG_USERNAME);
        String password = required(paraTool, Constant.MYSQL_CATALOG_PASSWORD);
        String baseUrl = required(paraTool, Constant.MYSQL_CATALOG_BASE_URL);

        MyMySqlCatalog catalog = new MyMySqlCatalog(
                SqlSubmit.class.getClassLoader(),
                catalogName,
                defaultDatabase,
                username,
                password,
                baseUrl);
        tabEnv.registerCatalog(catalogName, catalog);
        tabEnv.useCatalog(catalogName);
        logger.info("registered and switched to mysql catalog: {}, default database: {}, base url: {}",
                catalogName, defaultDatabase, baseUrl);
    }

    private static String required(ParameterTool paraTool, String key) {
        String value = paraTool.get(key);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("missing required config: " + key);
        }
        return value.trim();
    }
}
