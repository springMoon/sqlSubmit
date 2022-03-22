-- kafka source
-- {"header": {"preModule":"bf062c50436c437e8a4b4dfd06d1b75c","sourceType":"Mysql","recordType":"realtime","catalog":"stepping_stone","readModule":"bf062c50436c437e8a4b4dfd06d1b75c","readTime":1622618503016,"checkPoint":"mysql-bin.000014-789759966","table":"t_feature"}, "operation":"update", "location":{"code":"B416_1", "send_time":1622561402000, "mean":11.490752510000, "rms":5.134761624000, "skewness":6.069043769000, "id":1551281, "peak":8.775708036000, "kurtosis":10.690323250000 },"data":{ "rows":[null,1551281,"B416_1",1622561402000,"4.134761624000","11.490752510000","8.775708036000","10.690323250000","6.069043769000"] }}
--  {"header": {"preModule":"bf062c50436c437e8a4b4dfd06d1b75c","sourceType":"Mysql","recordType":"realtime","catalog":"stepping_stone","readModule":"bf062c50436c437e8a4b4dfd06d1b75c","readTime":1622618523295,"readmoduleid":"bf062c50436c437e8a4b4dfd06d1b75c","checkPoint":"mysql-bin.000014-789922670","table":"t_feature"}, "operation":"delete", "location":{"code":"B416_2","send_time":1622561402000,"mean":11.578577090000,"rms":3.408678729000,"skewness":10.941250040000,"id":1551282,"peak":10.741151940000,"kurtosis":10.788141910000}, "data":{ "rows":[]}}
CREATE TABLE t_json (
    data row(`rows` string)
) WITH (
      'connector' = 'kafka'
      ,'topic' = 'user_log_dct'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 'user_log_dct'
      ,'format' = 'csv'
      ,'csv.field-delimiter' = U&'\0001'
      ,'csv.ignore-parse-errors' = 'true'
      ,'csv.allow-comments' = 'true'
      );

CREATE TABLE t_json_sink (
    id string
    ,code string
    ,send_time string
    ,mean string
    ,rms string
) WITH (
      'connector' = 'print'
      );
-- sink
insert into t_json_sink
select arr[1] id
     ,arr[2] code
     ,arr[3] send_time
     ,arr[4] mean
     ,arr[5] rms
from t_json a
         LEFT JOIN LATERAL TABLE(udf_parse_dct_json(`data`.`rows`)) AS T(arr) ON TRUE
;

