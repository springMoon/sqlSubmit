package com.rookie.submit.util

import com.rookie.submit.common.Constant
import org.apache.flink.api.common.time.Time
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.table.api.scala.StreamTableEnvironment

/**
  * flink table config
  */
object TableConfUtil {

  def conf(tableEnv: StreamTableEnvironment, paraTool: ParameterTool): Unit = {

    val tabConf = tableEnv.getConfig
    // state retention：min，max，interval must greater than 5 minute
    tabConf.setIdleStateRetentionTime(Time.minutes(paraTool.getInt(Constant.STATE_RETENTION_MIN_TIME)), Time.minutes(paraTool.getInt(Constant.STATE_RETENTION_MAX_TIME)))

    val conf = tableEnv.getConfig.getConfiguration
    // sql default parallelism
    conf.setString("table.exec.resource.default-parallelism", paraTool.get(Constant.TABLE_EXEC_RESOURCE_DEFAULT_PARALLELISM))
  }

}
