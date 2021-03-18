package com.rookie.submit.util

import java.time.Duration

import com.rookie.submit.common.{Common, Constant}
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

/**
 * flink table config
 */
object TableConfUtil {

  def conf(tableEnv: StreamTableEnvironment, paraTool: ParameterTool): Unit = {

    val tabConf = tableEnv.getConfig
    // state retention：min，max，interval must greater than 5 minute
    // Deprecated
    //    tabConf.setIdleStateRetentionTime(Time.minutes(paraTool.getInt(Constant.STATE_RETENTION_MIN_TIME)), Time.minutes(paraTool.getInt(Constant.STATE_RETENTION_MAX_TIME)))
    tabConf.setIdleStateRetention(Duration.ofMinutes(paraTool.getInt(Constant.STATE_RETENTION_DURATION)))

    val conf = tableEnv.getConfig.getConfiguration
    // sql default parallelism
    conf.setString("table.exec.resource.default-parallelism", paraTool.get(Constant.TABLE_EXEC_RESOURCE_DEFAULT_PARALLELISM))
    // close hive source parallelism auto set: hive file is 21, hive source parallelism
    conf.setString("table.exec.hive.infer-source-parallelism", "false")
    conf.setString("pipeline.name", Common.jobName)
    if (paraTool.get(Constant.TABLE_EXEC_MINI_BATCH_ENABLE) != null) {
      conf.setString("table.exec.mini-batch.enabled", paraTool.get(Constant.TABLE_EXEC_MINI_BATCH_ENABLE))
      conf.setString("table.exec.mini-batch.allow-latency", paraTool.get(Constant.TABLE_EXEC_MINI_BATCH_ALLOW_LATENCY))
      conf.setString("table.exec.mini-batch.size", paraTool.get(Constant.TABLE_EXEC_MINI_BATCH_SIZE))
    }
    // for topn/deduplication state ttl
    if (paraTool.get(Constant.TABLE_EXEC_STATE_TTL) != null) {
      conf.setString("table.exec.state.ttl", paraTool.get(Constant.TABLE_EXEC_STATE_TTL))
    }
  }

}
