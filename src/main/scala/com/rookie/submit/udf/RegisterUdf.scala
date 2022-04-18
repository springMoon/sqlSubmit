package com.rookie.submit.udf

import com.rookie.submit.common.Constant
import com.rookie.submit.udaf.{BloomFilter, JedisRedisUv, RedisUv}
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

object RegisterUdf {

  def registerUdf(env: StreamTableEnvironment, paraTool: ParameterTool) = {

    // udf
    env.createTemporarySystemFunction("udf_decode", new Decode)

    // udtf
    env.createTemporarySystemFunction("udf_split", new SplitFunction)
    env.createTemporarySystemFunction("udf_parse_json", new ParseJson)
    env.createTemporarySystemFunction("udf_date_add", new DateAdd)
    env.createTemporarySystemFunction("udf_date_add_new", new DateAdd)
    // 可以基于配置动态生成UDF
    // join hbase table, first qualify is join key
    env.createTemporarySystemFunction("udf_join_hbase_non_rowkey_no_cache", new JoinHbaseNonRowkeyNoCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10"))
    env.createTemporarySystemFunction("udf_join_hbase_non_rowkey_cache", new JoinHbaseNonRowkeyCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10", 600, 10000))

    // udaf
    env.createTemporarySystemFunction("udaf_uv_count", classOf[BloomFilter]);
    env.createTemporarySystemFunction("udaf_redis_uv_count", new RedisUv(paraTool.get(Constant.REDIS_URL), "user_log_uv"));
//    env.createTemporarySystemFunction("udaf_redis_uv_count", new JedisRedisUv("localhost", 6379));

  }

}
