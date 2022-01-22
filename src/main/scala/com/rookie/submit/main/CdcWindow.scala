package com.rookie.submit.main

import com.ververica.cdc.connectors.mysql.MySqlSource
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 * flink 1.13
 * table api 测试 cdc
 */
object CdcWindow {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val cdc = MySqlSource
      .builder[String]()

  }

}
