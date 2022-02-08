package com.rookie.submit.main

import com.google.gson.{Gson, JsonObject, JsonParser}
import com.ververica.cdc.connectors.mysql.source.MySqlSource
import com.ververica.cdc.connectors.mysql.table.StartupOptions
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema
import org.apache.flink.api.common.accumulators.LongCounter
import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.state.StateTtlConfig.{StateVisibility, UpdateType}
import org.apache.flink.api.common.state.{StateTtlConfig, ValueState, ValueStateDescriptor}
import org.apache.flink.api.common.typeinfo.{TypeHint, TypeInformation}
import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.triggers.{ContinuousProcessingTimeTrigger, Trigger}
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
import org.slf4j.LoggerFactory

/**
 * flink 1.13
 * table api 测试 cdc count
 * when delete, minus it
 *
 *
 * JsonDebeziumDeserializationSchema: parse BigDecimal as byte fail
 *
 */
object CdcWindow {

  val LOG = LoggerFactory.getLogger("CdcWindow")

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.getConfig.setAutoWatermarkInterval(0)

    val cdc = MySqlSource
      .builder[String]()
      .hostname("localhost")
      .port(3306)
      .databaseList("venn")
      .tableList("venn.user_log")
      .username("root")
      .password("123456")
      // parameter true, include schema
      .deserializer(new JsonDebeziumDeserializationSchema())
      .startupOptions(StartupOptions.latest())
      .build()

    env
      .fromSource(cdc, WatermarkStrategy.noWatermarks(), "cdcSource")
      .map(str => str)
      // shuffle all date to same key
      .keyBy(new KeySelector[String, String] {
        override def getKey(value: String): String = {
          "1"
        }
      })
      // 1 minute 10 second
      .window(TumblingProcessingTimeWindows.of(Time.minutes(10), Time.seconds(10)))
      // trigger every 10 second
      .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(10)))
      .process(new ProcessWindowFunction[String, String, String, TimeWindow] {

        // json parser object
        var jsonParser: JsonParser = _
        // counter
        var countState: ValueState[LongCounter] = _
        var amountState: ValueState[BigDecimal] = _

        // long time state
        var allCountState: ValueState[LongCounter] = _
        var allAmountState: ValueState[BigDecimal] = _
        // tmp var for save amount
        var beforeAmount: BigDecimal = 0
        var afterAmount: BigDecimal = 0

        override def open(parameters: Configuration): Unit = {

          LOG.info("window process open")
          jsonParser = new JsonParser()

          // tmp state for a window
          // counter
          val countStateDescriptor = new ValueStateDescriptor[LongCounter]("count", TypeInformation.of(new TypeHint[LongCounter]() {}))
          countState = getRuntimeContext.getState(countStateDescriptor)

          // amount
          val amountStateDescriptor = new ValueStateDescriptor[BigDecimal]("amount", TypeInformation.of(new TypeHint[BigDecimal]() {}))
          amountState = getRuntimeContext.getState(amountStateDescriptor)

          // ttl for window gap, if long time no data trigger window, clear all state
          val ttl = StateTtlConfig.newBuilder(org.apache.flink.api.common.time.Time.minutes(10))
            .setStateVisibility(StateVisibility.NeverReturnExpired)
            .setUpdateType(UpdateType.OnCreateAndWrite)
            .build()

          // all state for job life cycle
          // counter
          val allCountStateDescriptor = new ValueStateDescriptor[LongCounter]("allCount", TypeInformation.of(new TypeHint[LongCounter]() {}))
          allCountStateDescriptor.enableTimeToLive(ttl)
          allCountState = getRuntimeContext.getState(countStateDescriptor)

          // amount
          val allAmountStateDescriptor = new ValueStateDescriptor[BigDecimal]("allAmount", TypeInformation.of(new TypeHint[BigDecimal]() {}))
          allAmountStateDescriptor.enableTimeToLive(ttl)
          allAmountState = getRuntimeContext.getState(allAmountStateDescriptor)
        }

        // parse before and after amount
        // {"before":{"id":1,"user_id":421001,"item_id":5242,"category_id":52,"behavior":"view1","ts":"2022-01-21T01:59:03Z","create_time":"2022-01-21T01:59:12Z"},"after":{"id":1,"user_id":421001,"item_id":5242,"category_id":52,"behavior":"view2","ts":"2022-01-21T01:59:03Z","create_time":"2022-01-21T01:59:12Z"},"source":{"version":"1.5.4.Final","connector":"mysql","name":"mysql_binlog_source","ts_ms":1642986987000,"snapshot":"false","db":"venn","sequence":null,"table":"user_log","server_id":999999,"gtid":null,"file":"mysql-bin.000021","pos":710,"row":0,"thread":null,"query":null},"op":"u","ts_ms":1642986987497,"transaction":null}
        def parseAmount(jsonElement: JsonObject, stag: String): BigDecimal = {
          // no before
          if (!jsonElement.has(stag) || "null".equals(jsonElement.get(stag).toString)) {
            return 0
          }
          val obj = jsonElement.get(stag).getAsJsonObject
          val amountObj = obj.get("amount")
          try {
            amountObj.getAsBigDecimal
          } catch {
            case _ => 0
          }
        }

        override def process(key: String, context: Context, elements: Iterable[String], out: Collector[String]): Unit = {

          // init state variable
          var counter = countState.value()
          var amount = amountState.value()
          if (counter == null) {
            if (allCountState.value() == null) {
              counter = new LongCounter()
              countState.update(counter)
            } else {
              counter = allCountState.value()
              countState.update(counter)
            }
          } else {
            counter = countState.value()
          }
          if (amount == null) {
            if (allAmountState.value() == null) {
              amount = 0
              amountState.update(0)
            } else {
              amount = allAmountState.value()
              amountState.update(amount)
            }
          } else {
            amount = amountState.value()
          }

          val it = elements.iterator
          while (it.hasNext) {
            // current value
            val json = it.next()
            // {"before":{"id":1,"user_id":421001,"item_id":5242,"category_id":52,"behavior":"view1","ts":"2022-01-21T01:59:03Z","create_time":"2022-01-21T01:59:12Z"},"after":{"id":1,"user_id":421001,"item_id":5242,"category_id":52,"behavior":"view2","ts":"2022-01-21T01:59:03Z","create_time":"2022-01-21T01:59:12Z"},"source":{"version":"1.5.4.Final","connector":"mysql","name":"mysql_binlog_source","ts_ms":1642986987000,"snapshot":"false","db":"venn","sequence":null,"table":"user_log","server_id":999999,"gtid":null,"file":"mysql-bin.000021","pos":710,"row":0,"thread":null,"query":null},"op":"u","ts_ms":1642986987497,"transaction":null}
            //            println("input json : " ,json)
            val jsonElement = jsonParser.parse(json).getAsJsonObject
            // data status, insert: c, update: u, delete: d ?
            val status: String = jsonElement.get("op").getAsString

            // parse before
            beforeAmount = parseAmount(jsonElement, "before")
            afterAmount = parseAmount(jsonElement, "after")

            // process counter & amount
            status match {
              case "c" =>
                // insert
                LOG.debug("insert")
                // counter
                counter.add(1)
                // amount
                amount = amount + afterAmount
                amountState.update(amount)
              case "u" =>
                LOG.debug("update")
                amount = amount - beforeAmount + afterAmount
              case "d" =>
                LOG.debug("delete")
                counter.add(-1)
                amount = amount - beforeAmount
              case _ =>
                LOG.warn("process some data, operator confuse : ", status)
            }
          }

          // temp print
          LOG.debug("key : " + key + ", counter : " + counter + ", amount : " + amount)
          // update state
          countState.update(counter)
          amountState.update(amount)

          val window = context.window

          out.collect(key + "," + window.getStart + "," + window.getEnd + "," + counter.getLocalValue + "," + amount)
        }

        override def clear(context: Context): Unit = {
          LOG.info("acc window counter and amount state")
          if (allCountState.value() == null) {
            // first init
            allCountState.update(countState.value())
          } else {
            // next window
            val allCounter = allCountState.value()
            allCounter.add(countState.value().getLocalValue)
            allCountState.update(allCounter)
          }

          if (allAmountState.value() == null) {
            // first init
            allAmountState.update(amountState.value())
          } else {
            // next window
            allAmountState.update(allAmountState.value() + amountState.value())
          }
          LOG.info("window clear, clear window state")
          countState.clear()
          amountState.clear()
          LOG.info("countState : " + countState.value() + ", amountState : " + amountState.value())
        }
      })
      .name("process")
      .uid("process")
      .print()


    env.execute("cdcWindow")


  }

}
