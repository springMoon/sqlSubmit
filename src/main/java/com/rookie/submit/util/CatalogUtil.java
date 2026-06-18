package com.rookie.submit.util;

import com.rookie.submit.common.Constant;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.hive.HiveCatalog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * hive catalog util
 */
public class CatalogUtil {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogUtil.class);

    private CatalogUtil() {
    }

    public static void initCatalog(StreamTableEnvironment tableEnv) {
        HiveCatalog catalog = new HiveCatalog(
                Constant.HIVE_CATALOG_NAME,
                Constant.HIVE_DEFAULT_DATABASE,
                Constant.DEFAULT_CONFIG_FILE,
                Constant.HIVE_VERSION);
        tableEnv.registerCatalog(Constant.HIVE_CATALOG_NAME, catalog);
        LOG.info("registered hive catalog: {}, default database: {}",
                Constant.HIVE_CATALOG_NAME, Constant.HIVE_DEFAULT_DATABASE);
    }
}
