package com.rookie.main

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.{EnvironmentSettings, TableEnvironment}

/**
  * sqlSubmit main class
  * input sql file name and execute sql content
  */
class SqlSubmit {

  def main(args: Array[String]): Unit = {

    // scala environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val settings = EnvironmentSettings.newInstance()
      .useBlinkPlanner()
      .inStreamingMode()
      .build()
    // table enviroment
    val tabEnv = TableEnvironment.create(settings)



  }

}
