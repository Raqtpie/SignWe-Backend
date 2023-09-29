package com.turingteam.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.turingteam.domain.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordDao extends BaseMapper<Record> {
}
