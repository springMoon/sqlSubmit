CREATE TABLE t_feature (
  header STRING
  ,readModule STRING
  ,checkPoint STRING
  ,operation STRING
  ,location ROW(id BIGINT, code STRING, send_time STRING, rms  decimal(12, 8),mean decimal(12, 8),peak decimal(12, 8),kurtosis decimal(12, 8),skewness decimal(12, 8))
  ,data ROW(meta STRING, `rows` ARRAY<STRING>)
  ,process_time as proctime()
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'test_dd'
  ,'properties.bootstrap.servers' = '10.201.1.131:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

CREATE TABLE t_sink (
    operation    STRING
    ,id          bigint
    ,code        STRING
    ,send_time   BIGINT
    ,rms         decimal(12, 8)
    ,mean        decimal(12, 8)
    ,peak        decimal(12, 8)
    ,kurtosis    decimal(12, 8)
    ,skewness    decimal(12, 8)
    ,l_code      STRING
) WITH (
   'connector' = 'print'
);

INSERT INTO t_sink
SELECT operation
    ,cast(data.`rows`[1] as bigint) id
    ,cast(data.`rows`[2] as string) code
    ,cast(data.`rows`[3] as BIGINT) send_time
    ,cast(data.`rows`[4] as decimal(12, 8)) rms
    ,cast(data.`rows`[5] as decimal(12, 8)) mean
    ,cast(data.`rows`[6] as decimal(12, 8)) peak
    ,cast(data.`rows`[7] as decimal(12, 8)) kurtosis
    ,cast(data.`rows`[8] as decimal(12, 8)) skewness
    ,location.code
FROM t_feature