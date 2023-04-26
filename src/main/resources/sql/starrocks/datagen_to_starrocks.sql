-- datagen to starrocks
drop table if  exists  datagen_key_source;
create table if not exists datagen_key_source1 (
    `col1` string
    ,`col2` string
    ,`col3` string
    ,`col4` string
) WITH (
      'connector' = 'datagen'
      ,'rows-per-second' = '20000'
      ,'number-of-rows' = '100000000'
      ,'fields.col1.kind' = 'random'
      ,'fields.col2.kind' = 'random'
      ,'fields.col3.kind' = 'random'
      ,'fields.col4.kind' = 'random'
      ,'fields.col1.length' = '20'
      ,'fields.col2.length' = '10'
      ,'fields.col3.length' = '10'
      ,'fields.col4.length' = '10'
);


drop table if  exists  starrocks_sink;
create table if not exists starrocks_sink (
    `col1` string
    ,`col2` string
    ,`col3` string
    ,`col4` string
    ,PRIMARY key(col1) NOT ENFORCED
) WITH (
          'connector'='starrocks',
          'load-url'='10.201.0.228:28030;10.201.0.229:28030;10.201.0.230:28030',
          'jdbc-url'='jdbc:mysql://10.201.0.228:29030,10.201.0.229:29030,10.201.0.230:29030',
          'username'='root',
          'password'='123456',
          'database-name'='shell',
          'table-name'='datagen_key'
);

insert into starrocks_sink
select * from datagen_key_source ;