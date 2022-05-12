-- flink cumulate window tvf calc pv&uv
create table if not exists datagen_source (
    id        int
    ,name     string
    ,sex      string
    ,age      int
    ,birthday string
    ,proc_time as proctime()
) with (
    'connector' = 'datagen'
    ,'rows-per-second' = '10000'
    ,'fields.id.kind' = 'random'
    ,'fields.id.min' = '1'
    ,'fields.id.max' = '2000000'
);

create table if not exists print_sink(
    start_time string
    ,end_time string
    ,pv  bigint
    ,uv  bigint
) with (
    'connector' = 'print'
);

insert into print_sink
select
 date_format(window_start, 'HH:mm:ss')
 , date_format(window_end, 'HH:mm:ss')
 , count(id)
 , count(distinct id)
  FROM TABLE(
    CUMULATE(TABLE datagen_source, DESCRIPTOR(proc_time), INTERVAL '10' SECOND, INTERVAL '1' DAY))
  GROUP BY window_start, window_end
