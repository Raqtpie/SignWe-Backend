package com.turingteam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.turingteam.domain.Chair;

public interface ChairService extends IService<Chair> {
    /**
     * 签退
     * @param chairId 座位id
     * @param userId 用户id
     */
    void life(Integer chairId, String userId);

    void closeAllChair();
}
