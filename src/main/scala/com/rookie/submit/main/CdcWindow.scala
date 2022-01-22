package com.rookie.submit.main

import com.ververica.cdc.connectors.mysql.source.MySqlSource
import com.ververica.cdc.connectors.mysql.table.StartupOptions
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema
import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

/**
 * flink 1.13
 * table api 测试 cdc
 */
object CdcWindow {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val cdc = MySqlSource
      .builder[String]()
      .hostname("localhost")
      .port(3306)
      .databaseList("venn")
      .tableList("user_log")
      .username("root")
      .password("123456")
      .deserializer(new JsonDebeziumDeserializationSchema())
      .startupOptions(StartupOptions.latest())
      .build()

    env
      .fromSource(cdc, WatermarkStrategy.noWatermarks(), "cdcSource")
      .map(str => str)
      .print()


    env.execute("cdcWindow")


  }

}
