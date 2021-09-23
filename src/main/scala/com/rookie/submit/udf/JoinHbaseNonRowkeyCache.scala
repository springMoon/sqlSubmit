package com.rookie.submit.udf

import java.util.concurrent.TimeUnit

import com.google.common.cache.{Cache, CacheBuilder}
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
 * udtf join hbase with non rowkey: guava cache, connect hbase only get null
 * hbase 表: 10000 条数据，非主键关联测试，关联键值10000条不重复，缓存时间 10 min
 * 笔记本idea测试 TPS： 350+
 * 笔记本 on yarn 测试 TPS： 900+ (基本可用，服务器环境应该会好很多，不够还可以加并行度)
 *
 */
class JoinHbaseNonRowkeyCache(familyString: String, qualifierString: String) extends TableFunction[Row] {

  val LOG: Logger = LoggerFactory.getLogger(classOf[JoinHbaseNonRowkeyCache])
  var table: Table = _
  var family: Array[Byte] = _
  var qualifier: ListBuffer[Array[Byte]] = _
  var filterColumn: BinaryComparator = _
  var cache: Cache[String, ListBuffer[Array[String]]] = _


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

    cache = CacheBuilder
      .newBuilder
      .expireAfterWrite(600, TimeUnit.SECONDS)
      .maximumSize(10000)
      .build[String, ListBuffer[Array[String]]]

  }

  @FunctionHint(output = new DataTypeHint("ROW<arr ARRAY<STRING>>"))
  def eval(key: String): Unit = {
    // if key is empty
    if (key == null || key.length == 0) {
      return
    }
    // insert
    val rowKind = RowKind.fromByteValue(0.toByte)
    val row = new Row(rowKind, 1)
    // get result from cache
    var list: ListBuffer[Array[String]] = cache.getIfPresent(key)
    if (list != null) {
      list.foreach(arr => {
        row.setField(0, arr)
        collect(row)
      })
      return
    }
    // cache get nothing, query hbase
    list = queryHbase(key)
    if (list.length == 0) {
      // if get nothing
      return
    }
    // get result, add to cache
    cache.put(key, list)
    list.foreach(arr => {
      row.setField(0, arr)
      collect(row)
    })
    //    LOG.info("finish join key : " + key)
  }


  private def queryHbase(key: String): ListBuffer[Array[String]] = {
    val scan: Scan = new Scan();
    qualifier.foreach(item => scan.addColumn(family, item))

    val filter = new SingleColumnValueFilter(family, qualifier.head, CompareOperator.EQUAL, key.getBytes("UTF8"))
    scan.setFilter(filter)

    val resultScanner = table.getScanner(scan)
    val it = resultScanner.iterator()

    val list = new ListBuffer[Array[String]]

    // loop result
    while (it.hasNext) {

      val result = it.next()
      val arr = new Array[String](qualifier.length + 1)
      // add rowkey to result array
      var index = 0
      val rowkey = new String(result.getRow)
      arr(index) = rowkey
      // add special qualify to result array
      qualifier.foreach(item => {
        val value = result.getValue(family, item)
        if (value != null) {
          index += 1
          arr(index) = new String(value, "UTF8")
        }
      })

      // add array to result
      list.+=(arr)
    }
    list
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
