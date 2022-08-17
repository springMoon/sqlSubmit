-- datagen to starrocks
drop table if  exists  datagen_key_source;
create table if not exists datagen_key_source (
    `col1` string
    ,`col2` string
    ,`col3` string
    ,`col4` string
) WITH (
      'connector' = 'datagen'
      ,'rows-per-second' = '200'
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
          'load-url'='127.0.0.1:8030',
          'jdbc-url'='jdbc:mysql://127.0.0.1:9030',
          'username'='root',
          'password'='123456',
          'database-name'='test',
          'table-name'='datagen_key'
);

insert into starrocks_sink
select * from datagen_key_source ;
