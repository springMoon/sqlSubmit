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
    category_id string
    ,user_id string
    ,item_id string
    ,sort_col int
    ,username string
    ,password string
    ,doub double
    ,sub_name string
--     ,sub_pass string
) WITH (
  'connector' = 'print'
);
-- sink
insert into t_json_sink
select category_id, user_id, item_id, cast(sort_col as int) sort_col, username, password, cast(doub as double) doub,sub_name --,sub_pass
from t_json a
    LEFT JOIN LATERAL TABLE(udf_parse_json(json, 'category_id', 'user_id', 'item_id', 'sort_col', 'sub_json')) AS T(category_id, user_id, item_id, sort_col, sub_json) ON TRUE
    LEFT JOIN LATERAL TABLE(udf_parse_json(sub_json, 'username', 'password', 'doub', 'sub_json_1')) AS T1(username, password, doub, sub_json_1) ON TRUE
    LEFT JOIN LATERAL TABLE(udf_parse_json(sub_json_1, 'sub_name')) AS T2(sub_name) ON TRUE
    ;

