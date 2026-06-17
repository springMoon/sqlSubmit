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

        // parse sql file
        List<String> sqlList = SqlFileUtil.readFile(paraTool.get(Constant.INPUT_SQL_FILE_PARA));

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

        // hive catalog
        // register catalog, only in server
        // mysql catalog, useless, cannot persistent table schema to mysql
        MyMySqlCatalog catalog = new MyMySqlCatalog(SqlSubmit.class.getClassLoader(),
                "my-mysql-catalog",
                "flink",
                "root",
                "123456",
                "jdbc:mysql://localhost:3306");
        tabEnv.registerCatalog("my-mysql-catalog", catalog);
        tabEnv.useCatalog("my-mysql-catalog");

        // load udf
        RegisterUdf.registerUdf(tabEnv, paraTool);
        tabEnv.getConfig().setLocalTimeZone(ZoneId.of("Asia/Shanghai"));
        // execute sql
        StatementSet statement = tabEnv.createStatementSet();
        boolean hasInsert = false;
        for (String sql : sqlList) {
            try {
                if (!sql.trim().equals("")) {
                    // execute sql set parameter
                    if (sql.toLowerCase().startsWith("set")) {
                        String[] tmp = sql.substring(4).split("=");
                        String key = tmp[0].trim();
                        String value = tmp[1].trim();
                        logger.info("add parameter to table config: " + key + " = " + value);
                        tabEnv.getConfig().getConfiguration().setString(key, value);
                    } else if (sql.toLowerCase().startsWith("insert")) {
                        statement.addInsertSql(sql);
                        hasInsert = true;
                    } else {
                        logger.info("dialect : " + tabEnv.getConfig().getSqlDialect());
                        tabEnv.executeSql(sql);
                    }
                    logger.info("execute success : " + sql);
                }
            } catch (Exception e) {
                logger.error("execute sql error : " + sql, e);
                e.printStackTrace();
                System.exit(-1);
            }
        }
        // execute sql insert
        if (hasInsert) {
            statement.execute();
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
        // checkpoint
        env.enableCheckpointing(paraTool.getLong(Constant.CHECKPOINT_INTERVAL) * 1000, CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setCheckpointTimeout(paraTool.getLong(Constant.CHECKPOINT_TIMEOUT) * 1000);
        // Flink 1.11.0 new feature: Enables unaligned checkpoints
        env.getCheckpointConfig().enableUnalignedCheckpoints();
        // checkpoint dir
        env.getCheckpointConfig().setCheckpointStorage(paraTool.get(Constant.CHECKPOINT_DIR));
    }
}
