-- hive table
-- select * from myhive.flink.h_read_test_2;


-- sink
CREATE TABLE read_hive_sink (
  id VARCHAR
  ,name VARCHAR
) WITH (
  'connector.type' = 'kafka'
  ,'connector.version' = 'universal'
  ,'connector.topic' = 'read_hive_sink'                            -- required: topic name from which the table is read
  ,'connector.properties.zookeeper.connect' = 'master:2181'    -- required: specify the ZooKeeper connection string
  ,'connector.properties.bootstrap.servers' = 'master:6667'    -- required: specify the Kafka server connection string
  ,'connector.properties.group.id' = 'flink_sql'                   -- optional: required in Kafka consumer, specify consumer group
  ,'connector.startup-mode' = 'group-offsets'                     -- optional: valid modes are "earliest-offset", "latest-offset", "group-offsets",  "specific-offsets"
  ,'connector.sink-partitioner' = 'fixed'                         --optional fixed 每个 flink 分区数据只发到 一个 kafka 分区
                                                                          -- round-robin flink 分区轮询分配到 kafka 分区
                                                                          -- custom 自定义分区策略
  --,'connector.sink-partitioner-class' = 'org.mycompany.MyPartitioner'   -- 自定义分区类
  ,'format.type' = 'json'                 -- required:  'csv', 'json' and 'avro'.
);

insert into read_hive_sink select id, name from h_read_test_2;