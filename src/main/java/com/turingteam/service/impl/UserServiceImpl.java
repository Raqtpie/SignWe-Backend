package com.turingteam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turingteam.dao.UserDao;
import com.turingteam.domain.User;
import com.turingteam.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
