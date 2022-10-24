package com.rookie.submit.udf

import com.rookie.submit.common.Constant
import com.rookie.submit.udaf.math.Median
import com.rookie.submit.udaf.{BloomFilter, RedisUv}
import com.rookie.submit.udtf.UdtfTimer
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

object RegisterUdf {

  def registerUdf(tabEnv: StreamTableEnvironment, paraTool: ParameterTool) = {

    // udf
    tabEnv.createTemporarySystemFunction("udf_decode", new Decode)
    tabEnv.createTemporarySystemFunction("udf_date_add", new DateAdd)

    // udtf
    tabEnv.createTemporarySystemFunction("udf_split", new SplitFunction)
    tabEnv.createTemporarySystemFunction("udf_parse_json", new ParseJson)
    tabEnv.createTemporarySystemFunction("udf_timer", new UdtfTimer(1000))
    // 可以基于配置动态生成UDF
    // join hbase table, first qualify is join key
    tabEnv.createTemporarySystemFunction("udf_join_hbase_non_rowkey_no_cache", new JoinHbaseNonRowkeyNoCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10"))
    tabEnv.createTemporarySystemFunction("udf_join_hbase_non_rowkey_cache", new JoinHbaseNonRowkeyCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10", 600, 10000))

    // udaf
    tabEnv.createTemporarySystemFunction("udaf_uv_count", classOf[BloomFilter]);
    tabEnv.createTemporarySystemFunction("udaf_redis_uv_count", new RedisUv(paraTool.get(Constant.REDIS_URL), "user_log_uv"));
//    env.createTemporarySystemFunction("udaf_redis_uv_count", new JedisRedisUv("localhost", 6379));
    tabEnv.createTemporarySystemFunction("udaf_median", classOf[Median]);

  }

}
