package com.rookie.subSubmit.main

import java.time.Duration
import java.util.List

import com.rookie.subSubmit.common.{Common, Constant}
import com.rookie.subSubmit.util.{SqlFileUtil, TableConfUtil}
import org.apache.flink.api.common.time.Time
import org.apache.flink.configuration.{Configuration, CoreOptions, PipelineOptions}
import org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.EnvironmentSettings
import org.apache.flink.table.api.scala.StreamTableEnvironment
import org.apache.flink.table.catalog.hive.HiveCatalog

import scala.collection.JavaConversions._


/**
  * sqlSubmit main class
  * input sql file name and execute sql content
  */
object SqlSubmit {

  var path: String = Constant.DEFAULT_CONFIG_FILE

  def main(args: Array[String]): Unit = {

    // parse input parameter and load job properties
    val paraTool = Common.init(args);
    // parse sql file
    val sqlList: List[String] = SqlFileUtil.readFile(paraTool.get(Constant.INPUT_SQL_FILE_PARA))

    // StreamExecutionEnvironment
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    // EnvironmentSettings
    val settings = EnvironmentSettings.newInstance()
      .useBlinkPlanner()
      .inStreamingMode()
      .build()
    // create table enviroment
    val tabEnv = StreamTableEnvironment.create(env, settings)

    // table Config
    TableConfUtil.conf(tabEnv, paraTool)

    // register catalog
    val catalog = new HiveCatalog(Constant.HIVE_CATALOG_NAME, Constant.HIVE_DEFAULT_DATABASE, Constant.HIVE_CONFIG_PATH, Constant.HIVE_VERSION)
    tabEnv.useCatalog(Constant.HIVE_CATALOG_NAME)

    // load udf

    // add sql
    for (sql <- sqlList) {
      try {
        tabEnv.sqlUpdate("sql")
        println("execute success : " + sql)
      } catch {
        case e: Exception => {
          println("execute sql error : " + sql)
          e.printStackTrace()
        }
      }
    }
    // execute flink job
    env.execute(paraTool.get(Constant.JOB_NAME))

  }

}
