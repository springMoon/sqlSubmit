insert into user_log_sink
SELECT user_id, item_id, category_id, behavior
FROM user_log;