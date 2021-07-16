-- kafka source
CREATE TABLE t_json (
   json string
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'user_behavior'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'format' = 'csv'
  ,'csv.field-delimiter' = U&'\0001'
  ,'csv.ignore-parse-errors' = 'true'
 ,'csv.allow-comments' = 'true'
);

CREATE TABLE t_json_sink (
    user_id string
    ,sub_name_1 string
    ,sub_name_2 string
    ,sub_name_3 string
    ,sub_name_4 string
    ,sub_name_5 string
) WITH (
  'connector' = 'print'
);
-- sink
insert into t_json_sink
select T.arr[1], T1.arr[1], T2.arr[1], T3.arr[1], T4.arr[1], T5.arr[1]
from t_json a
    LEFT JOIN LATERAL TABLE(udf_parse_json(json, 'user_id', 'sub_json')) AS T(arr) ON TRUE
    LEFT JOIN LATERAL TABLE(udf_parse_json(T.arr[2], 'sub_name', 'sub_json')) AS T1(arr) ON TRUE
    LEFT JOIN LATERAL TABLE(udf_parse_json(T1.arr[2], 'sub_name', 'sub_json')) AS T2(arr) ON TRUE
    LEFT JOIN LATERAL TABLE(udf_parse_json(T2.arr[2], 'sub_name', 'sub_json')) AS T3(arr) ON TRUE
    LEFT JOIN LATERAL TABLE(udf_parse_json(T3.arr[2], 'sub_name', 'sub_json')) AS T4(arr) ON TRUE
    LEFT JOIN LATERAL TABLE(udf_parse_json(T4.arr[2], 'sub_name', 'sub_json')) AS T5(arr) ON TRUE
    ;

