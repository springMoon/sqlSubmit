package com.rookie.submit.udf;

import com.rookie.submit.common.Constant;
import com.rookie.submit.udaf.BloomFilter;
import com.rookie.submit.udaf.RedisUv;
import com.rookie.submit.udaf.math.Median;
import com.rookie.submit.udtf.UdtfTimer;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterUdf {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterUdf.class);

    private RegisterUdf() {
    }

    public static void registerUdf(StreamTableEnvironment tabEnv, ParameterTool paraTool) {
        // udf
        tabEnv.createTemporarySystemFunction("udf_decode", new Decode());
        tabEnv.createTemporarySystemFunction("udf_date_add", new DateAdd());

        // udtf
        tabEnv.createTemporarySystemFunction("udf_split", new SplitFunction());
        tabEnv.createTemporarySystemFunction("udf_parse_json", new ParseJson());
        tabEnv.createTemporarySystemFunction("udf_timer", new UdtfTimer(1000));
        // 可以基于配置动态生成UDF
        // join hbase table, first qualify is join key
        tabEnv.createTemporarySystemFunction("udf_join_hbase_non_rowkey_no_cache", new JoinHbaseNonRowkeyNoCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10"));
        tabEnv.createTemporarySystemFunction("udf_join_hbase_non_rowkey_cache", new JoinHbaseNonRowkeyCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10", 600, 10000));

        // udaf
        tabEnv.createTemporarySystemFunction("udaf_uv_count", BloomFilter.class);
        tabEnv.createTemporarySystemFunction("udaf_redis_uv_count", new RedisUv(paraTool.get(Constant.REDIS_URL), "user_log_uv"));
        tabEnv.createTemporarySystemFunction("udaf_median", Median.class);
        LOG.info("registered udf/udtf/udaf functions");
    }
}
