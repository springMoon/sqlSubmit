CREATE TABLE t_tmp (
      data  ARRAY<ROW(prykey string,retailnumber string,transnumber string,businessdaydate string,gj_time string,materialnumber01 string,retailstoreid  string,commemployeeid01 string,disctypecode string,batchid string,tendertypecode string,operatorid string,customernumber string,origtransnumber string,thbs string,chnl string,slt_time  string,bk01 string,bk02 string,bk03 string,bk04 string,bk05 string,salesamount string,normalsalesamt string,retailquantity string,cost_hs string,profit_hs string,custcardnumber string,tenderamount string,reductionamount string,share_sum_profit string)>  comment 'data'
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'test_dd'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

CREATE TABLE t_sink (
  prykey string
  ,retailnumber string
) WITH (
   'connector' = 'print'
);

INSERT INTO t_sink
SELECT data[1].prykey
  ,data[1].retailnumber
FROM t_tmp