package com.rookie.common

import java.io.File
import org.apache.flink.api.java.utils.ParameterTool

object Common {

  var path: String = Constant.DEFAULT_CONFIG_FILE

  /**
    * 1. add sqlSubmit.properties to parameterTool
    * 2. add job.prop.file content properties to  parameterTool (if file exists)
    * 3. add input parameter to parameterTool (if exists)
    *
    * @param args
    * @return
    */
  def init(args: Array[String]): ParameterTool = {

    // input parameter
    val inputPara = ParameterTool.fromArgs(args)
    if (!inputPara.has(Constant.INPUT_SQL_FILE_PARA)) {
      println("please input sql file. like : --sql sql/demo.sql")
      System.exit(-1)
    }
    // load properties
    if ("\\" == File.separator) { // windows
      path = Common.getClass.getClassLoader.getResource(Constant.DEFAULT_CONFIG_FILE).getPath.substring(1)
    }

    // load default properties
    // load default properties : sqlSubmit.properties
    val defaultPropFile = ParameterTool.fromPropertiesFile(path)

    // load input job properties
    var inputJobPropFile: ParameterTool = null
    if (inputPara.has(Constant.INPUT_JOB_PROP_FILE_PARA)) {
      inputJobPropFile = ParameterTool.fromPropertiesFile(inputPara.get(Constant.INPUT_JOB_PROP_FILE_PARA))
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

    parameterTool
  }

}
