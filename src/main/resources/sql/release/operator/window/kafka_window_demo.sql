-- parse special json, then process window
CREATE TABLE t_feature (
  header      ROW(`catalog` STRING, readTime bigint)
  ,readModule STRING
  ,checkPoint STRING
  ,operation  STRING
  ,location   ROW(id BIGINT, code STRING, send_time STRING, rms  decimal(12, 8),mean decimal(12, 8),peak decimal(12, 8),kurtosis decimal(12, 8),skewness decimal(12, 8))
  ,data       ROW(meta STRING, `rows` ARRAY<STRING>)
  ,process_time as proctime()
--   ,watermark for header.readTime as header.readTime - INTERVAL '5' SECOND -- not work
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'test_dd'
  ,'properties.bootstrap.servers' = '10.201.1.131:9092'
  ,'properties.group.id' = 'user_log'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

create view v_t_feature as
SELECT operation
    ,cast(data.`rows`[1] as bigint) id
    ,cast(data.`rows`[2] as string) code
    ,FROM_UNIXTIME(cast(data.`rows`[3] as bigint))  send_time
    ,cast(data.`rows`[4] as decimal(12, 8)) rms
    ,cast(data.`rows`[5] as decimal(12, 8)) mean
    ,cast(data.`rows`[6] as decimal(12, 8)) peak
    ,cast(data.`rows`[7] as decimal(12, 8)) kurtosis
    ,cast(data.`rows`[8] as decimal(12, 8)) skewness
    ,location.code
    ,process_time
--     ,read_time
FROM t_feature;

CREATE TABLE t_sink (
    code          STRING
    ,window_start TIMESTAMP(3)
    ,window_end   TIMESTAMP(3)
    ,coun         BIGINT
    ,rms          DECIMAL(12, 8)
) WITH (
   'connector' = 'print'
);

insert into t_sink
SELECT code, window_start, window_end, count(id) coun, min(rms) min_rms FROM
TABLE(TUMBLE(TABLE v_t_feature, DESCRIPTOR(process_time), INTERVAL '1' MINUTES))
   GROUP BY code, window_start, window_end
;
