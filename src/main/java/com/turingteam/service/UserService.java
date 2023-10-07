package com.turingteam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.turingteam.domain.User;
import com.turingteam.domain.dto.UserDto;

public interface UserService extends IService<User> {
    void register(UserDto userDto);

    String login(String jsCode);
}
