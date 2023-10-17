package com.turingteam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.turingteam.domain.LabStatus;

import java.util.List;

public interface LabStatusService extends IService<LabStatus> {
    /**
     * 实验室开门时，给订阅消息的用户发送消息
     */
    void sendMsg(List<String> userIdList, String operator);
}
