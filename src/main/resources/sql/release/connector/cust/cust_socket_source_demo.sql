create table cust_socket_source(
    id string
    ,name string
    ,sex string
)WITH(
 'connector' = 'socket'
 ,'hostname' = 'localhost'
 ,'port' = '8888'
--  ,'byte-delimiter' = '10'
 ,'format' = 'changelog-csv'
 ,'changelog-csv.column-delimiter' = ','
)
;

create table cust_socket_sink(
    id string
    ,name string
    ,sex string
)WITH(
    'connector' = 'print'
)
;

insert into cust_socket_sink
select id,name,sex
from cust_socket_source;