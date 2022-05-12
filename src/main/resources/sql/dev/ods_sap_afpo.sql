drop table if  exists  stg_sap_afpo;
create table if not exists stg_sap_afpo (
    `aufnr` string
    ,`mandt` string
    ,`posnr` string
    ,`ablad` string
    ,`amein` string
    ,`anzsn` string
    ,`arsnr` string
    ,`arsps` string
    ,`berid` string
    ,`beskz` string
    ,`bwtar` string
    ,`bwtty` string
    ,`ch_proc` string
    ,`charg` string
    ,`cuobj` string
    ,`cuobj_root` string
    ,`dauat` string
    ,`dauty` string
    ,`dfrei` string
    ,`dgltp` string
    ,`dglts` string
    ,`dnrel` string
    ,`dwerk` string
    ,`elikz` string
    ,`etrmp` string
    ,`fsh_collection` string
    ,`fsh_salloc_qty` string
    ,`fsh_season` string
    ,`fsh_season_year` string
    ,`fsh_theme` string
    ,`fxpru` string
    ,`gsber` string
    ,`gsbtr` string
    ,`iamng` string
    ,`insmk` string
    ,`kalnr` string
    ,`kbnkz` string
    ,`kckey` string
    ,`kdauf` string
    ,`kdein` string
    ,`kdpos` string
    ,`knttp` string
    ,`krsnr` string
    ,`krsps` string
    ,`ksbis` string
    ,`ksvon` string
    ,`kunnr2` string
    ,`kzavc` string
    ,`kzbws` string
    ,`kzvbr` string
    ,`lgort` string
    ,`ltrmi` string
    ,`ltrmp` string
    ,`matnr` string
    ,`meins` string
    ,`mill_oc_aufnr_u` string
    ,`mill_oc_rumng` string
    ,`mill_oc_sort` string
    ,`ndisr` string
    ,`objnp` string
    ,`objtype` string
    ,`pamng` string
    ,`pgmng` string
    ,`plnum` string
    ,`projn` string
    ,`psamg` string
    ,`psmng` string
    ,`psobs` string
    ,`pwerk` string
    ,`qunum` string
    ,`qupos` string
    ,`rtp01` string
    ,`rtp02` string
    ,`rtp03` string
    ,`rtp04` string
    ,`safnr` string
    ,`sernp` string
    ,`sernr` string
    ,`sgt_scat` string
    ,`sobkz` string
    ,`strmp` string
    ,`techs` string
    ,`techs_copy` string
    ,`tpauf` string
    ,`uebtk` string
    ,`uebto` string
    ,`umren` string
    ,`umrez` string
    ,`untto` string
    ,`verid` string
    ,`verto` string
    ,`vfmng` string
    ,`weaed` string
    ,`webaz` string
    ,`wemng` string
    ,`wempf` string
    ,`wepos` string
    ,`weunb` string
    ,`wewrt` string
    ,`xloek` string
    ,`ernam` string
    ,`erdat` string
    ,`aenam` string
    ,`aedat` string
    ,`erfzeit` string
    ,`aezeit` string
    ,`cpd_updat` string
    ,`in_date` string
    ,`source_db` string
) WITH (
      'connector'='starrocks',
      'load-url'='172.28.26.104:18030',
      'jdbc-url'='jdbc:mysql://172.28.26.104:19030',
      'username'='root',
      'password'='123456',
      'database-name'='hive',
      'table-name'='stg_sap_afpo'
      );




drop table if  exists  stg_sap_afpo;
create table if not exists stg_sap_afpo (
                                               `aufnr` string
    ,`mandt` string
    ,`posnr` string
    ,`ablad` string
    ,`amein` string
    ,`anzsn` string
    ,`arsnr` string
    ,`arsps` string
    ,`berid` string
    ,`beskz` string
    ,`bwtar` string
    ,`bwtty` string
    ,`ch_proc` string
    ,`charg` string
    ,`cuobj` string
    ,`cuobj_root` string
    ,`dauat` string
    ,`dauty` string
    ,`dfrei` string
    ,`dgltp` string
    ,`dglts` string
    ,`dnrel` string
    ,`dwerk` string
    ,`elikz` string
    ,`etrmp` string
    ,`fsh_collection` string
    ,`fsh_salloc_qty` string
    ,`fsh_season` string
    ,`fsh_season_year` string
    ,`fsh_theme` string
    ,`fxpru` string
    ,`gsber` string
    ,`gsbtr` string
    ,`iamng` string
    ,`insmk` string
    ,`kalnr` string
    ,`kbnkz` string
    ,`kckey` string
    ,`kdauf` string
    ,`kdein` string
    ,`kdpos` string
    ,`knttp` string
    ,`krsnr` string
    ,`krsps` string
    ,`ksbis` string
    ,`ksvon` string
    ,`kunnr2` string
    ,`kzavc` string
    ,`kzbws` string
    ,`kzvbr` string
    ,`lgort` string
    ,`ltrmi` string
    ,`ltrmp` string
    ,`matnr` string
    ,`meins` string
    ,`mill_oc_aufnr_u` string
    ,`mill_oc_rumng` string
    ,`mill_oc_sort` string
    ,`ndisr` string
    ,`objnp` string
    ,`objtype` string
    ,`pamng` string
    ,`pgmng` string
    ,`plnum` string
    ,`projn` string
    ,`psamg` string
    ,`psmng` string
    ,`psobs` string
    ,`pwerk` string
    ,`qunum` string
    ,`qupos` string
    ,`rtp01` string
    ,`rtp02` string
    ,`rtp03` string
    ,`rtp04` string
    ,`safnr` string
    ,`sernp` string
    ,`sernr` string
    ,`sgt_scat` string
    ,`sobkz` string
    ,`strmp` string
    ,`techs` string
    ,`techs_copy` string
    ,`tpauf` string
    ,`uebtk` string
    ,`uebto` string
    ,`umren` string
    ,`umrez` string
    ,`untto` string
    ,`verid` string
    ,`verto` string
    ,`vfmng` string
    ,`weaed` string
    ,`webaz` string
    ,`wemng` string
    ,`wempf` string
    ,`wepos` string
    ,`weunb` string
    ,`wewrt` string
    ,`xloek` string
    ,`ernam` string
    ,`erdat` string
    ,`aenam` string
    ,`aedat` string
    ,`erfzeit` string
    ,`aezeit` string
    ,`cpd_updat` string
    ,`in_date` string
    ,`source_db` string
)WITH (
     'connector' = 'filesystem',
     -- 'path' = 'hdfs://172.28.9.102/user/hive/warehouse/bigdata.db/ods_sap_afpo',
     'path' = 'hdfs:////user/venn/ods_sap_afpo',
     'format' = 'parquet'
     );
insert into stg_sap_afpo
select * from stg_sap_afpo ;
