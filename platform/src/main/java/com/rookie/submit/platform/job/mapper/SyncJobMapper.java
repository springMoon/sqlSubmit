package com.rookie.submit.platform.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rookie.submit.platform.job.entity.SyncJobEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SyncJobMapper extends BaseMapper<SyncJobEntity> {
}
