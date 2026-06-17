package com.rookie.submit.util;

import com.rookie.submit.common.Common;
import com.rookie.submit.common.Constant;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * flink table config
 */
public class TableConfUtil {
    private static final Logger logger = LoggerFactory.getLogger("TableConfUtil");

    private TableConfUtil() {
    }

    public static void conf(StreamTableEnvironment tableEnv, ParameterTool paraTool, List<String> sqlList) {
        org.apache.flink.table.api.TableConfig tabConf = tableEnv.getConfig();
        tabConf.setIdleStateRetention(Duration.ofMinutes(paraTool.getInt(Constant.STATE_RETENTION_DURATION)));

        org.apache.flink.configuration.Configuration conf = tableEnv.getConfig().getConfiguration();
        conf.setString("table.exec.resource.default-parallelism", paraTool.get(Constant.TABLE_EXEC_RESOURCE_DEFAULT_PARALLELISM));
        conf.setString("table.exec.hive.infer-source-parallelism", "false");
        conf.setString("pipeline.name", Common.jobName);

        if (paraTool.get(Constant.TABLE_EXEC_MINI_BATCH_ENABLE) != null) {
            conf.setString("table.exec.mini-batch.enabled", paraTool.get(Constant.TABLE_EXEC_MINI_BATCH_ENABLE));
            conf.setString("table.exec.mini-batch.allow-latency", paraTool.get(Constant.TABLE_EXEC_MINI_BATCH_ALLOW_LATENCY));
            conf.setString("table.exec.mini-batch.size", paraTool.get(Constant.TABLE_EXEC_MINI_BATCH_SIZE));
        }
        if (paraTool.get(Constant.TABLE_EXEC_STATE_TTL) != null) {
            conf.setString("table.exec.state.ttl", paraTool.get(Constant.TABLE_EXEC_STATE_TTL));
        }
        if (paraTool.get(Constant.TABLE_EXEC_SOURCE_FORCE_BREAK_CHAIN) != null) {
            conf.setString("table.exec.source.force-break-chain", paraTool.get(Constant.TABLE_EXEC_SOURCE_FORCE_BREAK_CHAIN));
            tabConf.addJobParameter("table.exec.source.force-break-chain", paraTool.get(Constant.TABLE_EXEC_SOURCE_FORCE_BREAK_CHAIN));
        }

        paraTool.getProperties().forEach((key, value) -> {
            if (key.toString().startsWith("cust")) {
                conf.setString(key.toString(), value.toString());
            }
        });

        List<String> indexList = new ArrayList<>();
        for (String sql : sqlList) {
            if (sql.trim().toLowerCase().contains("execution.runtime-mode")) {
                indexList.add(sql);
                try {
                    String[] tmp = sql.substring(4).split("=");
                    String key = tmp[0].trim();
                    String value = tmp[1].trim();
                    logger.info("ignore table config: " + key + " = " + value);
                } catch (Exception e) {
                    System.exit(-1);
                }
            }
        }

        sqlList.removeAll(indexList);
    }
}
