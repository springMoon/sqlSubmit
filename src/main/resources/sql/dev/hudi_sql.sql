-- sets up the result mode to tableau to show the results directly in the CLI
-- set execution.result-mode=tableau;
-- set sql-client.execution.result-mode=tableau;

CREATE TABLE t1(
  uuid VARCHAR(20), -- you can use 'PRIMARY KEY NOT ENFORCED' syntax to mark the field as record key
  name VARCHAR(10),
  age INT,
  ts TIMESTAMP(3),
  `partition` VARCHAR(20)
)
PARTITIONED BY (`partition`)
WITH (
  'connector' = 'hudi',
  'path' = 'file:///hudi/',
--    'path' = 'file:///opt/data',
  'write.tasks' = '1', -- default is 4 ,required more resource
  'compaction.tasks' = '1', -- default is 10 ,required more resource
  'table.type' = 'MERGE_ON_READ' -- this creates a MERGE_ON_READ table, by default is COPY_ON_WRITE
);

-- insert data using values
INSERT INTO t1 VALUES  ('id1','Danny',23,TIMESTAMP '1970-01-01 00:00:01','par1');