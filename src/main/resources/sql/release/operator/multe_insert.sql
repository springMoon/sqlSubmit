-- parse special json, then process window
CREATE TABLE t_feature (
   data  string
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'test_dd'
  ,'properties.bootstrap.servers' = 'localhost:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'csv'
);

CREATE TABLE t_sink_1 (
    data          STRING
) WITH (
   'connector' = 'print'
);

CREATE TABLE t_sink_2 (
    data          STRING
) WITH (
   'connector' = 'print'
);
insert into t_sink_1
select concat(data, '_1') from t_feature;

insert into t_sink_2
select concat(data, '_2') from t_feature;
