-- 从 datagen 读取测试数据，写入 print connector

CREATE TABLE datagen_source (
    id BIGINT,
    name STRING,
    age INT,
    score DOUBLE,
    event_time AS PROCTIME()
) WITH (
    'connector' = 'datagen',
    'rows-per-second' = '1',
    'fields.id.kind' = 'sequence',
    'fields.id.start' = '1',
    'fields.id.end' = '1000000',
    'fields.name.length' = '8',
    'fields.age.min' = '18',
    'fields.age.max' = '60',
    'fields.score.min' = '0',
    'fields.score.max' = '100'
);

CREATE TABLE print_sink (
    id BIGINT,
    name STRING,
    age INT,
    score DOUBLE,
    event_time TIMESTAMP_LTZ(3)
) WITH (
    'connector' = 'print'
);

INSERT INTO print_sink
SELECT
    id,
    name,
    age,
    score,
    event_time
FROM datagen_source;
