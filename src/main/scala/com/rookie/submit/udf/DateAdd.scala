package com.rookie.submit.udf

import com.rookie.submit.util.DateTimeUtil
import org.apache.flink.table.annotation.{DataTypeHint, FunctionHint}
import org.apache.flink.table.functions.ScalarFunction
import org.codehaus.groovy.runtime.DefaultGroovyMethods.collect

import java.text.ParseException
import java.time.LocalDateTime
import java.util.Date

class DateAdd extends ScalarFunction{

  /**
   * @param tar target day need to add
   * @param num  add day num
   *             return yyyy-MM-dd day string
   */
  @FunctionHint(output = new DataTypeHint("String"))
  def eval(input: String, num: Int): Unit = {

    val tar: String = input.toString
    if (tar == null || tar.length == 0) return
    var day: Date = null
    try day = DateTimeUtil.parse(tar)
    catch {
      case e: ParseException =>
        return
    }
    day = DateTimeUtil.plusDay(day, num)

    val resultStr = DateTimeUtil.format(day, DateTimeUtil.YYYY_MM_DD)
    collect(resultStr)

  }

}
