-- time-windowd join
---sourceTable
-- 订单表
CREATE TABLE t_order(
    order_id VARCHAR,         -- 订单 id
    product_id VARCHAR,       -- 产品 id
    create_time VARCHAR, -- 订单时间
    order_proctime as PROCTIME()
) WITH (
    'connector.type' = 'kafka',
    'connector.version' = 'universal',
    'connector.topic' = 'order',
    'connector.startup-mode' = 'latest-offset',
    'connector.properties.zookeeper.connect' = 'venn:2181',
    'connector.properties.bootstrap.servers' = 'venn:9092',
    'update-mode' = 'append',
    'format.type' = 'json',
    'format.derive-schema' = 'true'
);
---sourceTable
--产品表
CREATE TABLE t_product (
    product_id VARCHAR,     -- 产品 id
    price DECIMAL(38,18),          -- 价格
    create_time VARCHAR, -- 订单时间
    product_proctime as PROCTIME()
) WITH (
    'connector.type' = 'kafka',
    'connector.version' = 'universal',
    'connector.topic' = 'shipments',
    'connector.startup-mode' = 'latest-offset',
    'connector.properties.zookeeper.connect' = 'venn:2181',
    'connector.properties.bootstrap.servers' = 'venn:9092',
    'update-mode' = 'append',
    'format.type' = 'json',
    'format.derive-schema' = 'true'
);

---sinkTable
--订单表 关联 产品表 成订购表
CREATE TABLE order_detail (
    order_id VARCHAR,
    producer_id VARCHAR,
    price DECIMAL(38,18),
    order_create_time VARCHAR,
    product_create_time VARCHAR
) WITH (
    'connector.type' = 'kafka',
    'connector.version' = 'universal',
    'connector.topic' = 'order_detail',
    'connector.startup-mode' = 'latest-offset',
    'connector.properties.zookeeper.connect' = 'venn:2181',
    'connector.properties.bootstrap.servers' = 'venn:9092',
    'update-mode' = 'append',
    'format.type' = 'json',
    'format.derive-schema' = 'true'
);

---order_sink
INSERT INTO order_detail(order_id, product_id, price, create_time)
SELECT a.order_id, a.product_id, b.price, a.create_time, b.create_time
FROM t_order a
  INNER JOIN t_product b ON a.product_id = b.product_id and a.order_proctime BETWEEN b.product_proctime - INTERVAL '10' MINUTE AND b.product_proctime + INTERVAL '10' MINUTE
where a.order_id is not null;
