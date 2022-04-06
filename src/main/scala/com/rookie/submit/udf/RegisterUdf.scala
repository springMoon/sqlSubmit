package com.rookie.submit.udf

import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

object RegisterUdf {

  def registerUdf(env: StreamTableEnvironment) = {

    // udf
    env.createTemporarySystemFunction("udf_decode", new Decode)

    // udtf
    env.createTemporarySystemFunction("udf_split", new SplitFunction)
    env.createTemporarySystemFunction("udf_parse_json", new ParseJson)
    env.createTemporarySystemFunction("udf_date_add", new DateAdd)
    env.createTemporarySystemFunction("udf_date_add_new", new DateAddNew)
    // 可以基于配置动态生成UDF
    // join hbase table, first qualify is join key
    env.createTemporarySystemFunction("udf_join_hbase_non_rowkey_no_cache", new JoinHbaseNonRowkeyNoCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10"))
    env.createTemporarySystemFunction("udf_join_hbase_non_rowkey_cache", new JoinHbaseNonRowkeyCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10", 600, 10000))
  }

}
