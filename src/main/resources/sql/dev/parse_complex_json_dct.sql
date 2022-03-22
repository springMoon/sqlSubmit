-- kafka source
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

