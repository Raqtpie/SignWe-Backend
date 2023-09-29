package com.turingteam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.turingteam.domain.UserRecord;
import com.turingteam.domain.dto.RecordDto;

import java.util.List;

public interface UserRecordService extends IService<UserRecord> {
    List<RecordDto> getList();

    UserRecord getTotalTimeByUserId(Integer userId);

    List<RecordDto> getRecordYesterday();
}
