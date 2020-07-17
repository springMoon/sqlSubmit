package com.rookie.submit.main

import com.rookie.submit.common.Common
import com.rookie.submit.common.Constant._
import com.rookie.submit.util.{RegisterUdf, SqlFileUtil, TableConfUtil}
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend
import org.apache.flink.runtime.state.StateBackend
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.{EnvironmentSettings, StatementSet}
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions._

/**
  * sqlSubmit main class
  * input sql file name and execute sql content
  */
object SqlSubmit {

  private val logger = LoggerFactory.getLogger(SqlSubmit.getClass)

  def main(args: Array[String]): Unit = {
    // parse input parameter and load job properties
    val paraTool = Common.init(args)

    // parse sql file
    val sqlList = SqlFileUtil.readFile(paraTool.get(INPUT_SQL_FILE_PARA))

    // StreamExecutionEnvironment
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    // state backend and checkpoint
    enableCheckpoint(env, paraTool)
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
    //    val catalog = new HiveCatalog(Constant.HIVE_CATALOG_NAME, Constant.HIVE_DEFAULT_DATABASE, Constant.HIVE_CONFIG_PATH, Constant.HIVE_VERSION)
    //    tabEnv.useCatalog(Constant.HIVE_CATALOG_NAME)

    // load udf
    RegisterUdf.registerUdf(tabEnv)
    // execute sql
    val statement: StatementSet = tabEnv.createStatementSet()
    var result: StatementSet = null
    for (sql <- sqlList) {
      try {
        if (sql.startsWith("insert")) {
          // ss
          result = statement.addInsertSql(sql)
        } else {
          tabEnv.executeSql(sql)
        }
        logger.info("execute success : " + sql)
      } catch {
        case e: Exception =>
          logger.error("execute sql error : " + sql, e)
          e.printStackTrace()
          System.exit(-1)
      }
    }
    result.execute(Common.jobName)
    // not need, sql will execute when call executeSql
    //        env.execute(Common.jobName)
  }

  def enableCheckpoint(env: StreamExecutionEnvironment, paraTool: ParameterTool): Unit = {
    // state backend
    var stateBackend: StateBackend = null
    if ("rocksdb".equals(paraTool.get(STATE_BACKEND))) {
      stateBackend = new RocksDBStateBackend(paraTool.get(CHECKPOINT_DIR), true)
    } else {
      stateBackend = new FsStateBackend(paraTool.get(CHECKPOINT_DIR), true)
    }
    env.setStateBackend(stateBackend)
    // checkpoint
    env.enableCheckpointing(paraTool.getLong(CHECKPOINT_INTERVAL) * 1000, CheckpointingMode.EXACTLY_ONCE)
    env.getCheckpointConfig.setCheckpointTimeout(paraTool.getLong(CHECKPOINT_TIMEOUT) * 1000)
    // Flink 1.11.0 new feature: Enables unaligned checkpoints
    env.getCheckpointConfig.enableUnalignedCheckpoints()
  }

}
