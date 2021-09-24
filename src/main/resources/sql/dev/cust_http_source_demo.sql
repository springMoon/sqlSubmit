create table cust_http_source(
    id string
    ,name string
    ,sex string
)WITH(
 'connector' = 'http'
 ,'http.url' = 'http://localhost:8888'
 ,'http.interval' = '1000'
 ,'format' = 'csv'
)
;

create table cust_http_sink(
id string
,name string
,sex string
)WITH(
    'connector' = 'print'
)
;

insert into cust_http_sink
select id,name,sex
from cust_http_source;