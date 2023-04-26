drop table if exists source_table ;
CREATE  TABLE source_table (
`col_0` bigint
,`col_1` bigint
,`col_2` varchar(65533)
,`col_3` timestamp(3)
,`col_4` bigint
,`col_5` int
,`col_6` int
,`col_7` decimal
,`col_8` int
,`col_9` boolean
,`col_10` bigint
,`col_11` decimal
,`col_12` decimal
,`col_13` decimal
,`col_14` int
,`col_15` decimal
,`col_16` decimal
,`col_17` int
,`col_18` timestamp(3)
,`col_19` varchar(65533)
,`col_20` decimal
,`col_21` int
,`col_22` timestamp(3)
,`col_23` timestamp(3)
,`col_24` bigint
,`col_25` decimal
,`col_26` boolean
,`col_27` bigint
,`col_28` bigint
,`col_29` decimal
,`col_30` decimal
,`col_31` boolean
,`col_32` varchar(65533)
,`col_33` bigint
,`col_34` bigint
,`col_35` varchar(65533)
,`col_36` int
,`col_37` int
,`col_38` int
,`col_39` varchar(65533)
,`col_40` decimal
,`col_41` timestamp(3)
,`col_42` timestamp(3)
,`col_43` varchar(65533)
,`col_44` decimal
,`col_45` int
,`col_46` varchar(65533)
,`col_47` varchar(65533)
,`col_48` varchar(65533)
,`col_49` timestamp(3)
,`col_50` boolean
) WITH (
   'connector' = 'jdbc'
   ,'url' = 'jdbc:mysql://10.201.0.230:29030/test'
   ,'table-name' = 't_for_test_r'
   ,'username' = 'root'
   ,'password' = '123456'
   ,'scan.partition.column' = 'col_0'
   ,'scan.partition.num' = '5'
   ,'scan.partition.lower-bound' = '0'
   ,'scan.partition.upper-bound' = '9999999'
);

drop table if  exists  starrocks_sink;
create table if not exists starrocks_sink (
    `col_0` bigint
    ,`col_1` bigint
    ,`col_2` varchar(65533)
    ,`col_3` timestamp(3)
    ,`col_4` bigint
    ,`col_5` int
    ,`col_6` int
    ,`col_7` decimal
    ,`col_8` int
    ,`col_9` boolean
    ,`col_10` bigint
    ,`col_11` decimal
    ,`col_12` decimal
    ,`col_13` decimal
    ,`col_14` int
    ,`col_15` decimal
    ,`col_16` decimal
    ,`col_17` int
    ,`col_18` timestamp(3)
    ,`col_19` varchar(65533)
    ,`col_20` decimal
    ,`col_21` int
    ,`col_22` timestamp(3)
    ,`col_23` timestamp(3)
    ,`col_24` bigint
    ,`col_25` decimal
    ,`col_26` boolean
    ,`col_27` bigint
    ,`col_28` bigint
    ,`col_29` decimal
    ,`col_30` decimal
    ,`col_31` boolean
    ,`col_32` varchar(65533)
    ,`col_33` bigint
    ,`col_34` bigint
    ,`col_35` varchar(65533)
    ,`col_36` int
    ,`col_37` int
    ,`col_38` int
    ,`col_39` varchar(65533)
    ,`col_40` decimal
    ,`col_41` timestamp(3)
    ,`col_42` timestamp(3)
    ,`col_43` varchar(65533)
    ,`col_44` decimal
    ,`col_45` int
    ,`col_46` varchar(65533)
    ,`col_47` varchar(65533)
    ,`col_48` varchar(65533)
    ,`col_49` timestamp(3)
    ,`col_50` boolean
) WITH (
          'connector'='starrocks',
          'load-url'='10.201.0.228:28030;10.201.0.229:28030;10.201.0.230:28030',
          'jdbc-url'='jdbc:mysql://10.201.0.228:29030,10.201.0.229:29030,10.201.0.230:29030',
          'username'='root',
          'password'='123456',
          'database-name'='test',
          'table-name'='t_for_test_w',
      'sink.properties.column_separator' = '\\x01',
      'sink.properties.row_delimiter' = '\\x02'
);

insert into starrocks_sink
select * from source_table ;