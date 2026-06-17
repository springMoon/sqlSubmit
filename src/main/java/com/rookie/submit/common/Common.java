package com.rookie.submit.common;

import org.apache.flink.api.java.utils.ParameterTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Common initialization utilities.
 */
public class Common {

    public static final Logger LOG = LoggerFactory.getLogger("Common");
    public static String path = Constant.DEFAULT_CONFIG_FILE;
    public static String jobName;

    private Common() {
    }

    /**
     * 1. add sqlSubmit.properties to parameterTool
     * 2. add job.prop.file content properties to parameterTool (if file exists)
     * 3. add input parameter to parameterTool (if exists)
     *
     * @param args program input param
     * @return merged parameters
     */
    public static ParameterTool init(String[] args) throws Exception {
        ParameterTool inputPara = ParameterTool.fromArgs(args);
        if (!inputPara.has(Constant.INPUT_SQL_FILE_PARA)) {
            LOG.info("please input sql file. like : --sql sql/demo.sql");
            System.exit(-1);
        }

        if (!new File(path).exists()) {
            LOG.info(Constant.DEFAULT_CONFIG_FILE + " not exists, find in class path");
            path = Common.class.getClassLoader().getResource(Constant.DEFAULT_CONFIG_FILE).getPath();
        }

        ParameterTool defaultPropFile = ParameterTool.fromPropertiesFile(path);

        ParameterTool inputJobPropFile = null;
        if (inputPara.has(Constant.INPUT_JOB_PROP_FILE_PARA)) {
            inputJobPropFile = ParameterTool.fromPropertiesFile(inputPara.get(Constant.INPUT_JOB_PROP_FILE_PARA));
        }

        ParameterTool parameterTool;
        if (inputJobPropFile != null) {
            parameterTool = defaultPropFile.mergeWith(inputJobPropFile).mergeWith(inputPara);
        } else {
            parameterTool = defaultPropFile.mergeWith(inputPara);
        }

        jobName = parameterTool.get(Constant.INPUT_SQL_FILE_PARA);
        if (jobName.contains("/")) {
            jobName = jobName.substring(jobName.lastIndexOf("/") + 1);
        }
        if (jobName.contains(".")) {
            jobName = jobName.substring(0, jobName.indexOf("."));
        }
        if (jobName == null || jobName.isEmpty()) {
            jobName = parameterTool.get(Constant.JOB_NAME);
        }

        return parameterTool;
    }
}
