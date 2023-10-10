package com.turingteam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.turingteam.domain.User;
import com.turingteam.domain.dto.UserDto;

public interface UserService extends IService<User> {
    /**
     * 注册
     * @param userDto 用户信息
     */
    String register(UserDto userDto);

    /**
     * 登录
     * @param jsCode 微信登录凭证
     * @return token
     */
    String login(String jsCode);
}
