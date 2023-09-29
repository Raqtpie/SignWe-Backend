package com.turingteam.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.turingteam.domain.UserRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRecordDao extends BaseMapper<UserRecord> {
}
