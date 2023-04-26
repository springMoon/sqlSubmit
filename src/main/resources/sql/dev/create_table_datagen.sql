-- -- kafka source
-- set execution.runtime-mode=BATCH;
drop table if exists user_log;
CREATE TABLE user_log
(
    user_id     VARCHAR,
    item_id     VARCHAR,
    category_id VARCHAR,
    behavior    VARCHAR
) WITH (
      'connector' = 'datagen'
      ,'rows-per-second' = '20'
      ,'number-of-rows' = '10000'
      ,'fields.user_id.kind' = 'random'
      ,'fields.item_id.kind' = 'random'
      ,'fields.category_id.kind' = 'random'
      ,'fields.behavior.kind' = 'random'
      ,'fields.user_id.length' = '20'
      ,'fields.item_id.length' = '10'
      ,'fields.category_id.length' = '10'
      ,'fields.behavior.length' = '10'
      );
