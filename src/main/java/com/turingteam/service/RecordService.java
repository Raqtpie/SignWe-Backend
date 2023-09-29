package com.turingteam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.turingteam.domain.Record;

import java.util.List;

public interface RecordService extends IService<Record> {
    /**
     * 根据用户id获取记录
     * @param userId 用户id
     * @return 记录列表（时间降序）
     */
    List<Record> getRecordByUserId(Integer userId);
}
