//package com.rookie.submit.udf
//
//import com.google.gson.JsonParser
//import org.apache.flink.table.annotation.{DataTypeHint, FunctionHint}
//import org.apache.flink.table.functions.TableFunction
//import org.apache.flink.types.Row
//
//
//@FunctionHint(output = new DataTypeHint("ROW<col1 STRING, col2 STRING>"))
//class ParseJson extends TableFunction[Row] {
//
//
//  def eval(str: String*): Unit = {
//    val length = str.length
//    if (str == null || length == 0) {
//      return
//    }
//    val jsonStr = str(0)
//    val json = new JsonParser().parse(jsonStr).getAsJsonObject
//    val arr = new Array[String](str.length - 1)
//    for (index <- 1 until str.length) {
//      try {
//        arr(0) = json.get(str(index + 1)).getAsString
//      } catch {
//        case e: Exception =>
//          e.printStackTrace()
//      }
//    }
//    collect(Row.of(arr(0), arr(1)))
//  }
//
//}
