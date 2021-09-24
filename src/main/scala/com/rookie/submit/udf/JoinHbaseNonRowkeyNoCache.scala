package com.rookie.submit.udf


import org.apache.commons.lang3.StringUtils
import org.apache.flink.table.annotation.{DataTypeHint, FunctionHint}
import org.apache.flink.table.functions.{FunctionContext, TableFunction}
import org.apache.flink.types.{Row, RowKind}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Scan, Table}
import org.apache.hadoop.hbase.filter.{BinaryComparator, SingleColumnValueFilter}
import org.apache.hadoop.hbase.{CompareOperator, HBaseConfiguration, TableName}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.ListBuffer

/**
 * udtf join hbase with non rowkey: no cache, connect hbase every event
 * hbase 表: 10000 条数据，非主键关联测试，关联键值10000条不重复
 * 笔记本idea测试 TPS： 40+
 * 笔记本 on yarn 测试 TPS： 80+
 *
 */
class JoinHbaseNonRowkeyNoCache(familyString: String, qualifierString: String) extends TableFunction[Row] {

  val LOG: Logger = LoggerFactory.getLogger(classOf[JoinHbaseNonRowkeyNoCache])
  var table: Table = _
  var family: Array[Byte] = _
  var qualifier: ListBuffer[Array[Byte]] = _
  var filterColumn: BinaryComparator = _

  override def open(context: FunctionContext): Unit = {

    val conf = HBaseConfiguration.create
    conf.set("hbase.zookeeper.quorum", "thinkpad:12181")
    conf.set("hbase.htable.threads.keepalivetime", "20")
    conf.set("zookeeper.znode.parent", "/hbase")

    val connection: Connection = ConnectionFactory.createConnection(conf)
    table = connection.getTable(TableName.valueOf("user_info"))

    if (StringUtils.isEmpty(familyString)) {
      LOG.error("hbase udtf family is empty")
      System.exit(-1)
    }
    if (StringUtils.isEmpty(qualifierString)) {
      LOG.error("hbase udtf qualifier is empty")
      System.exit(-1)
    }
    family = familyString.getBytes("UTF8")
    val arr = qualifierString.split(",")
    qualifier = new ListBuffer[Array[Byte]]()
    arr.foreach(item => qualifier.+=(item.getBytes("UTF8")))

    filterColumn = new BinaryComparator(qualifier.head)
    LOG.info("hbase udtf join family: " + familyString + ", qualifier: " + qualifierString)

  }

  @FunctionHint(output = new DataTypeHint("ROW<arr ARRAY<STRING>>"))
  def eval(key: String): Unit = {
    if (key == null || key.length == 0) {
      return
    }
    val scan: Scan = new Scan();
    qualifier.foreach(item => scan.addColumn(family, item))

    val filter = new SingleColumnValueFilter(family, qualifier.head, CompareOperator.EQUAL, key.getBytes("UTF8"))
    scan.setFilter(filter)

    val resultScanner = table.getScanner(scan)
    val it = resultScanner.iterator()

    // insert
    val rowKind = RowKind.fromByteValue(0.toByte)
    val row = new Row(rowKind, 1)
    while (it.hasNext) {

      val result = it.next()
      val arr = new Array[String](qualifier.length + 1)
      var index = 0
      val rowkey = new String(result.getRow)
      arr(index) = rowkey
      qualifier.foreach(item => {
        val value = result.getValue(family, item)
        if (value != null) {
          index += 1
          arr(index) = new String(value, "UTF8")
        }

      })
      row.setField(0, arr)
      collect(row)

    }
    //    LOG.info("finish join key : " + key)
  }


  override def close(): Unit = {
    if (table != null) {
      table.close()
    }
  }


  def main(args: Array[String]): Unit = {

    // new object
    val joinHbase = new JoinHbaseNonRowkeyNoCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10")

    // init join Hbase
    joinHbase.open(null)

    // query hbase
    joinHbase.eval("002")


  }
}
