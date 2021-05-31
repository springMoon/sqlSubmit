package com.rookie.submit.udf

import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

object RegisterUdf {

  def registerUdf(env: StreamTableEnvironment) = {

    // udf
    env.createTemporarySystemFunction("udf_decode", new Decode)

    // udtf
    env.createTemporarySystemFunction("udf_split", new SplitFunction)
    env.createTemporarySystemFunction("udf_parse_json", new ParseJson)
  }

}
