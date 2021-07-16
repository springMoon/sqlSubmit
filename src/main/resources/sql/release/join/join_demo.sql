-- Regular Joins like Global Join
---sourceTable
-- 订单表
CREATE TABLE t_order(
    order_id VARCHAR,         -- 订单 id
    product_id VARCHAR,       -- 产品 id
    create_time VARCHAR -- 订单时间
) WITH (
    'connector.type' = 'kafka'
    ,'connector.version' = 'universal'
    ,'connector.topic' = 'order'
    ,'connector.properties.zookeeper.connect' = 'venn:2181'
    ,'connector.properties.bootstrap.servers' = 'venn:9092'
    ,'update-mode' = 'append'
    ,'format.type' = 'json'
    ,'format.derive-schema' = 'true'
);
---sourceTable
--产品表
CREATE TABLE t_product (
    product_id VARCHAR,     -- 产品 id
    price DECIMAL(38,18),          -- 价格
    create_time VARCHAR -- 订单时间
) WITH (
    'connector.type' = 'kafka'
    ,'connector.version' = 'universal'
    ,'connector.topic' = 'shipments'
    ,'connector.startup-mode' = 'latest-offset'
    ,'connector.properties.zookeeper.connect' = 'venn:2181'
    ,'connector.properties.bootstrap.servers' = 'venn:9092'
    ,'update-mode' = 'append'
    ,'format.type' = 'json'
    ,'format.derive-schema' = 'true'
);

---sinkTable
--订单表 关联 产品表 成订购表
CREATE TABLE order_detail (
    order_id VARCHAR,
    producer_id VARCHAR ,
    price DECIMAL(38,18),
    order_create_time VARCHAR,
    product_create_time VARCHAR
) WITH (
    'connector.type' = 'kafka'
    ,'connector.version' = 'universal'
    ,'connector.topic' = 'order_detail'
    ,'connector.startup-mode' = 'latest-offset'
    ,'connector.properties.zookeeper.connect' = 'venn:2181'
    ,'connector.properties.bootstrap.servers' = 'venn:9092'
    ,'update-mode' = 'append'
    ,'format.type' = 'json'
    ,'format.derive-schema' = 'true'
);

---order_sink
INSERT INTO order_detail(order_id, product_id, price, create_time)
SELECT a.order_id, a.product_id, b.price, a.create_time, b.create_time
FROM t_order a
  INNER JOIN t_product b ON a.product_id = b.product_id
where a.order_id is not null;

-- 可以再尝试个 聚合的
--product_total
--INSERT INTO product_total
--select a.product_id, sum(price) total
--from order a INNER join product b where a.product_id = b.product_id
--group by a.product_id