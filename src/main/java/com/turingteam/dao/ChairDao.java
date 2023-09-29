package com.turingteam.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.turingteam.domain.Chair;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChairDao extends BaseMapper<Chair>{
}
