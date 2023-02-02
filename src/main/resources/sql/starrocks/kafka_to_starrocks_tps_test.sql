drop table if exists kafka_to_starrocks_tps_test;
CREATE TABLE kafka_to_starrocks_tps_test (
    col_0   bigint
    ,col_1  bigint
    ,col_2  string
    ,col_3  timestamp(3)
    ,col_4  bigint
    ,col_5  tinyint
    ,col_6  tinyint
    ,col_7  decimal(13, 2)
    ,col_8  tinyint
    ,col_9  boolean
    ,col_10 bigint
    ,col_11 decimal(14, 3)
    ,col_12 decimal(13, 2)
    ,col_13 decimal(15, 4)
    ,col_14 tinyint
    ,col_15 decimal(16, 5)
    ,col_16 decimal(17, 3)
    ,col_17 tinyint
    ,col_18 timestamp(3)
    ,col_19 string
    ,col_20 decimal(15, 2)
    ,col_21 tinyint
    ,col_22 timestamp(3)
    ,col_23 timestamp(3)
    ,col_24 bigint
    ,col_25 decimal(15, 0)
    ,col_26 boolean
    ,col_27 bigint
    ,col_28 bigint
    ,col_29 decimal(17, 4)
    ,col_30 decimal(12, 0)
    ,col_31 boolean
    ,col_32 string
    ,col_33 bigint
    ,col_34 bigint
    ,col_35 string
    ,col_36 tinyint
    ,col_37 tinyint
    ,col_38 tinyint
    ,col_39 string
    ,col_40 decimal(14, 2)
    ,col_41 timestamp(3)
    ,col_42 timestamp(3)
    ,col_43 string
    ,col_44 decimal(12, 4)
    ,col_45 tinyint
    ,col_46 string
    ,col_47 string
    ,col_48 string
    ,col_49 timestamp(3)
    ,col_50 boolean
    ,col_51 timestamp(3)
    ,col_52 timestamp(3)
    ,col_53 boolean
    ,col_54 decimal(15, 2)
    ,col_55 bigint
    ,col_56 tinyint
    ,col_57 bigint
    ,col_58 timestamp(3)
    ,col_59 bigint
    ,col_60 decimal(14, 3)
    ,col_61 decimal(13, 4)
    ,col_62 boolean
    ,col_63 string
    ,col_64 boolean
    ,col_65 bigint
    ,col_66 bigint
    ,col_67 boolean
    ,col_68 tinyint
    ,col_69 string
    ,col_70 tinyint
    ,col_71 boolean
    ,col_72 decimal(16, 0)
    ,col_73 boolean
    ,col_74 decimal(17, 2)
    ,col_75 string
    ,col_76 bigint
    ,col_77 string
    ,col_78 string
    ,col_79 timestamp(3)
    ,col_80 boolean
    ,col_81 tinyint
    ,col_82 decimal(15, 1)
    ,col_83 decimal(14, 2)
    ,col_84 tinyint
    ,col_85 string
    ,col_86 tinyint
    ,col_87 tinyint
    ,col_88 tinyint
    ,col_89 string
    ,col_90 string
    ,col_91 bigint
    ,col_92 decimal(12, 5)
    ,col_93 boolean
    ,col_94 tinyint
    ,col_95 string
    ,col_96 string
    ,col_97 string
    ,col_98 tinyint
    ,col_99 timestamp(3)
    ,col_100    timestamp(3)
) WITH (
      'connector' = 'kafka'
      ,'topic' = 't_for_test'
      ,'properties.bootstrap.servers' = 'localhost:9092'
      ,'properties.group.id' = 't_for_test'
      ,'scan.startup.mode' = 'latest-offset'
      ,'format' = 'json'
      );


drop table if  exists  kafka_to_starrocks_tps_test_sink;
create table if not exists kafka_to_starrocks_tps_test_sink (
    col_0  bigint
    ,col_1  bigint
    ,col_2  string
    ,col_3  timestamp(3)
    ,col_4  bigint
    ,col_5  tinyint
    ,col_6  tinyint
    ,col_7  decimal(13, 2)
    ,col_8  tinyint
    ,col_9  boolean
    ,col_10 bigint
    ,col_11 decimal(14, 3)
    ,col_12 decimal(13, 2)
    ,col_13 decimal(15, 4)
    ,col_14 tinyint
    ,col_15 decimal(16, 5)
    ,col_16 decimal(17, 3)
    ,col_17 tinyint
    ,col_18 timestamp(3)
    ,col_19 string
    ,col_20 decimal(15, 2)
    ,col_21 tinyint
    ,col_22 timestamp(3)
    ,col_23 timestamp(3)
    ,col_24 bigint
    ,col_25 decimal(15, 0)
    ,col_26 boolean
    ,col_27 bigint
    ,col_28 bigint
    ,col_29 decimal(17, 4)
    ,col_30 decimal(12, 0)
    ,col_31 boolean
    ,col_32 string
    ,col_33 bigint
    ,col_34 bigint
    ,col_35 string
    ,col_36 tinyint
    ,col_37 tinyint
    ,col_38 tinyint
    ,col_39 string
    ,col_40 decimal(14, 2)
    ,col_41 timestamp(3)
    ,col_42 timestamp(3)
    ,col_43 string
    ,col_44 decimal(12, 4)
    ,col_45 tinyint
    ,col_46 string
    ,col_47 string
    ,col_48 string
    ,col_49 timestamp(3)
    ,col_50 boolean
    ,col_51 timestamp(3)
    ,col_52 timestamp(3)
    ,col_53 boolean
    ,col_54 decimal(15, 2)
    ,col_55 bigint
    ,col_56 tinyint
    ,col_57 bigint
    ,col_58 timestamp(3)
    ,col_59 bigint
    ,col_60 decimal(14, 3)
    ,col_61 decimal(13, 4)
    ,col_62 boolean
    ,col_63 string
    ,col_64 boolean
    ,col_65 bigint
    ,col_66 bigint
    ,col_67 boolean
    ,col_68 tinyint
    ,col_69 string
    ,col_70 tinyint
    ,col_71 boolean
    ,col_72 decimal(16, 0)
    ,col_73 boolean
    ,col_74 decimal(17, 2)
    ,col_75 string
    ,col_76 bigint
    ,col_77 string
    ,col_78 string
    ,col_79 timestamp(3)
    ,col_80 boolean
    ,col_81 tinyint
    ,col_82 decimal(15, 1)
    ,col_83 decimal(14, 2)
    ,col_84 tinyint
    ,col_85 string
    ,col_86 tinyint
    ,col_87 tinyint
    ,col_88 tinyint
    ,col_89 string
    ,col_90 string
    ,col_91 bigint
    ,col_92 decimal(12, 5)
    ,col_93 boolean
    ,col_94 tinyint
    ,col_95 string
    ,col_96 string
    ,col_97 string
    ,col_98 tinyint
    ,col_99 timestamp(3)
    ,col_100    timestamp(3)
    ,PRIMARY key(col_0,col_1,col_2) NOT ENFORCED
) WITH (
          'connector'='starrocks',
          'load-url'='10.201.0.228:28030;10.201.0.229:28030;10.201.0.230:28030',
          'jdbc-url'='jdbc:mysql://10.201.0.228:29030,10.201.0.229:29030,10.201.0.230:29030',
          'username'='root',
          'password'='123456',
          'database-name'='test',
          'table-name'='t_for_test',
      'sink.buffer-flush.max-rows' = '64000',
      'sink.buffer-flush.max-bytes' = '300000000',
      'sink.buffer-flush.interval-ms' = '5000'
    ,'sink.properties.format' = 'json'
    ,'sink.properties.strip_outer_array' = 'true'
    ,'sink.properties.ignore_json_size' = 'true'
);

insert into kafka_to_starrocks_tps_test_sink
select * from kafka_to_starrocks_tps_test ;
