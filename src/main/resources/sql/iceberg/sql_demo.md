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
drop catalog iceberg;
CREATE CATALOG iceberg WITH (
  'type'='iceberg',
  'catalog-type'='hive',
  'uri'='thrift://thinkpad:9083',
  'clients'='5',
  'property-version'='1',
  'warehouse'='hdfs://thinkpad:8020/user/hive/datalake/iceberg.db'
);
```


### hadoop catalog
```sql
CREATE CATALOG iceberg WITH (
  'type'='iceberg',
  'catalog-type'='hadoop',
  'warehouse'='hdfs://thinkpad:8020/user/hive/datalake/iceberg.db',
  'property-version'='1'
);
```


## database
```sql
CREATE DATABASE iceberg;
USE iceberg;
```

## ddl
```sql
-- create table
CREATE TABLE `iceberg`.`iceberg`.`sample` (
    id BIGINT COMMENT 'unique id',
    data STRING
);

-- PARTITIONED BY
CREATE TABLE `iceberg`.`iceberg`.`sample1` (
    id BIGINT COMMENT 'unique id',
    data STRING
) PARTITIONED BY (data);

-- CREATE TABLE LIKE
CREATE TABLE  `iceberg`.`iceberg`.`sample_like` LIKE `iceberg`.`iceberg`.`sample`;

-- ALTER TABLE
ALTER TABLE `iceberg`.`iceberg`.`sample` SET ('write.format.default'='avro');

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