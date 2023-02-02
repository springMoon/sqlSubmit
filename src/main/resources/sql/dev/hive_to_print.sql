-- read hive, write to print -- batch when read complete, job finish
-- sink
drop table if exists read_hiv_sink;
CREATE TABLE read_hiv_sink (
  user_id VARCHAR
  ,item_id VARCHAR
  ,category_id VARCHAR
  ,behavior VARCHAR
  ,ds VARCHAR
) WITH (
  'connector' = 'print'
);

-- set streaming-source.enable = false;
-- set execution.runtime-mode = batch;
insert into read_hiv_sink
select user_id, item_id, category_id, behavior, ds
from myHive.test.user_log;