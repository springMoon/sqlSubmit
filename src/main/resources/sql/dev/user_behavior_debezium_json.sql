-- 1. 源表：去掉 PRIMARY KEY（不定义主键）
CREATE TABLE user_behavior_debezium_json_source (
  after ROW<
    next_page   STRING,
    category_id INT,
    user_id     STRING,
    item_id     STRING,
    price       DECIMAL(20, 10),
    last_page   STRING,
    created_at  BIGINT,
    id          BIGINT,
    page        STRING,
    `position`  STRING,
    sort        STRING,
    behavior    STRING
  >,
  id STRING
) WITH (
  'connector' = 'kafka',
  'topic' = 'user_behavior_debezium_json',
  'properties.bootstrap.servers' = '10.201.0.191:9092',
  'properties.group.id' = 'user_behavior_debezium_json',
  'scan.startup.mode' = 'latest-offset',
  'format' = 'debezium-json',
  'debezium-json.ignore-parse-errors' = 'true'
);

-- 2. 目标表：upsert-kafka sink，主键使用物理列 id
CREATE TABLE user_behavior_debezium_json_sink (
  id          BIGINT,
  next_page   STRING,
  category_id INT,
  user_id     STRING,
  item_id     STRING,
  price       DECIMAL(20, 10),
  last_page   STRING,
  page        STRING,
  `position`  STRING,
  sort        STRING,
  behavior    STRING,
  created_at  TIMESTAMP(3),
  PRIMARY KEY (id) NOT ENFORCED
) WITH (
  'connector' = 'upsert-kafka',
  'topic' = 'user_behavior_debezium_json_out',
  'properties.bootstrap.servers' = '10.201.0.191:9092',
  'key.format' = 'json',
  'value.format' = 'json'
);

-- 3. 插入数据
INSERT INTO user_behavior_debezium_json_sink
SELECT
  id,
  after.next_page,
  after.category_id,
  after.user_id,
  after.item_id,
  after.price,
  after.last_page,
  after.page,
  after.`position`,
  after.sort,
  after.behavior,
  TO_TIMESTAMP_LTZ(after.created_at, 3) AS created_at
FROM user_behavior_debezium_json_source
;