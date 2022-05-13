CREATE TABLE mysql_tbls (
    TBL_ID             BIGINT
    ,CREATE_TIME        INT
    ,DB_ID              BIGINT
    ,LAST_ACCESS_TIME   INT
    ,OWNER              VARCHAR(767)
    ,OWNER_TYPE         VARCHAR(10)
    ,RETENTION          INT
    ,SD_ID              BIGINT
    ,TBL_NAME           VARCHAR(256)
    ,TBL_TYPE           VARCHAR(128)
    ,VIEW_EXPANDED_TEXT STRING
    ,VIEW_ORIGINAL_TEXT STRING
    ,PRIMARY KEY (TBL_ID) NOT ENFORCED
) WITH (
 'connector' = 'mysql-cdc'
 ,'hostname' = 'localhost'
 ,'port' = '3306'
 ,'username' = 'root'
 ,'password' = '123456'
 ,'database-name' = 'hive_3'
 ,'table-name' = 'tbls'
 ,'server-id' = '5400-5440'
 ,'scan.startup.mode' = 'initial'
);

-- kafka sink
drop table if exists mysql_tbls_new;
CREATE TABLE mysql_tbls_new (
    TBL_ID             BIGINT
    ,CREATE_TIME        INT
    ,DB_ID              BIGINT
    ,LAST_ACCESS_TIME   INT
    ,OWNER              VARCHAR(767)
    ,OWNER_TYPE         VARCHAR(10)
    ,RETENTION          INT
    ,SD_ID              BIGINT
    ,TBL_NAME           VARCHAR(256)
    ,TBL_TYPE           VARCHAR(128)
    ,VIEW_EXPANDED_TEXT STRING
    ,VIEW_ORIGINAL_TEXT STRING
--     ,PRIMARY KEY (tbl_id) NOT ENFORCED
) WITH (
    'connector' = 'print'
--       'connector' = 'jdbc'
--       ,'url' = 'jdbc:mysql://venn:3306/venn'
--       ,'table-name' = 'tbls_new'
--       ,'username' = 'root'
--       ,'password' = '123456'
);

insert into mysql_tbls_new
select TBL_ID,CREATE_TIME,DB_ID,LAST_ACCESS_TIME,OWNER,OWNER_TYPE,RETENTION,SD_ID,TBL_NAME,TBL_TYPE,VIEW_EXPANDED_TEXT,VIEW_ORIGINAL_TEXT
from mysql_tbls;

-- create table TBLS
-- (
--     TBL_ID             bigint                          not null
--         primary key,
--     CREATE_TIME        int                             not null,
--     DB_ID              bigint                          null,
--     LAST_ACCESS_TIME   int                             not null,
--     OWNER              varchar(767) collate latin1_bin null,
--     OWNER_TYPE         varchar(10) collate latin1_bin  null,
--     RETENTION          int                             not null,
--     SD_ID              bigint                          null,
--     TBL_NAME           varchar(256) collate latin1_bin null,
--     TBL_TYPE           varchar(128) collate latin1_bin null,
--     VIEW_EXPANDED_TEXT mediumtext                      null,
--     VIEW_ORIGINAL_TEXT mediumtext                      null,
--     IS_REWRITE_ENABLED bit default b'0'                not null
-- );