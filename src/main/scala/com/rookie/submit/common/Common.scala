package com.rookie.submit.common

import java.io.File
import org.apache.flink.api.java.utils.ParameterTool
import com.rookie.submit.common.Constant._

object Common {

  var path: String = DEFAULT_CONFIG_FILE
  var jobName: String = _

  /**
    * 1. add sqlSubmit.properties to parameterTool
    * 2. add job.prop.file content properties to  parameterTool (if file exists)
    * 3. add input parameter to parameterTool (if exists)
    *
    * @param args program input param
    * @return
    */
  def init(args: Array[String]): ParameterTool = {

    // input parameter
    val inputPara = ParameterTool.fromArgs(args)
    if (!inputPara.has(INPUT_SQL_FILE_PARA)) {
      println("please input sql file. like : --sql sql/demo.sql")
      System.exit(-1)
    }
    // load properties
    if ("\\" == File.separator) { // windows
      path = Common.getClass.getClassLoader.getResource(DEFAULT_CONFIG_FILE).getPath.substring(1)
    }

    // load default properties
    // load default properties : sqlSubmit.properties
    val defaultPropFile = ParameterTool.fromPropertiesFile(path)

    // load input job properties
    var inputJobPropFile: ParameterTool = null
    if (inputPara.has(INPUT_JOB_PROP_FILE_PARA)) {
      inputJobPropFile = ParameterTool.fromPropertiesFile(inputPara.get(INPUT_JOB_PROP_FILE_PARA))
    }

    var parameterTool: ParameterTool = null

    if (null != inputJobPropFile) {
      // if inputJobPropFile exists
      // first putAll inputJobPropFile to defaultPropFile, then put inputPara to defaultPropFile, return defaultPropFile
      parameterTool = defaultPropFile.mergeWith(inputJobPropFile).mergeWith(inputPara)
    } else {
      // if no exists inputJobPropFile
      // just put inputPara to defaultPropFile, return defaultPropFile
      parameterTool = defaultPropFile.mergeWith(inputPara)
    }
    // parse job nam
    jobName = parameterTool.get(INPUT_SQL_FILE_PARA)
    // split prefix
    if (jobName.contains("/")) {
      jobName = jobName.substring(jobName.lastIndexOf("/") + 1)
    }
    // suffix
    if (jobName.contains(".")) {
      jobName = jobName.substring(0, jobName.indexOf("."))
    }


    parameterTool
  }

}
