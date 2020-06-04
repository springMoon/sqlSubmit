package com.rookie.main

import java.io.File

import com.rookie.common.{Common, Constant}
import org.apache.flink.table.api.{EnvironmentSettings}

/**
  * sqlSubmit main class
  * input sql file name and execute sql content
  */
object SqlSubmit {

  var path: String = Constant.DEFAULT_CONFIG_FILE

  def main(args: Array[String]): Unit = {

    // parse input parameter and load job properties
    val parameterTool = Common.init(args);

    val settings = EnvironmentSettings.newInstance()
      .useBlinkPlanner()
      .inStreamingMode()
      .build()
    // table enviroment
    //    val tabEnv = TableEnvironment.create(settings)


  }

}
