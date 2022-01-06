# iceberg sql 

## catalog
### iceberg catalog 
* fail: need store database ?
```sql
CREATE CATALOG iceberg WITH (
  'type'='iceberg'
);
```


### iceberg catalog: catalog-type
```sql
drop catalog ice;
CREATE CATALOG ice WITH (
  'type'='iceberg',
  'catalog-type'='hive',
  'uri'='thrift://thinkpad:9083',
  'clients'='5',
  'property-version'='2',
  'warehouse'='hdfs://thinkpad:8020/user/hive/datalake/ice.db'
);
```


### hadoop catalog
```sql
CREATE CATALOG ice WITH (
  'type'='iceberg',
  'catalog-type'='hadoop',
  'warehouse'='hdfs://thinkpad:8020/user/hive/datalake/iceberg.db',
  'property-version'='1'
);
```


## database
```sql
CREATE DATABASE ice;
USE iceberg;


```

## ddl
```sql
-- create table
CREATE TABLE `ice`.`ice`.`sample` (
    id BIGINT COMMENT 'unique id',
    data STRING
);


CREATE TABLE flink_table (
    id   BIGINT,
    data STRING
) WITH (
    'connector'='iceberg',
    'catalog-name'='hive_prod',
    'uri'='thrift://thinkpad:9083',
    'warehouse'='hdfs://thinkpad:8020/user/hive/datalake/ice'
);


insert into sample values(1, 'data1');
insert into sample values(2, 'data2');
insert into sample values(3, 'data3');
insert into sample values(4, 'data4');


-- PARTITIONED BY
CREATE TABLE `ice`.`ice`.`sample1` (
    id BIGINT COMMENT 'unique id',
    data STRING
) PARTITIONED BY (data);

-- CREATE TABLE LIKE
CREATE TABLE  `ice`.`ice`.`sample_like` LIKE `iceberg`.`iceberg`.`sample`;

-- ALTER TABLE
ALTER TABLE `ice`.`iceberg`.`sample` SET ('write.format.default'='avro');

-- ALTER TABLE .. RENAME TO
ALTER TABLE `iceberg`.`iceberg`.`sample` RENAME TO `iceberg`.`iceberg`.`new_sample`;

-- DROP TABLE
DROP TABLE `iceberg`.`iceberg`.`sample`;

```
## Querying with SQL

### execute mode
```sql
-- Execute the flink job in streaming mode for current session context
SET execution.type = streaming

-- Execute the flink job in batch mode for current session context
SET execution.type = batch

```

### batch read
```sql
-- Execute the flink job in batch mode for current session context
SET execution.type = batch ;
SELECT * FROM sample       ;
```


## read
```sql
-- Submit the flink job in streaming mode for current session.
SET execution.runtime-mode = streaming ;
SET execution.runtime-mode = batch ;

-- Enable this switch because streaming read SQL will provide few job options in flink SQL hint options.
SET table.dynamic-table-options.enabled=true;

-- Read all the records from the iceberg current snapshot, and then read incremental data starting from that snapshot.
SELECT * FROM sample /*+ OPTIONS('streaming'='true', 'monitor-interval'='1s')*/ ;

-- Read all incremental data starting from the snapshot-id '3821550127947089987' (records from this snapshot will be excluded).
SELECT * FROM sample /*+ OPTIONS('streaming'='true', 'monitor-interval'='1s', 'start-snapshot-id'='3821550127947089987')*/ ;


```


## table
```sql

CREATE TABLE ice.ice.flink_table (
    id   BIGINT,
    data STRING
    ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
    'connector'='iceberg',
    'catalog-name'='hive_prod',
    'catalog-database'='ice',
    'uri'='thrift://thinkpad:9083',
    'warehouse'='hdfs://thinkpad:8020/user/hive/datalake/ice',
    'format-version' = '2'
);


insert into flink_table values(1, 'data1_0');
insert into flink_table values(1, 'data1_1');
insert into flink_table values(1, 'data1_2');
insert into flink_table values(1, 'data1_3');



```

## new
```sql

CREATE CATALOG ice WITH (
  'type'='iceberg',
  'catalog-type'='hive',
  'uri'='thrift://thinkpad:9083',
  'clients'='5',
  'property-version'='2',
  'warehouse'='hdfs://thinkpad:8020/user/hive/datalake/ice.db'
);

CREATE TABLE flink_table_source (
  id BIGINT
  ,data VARCHAR
) WITH (
  'connector' = 'kafka'
  ,'topic' = 'flink_table'
  ,'properties.bootstrap.servers' = 'thinkpad:9092'
  ,'properties.group.id' = 'user_log_x'
  ,'scan.startup.mode' = 'group-offsets'
  ,'format' = 'json'
);

{"id":1,"data":"data1_0"}
{"id":1,"data":"data1_1"}
{"id":1,"data":"data1_2"}
{"id":1,"data":"data1_3"}
{"id":1,"data":"data1_4"}
{"id":2,"data":"data1_0"}

drop table ice.ice.flink_table;
CREATE TABLE ice.ice.flink_table (
    id   BIGINT,
    data STRING
    ,PRIMARY KEY (id) NOT ENFORCED
) WITH (
    'format-version' = '2'
    ,'write.upsert.enabled' = 'true'
);


select * from ice.ice.flink_table /*+ OPTIONS('streaming'='true', 'monitor-interval'='1s', 'start-snapshot-id'='-1')*/;

select * from ice.ice.flink_table /*+ OPTIONS('streaming'='true', 'monitor-interval'='1s')*/;

insert into ice.ice.flink_table select id,data from flink_table_source;
```