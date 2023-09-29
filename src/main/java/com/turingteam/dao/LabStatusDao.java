package com.turingteam.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.turingteam.domain.LabStatus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LabStatusDao extends BaseMapper<LabStatus> {
}
