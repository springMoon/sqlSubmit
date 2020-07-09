package com.rookie.submit.util

import com.rookie.submit.udf.Decode
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

object RegisterUdf {

  def registerUdf(env: StreamTableEnvironment) = {

    // udf
    env.createTemporarySystemFunction("decode", new Decode)

    // udtf
  }

}
