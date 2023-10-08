package com.turingteam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.turingteam.domain.UserRecord;
import com.turingteam.domain.dto.RecordDto;

import java.util.List;

public interface UserRecordService extends IService<UserRecord> {
    /**
     * 获取所有记录
     * @return 所有记录
     */
    List<RecordDto> getList();

    /**
     * 获取某个用户的所有记录
     * @param userId 用户id
     * @return 某个用户的所有记录
     */
    UserRecord getTotalTimeByUserId(String userId);

    /**
     * 获取今天的记录
     * @return 今天的记录
     */
    UserRecord getTotalTimeByUserIdToday(String userId);

    /**
     * 获取昨天的记录
     * @return 昨天的记录
     */
    List<RecordDto> getRecordYesterday();
}
