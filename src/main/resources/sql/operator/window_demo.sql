-- mysql cdc to print
-- creates a mysql table source
drop table if exists t_feature_source;
CREATE TABLE t_feature_source (
  id bigint
  ,code VARCHAR
  ,rms DOUBLE
  ,mean DOUBLE
  ,peak DOUBLE
  ,kurtosis DOUBLE
  ,skewness DOUBLE
  ,send_time TIMESTAMP(3)
  ,WATERMARK FOR send_time AS send_time
) WITH (
 'connector' = 'kafka'
  ,'topic' = 't_feature_1'
  ,'properties.bootstrap.servers' = '10.201.0.39:9092'
  ,'properties.group.id' = 't_feature_source'
  ,'format' = 'json'
);

-- kafka sink
-- drop table if exists t_feature_sink;
-- CREATE TABLE t_feature_sink (
--   window_start TIMESTAMP(3)
--   ,window_end TIMESTAMP(3)
--   ,min_rms DOUBLE
--   ,max_rms DOUBLE
--   ,avg_rms DOUBLE
-- ) WITH (
--   'connector' = 'print'
-- );

-- sink to kafka
-- insert into t_feature_sink
-- select window_start,window_end,min(rms) min_rms, max(rms) max_rms, avg(rms) avg_rms
-- from TABLE(TUMBLE(TABLE t_feature_source, DESCRIPTOR(send_time), INTERVAL '1' MINUTES))
-- where code = 'B416_1'
-- group by window_start,window_end;

CREATE TABLE t_feature_source_sink (
  id bigint
  ,code VARCHAR
  ,rms DOUBLE
  ,mean DOUBLE
  ,peak DOUBLE
  ,kurtosis DOUBLE
  ,skewness DOUBLE
  ,send_time TIMESTAMP(3)
) WITH (
 'connector' = 'print'
);

insert into t_feature_source_sink
select id, code, rms, mean, peak, kurtosis, skewness, send_time from t_feature_source