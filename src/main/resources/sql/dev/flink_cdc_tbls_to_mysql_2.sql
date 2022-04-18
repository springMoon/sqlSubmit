CREATE TABLE mysql_tbls (
    TBL_NAME           VARCHAR(256)
    ,PRIMARY KEY (TBL_NAME) NOT ENFORCED
) WITH (
 'connector' = 'mysql-cdc'
 ,'hostname' = 'localhost'
 ,'port' = '3306'
 ,'username' = 'root'
 ,'password' = '123456'
 ,'database-name' = 'venn'
 ,'table-name' = 'tbls'
 ,'server-id' = '5400-5440'
 ,'scan.startup.mode' = 'initial'
);

-- kafka sink
drop table if exists mysql_tbls_new;
CREATE TABLE mysql_tbls_new (
    TBL_NAME           VARCHAR(256)
    ,PRIMARY KEY (TBL_NAME) NOT ENFORCED
) WITH (
      'connector' = 'jdbc'
      ,'url' = 'jdbc:mysql://venn:3306/venn'
      ,'table-name' = 'tbls_new'
      ,'username' = 'root'
      ,'password' = '123456'
);

insert into mysql_tbls_new
select TBL_NAME
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