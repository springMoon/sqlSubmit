package com.rookie.submit.util

import com.rookie.submit.common.{Common, Constant}
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.slf4j.LoggerFactory

import java.time.Duration
import java.util
import scala.collection.JavaConversions._

/**
 * flink table config
 */
object TableConfUtil {
  private val logger = LoggerFactory.getLogger("TableConfUtil")

  def conf(tableEnv: StreamTableEnvironment, paraTool: ParameterTool, sqlList: util.List[String]): Unit = {

    val tabConf = tableEnv.getConfig
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
    // for deduplication state ttl
    if (paraTool.get(Constant.TABLE_EXEC_STATE_TTL) != null) {
      conf.setString("table.exec.state.ttl", paraTool.get(Constant.TABLE_EXEC_STATE_TTL))
    }
    // for disable kafka sql source chain
    if (paraTool.get(Constant.TABLE_EXEC_SOURCE_FORCE_BREAK_CHAIN) != null) {
      conf.setString("table.exec.source.force-break-chain", paraTool.get(Constant.TABLE_EXEC_SOURCE_FORCE_BREAK_CHAIN))
      tabConf.addJobParameter("table.exec.source.force-break-chain", paraTool.get(Constant.TABLE_EXEC_SOURCE_FORCE_BREAK_CHAIN))
    }
    // set custom parameter
    paraTool.getProperties.forEach((key, value) => {
      if (key.toString.startsWith("cust")) {
        conf.setString(key.toString, value.toString)
      }
    })
    //    conf.setString("table.exec.emit.early-fire.enabled", "true")
    //    conf.setString("table.exec.emit.early-fire.delay", 5 * 1000 + "")

    // add parameter to table config
//    val indexList = new util.ArrayList[String]()
//    for (sql <- sqlList) {
//
//      // if sql start with 'set ' parse as table parameter
//      if (sql.trim.toLowerCase().startsWith("set ") ) {
//        indexList.add(sql)
//        try {
//          val tmp = sql.substring(4).split("=")
//          val key = tmp(0).trim
//          val value = tmp(1).trim
//          logger.info("add parameter to table config: " + key + " = " + value)
//          conf.setString(key, value)
//        } catch {
//          case e: Exception =>
//            logger.error("parse parameter error : " + sql)
//            e.printStackTrace()
//            System.exit(-1)
//        }
//      }
//    }
//
//    // remove set statement
//    sqlList.removeAll(indexList)

  }

}
