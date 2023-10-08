package com.turingteam.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turingteam.common.CustomException;
import com.turingteam.constants.WeChatResponseCode;
import com.turingteam.dao.UserDao;
import com.turingteam.domain.User;
import com.turingteam.domain.dto.UserDto;
import com.turingteam.domain.dto.WeChatResponseDto;
import com.turingteam.service.UserService;
import com.turingteam.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @Value("${wx.grant_type}")
    private String grantType;

    @Override
    public void register(UserDto userDto) {
        String openid = getOpenid(userDto.getJsCode());
        if (getById(openid) != null) {
            throw new CustomException("用户已存在");
        }
        User user = new User();
        user.setId(openid);
        user.setName(userDto.getName());
        user.setClassName(userDto.getClassName());
        user.setPermission(0);
        boolean save = save(user);
        if (!save) {
            throw new CustomException("注册失败");
        }
    }

    @Override
    public String login(String jsCode) {
        String openid = getOpenid(jsCode);
        User user = getById(openid);
        if (user == null) {
            throw new CustomException("用户不存在，请先注册");
        }
        return JwtUtil.generateToken(openid);
    }

    private String getOpenid(String jsCode) {
        String apiUrl = "https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + appid +
                "&secret=" + secret +
                "&js_code=" + jsCode +
                "&grant_type=" + grantType;
        String responseStr = restTemplate.getForObject(apiUrl, String.class);
        if (responseStr == null) {
            throw new CustomException("微信服务器无法响应，请稍后再试。");
        }
        WeChatResponseDto response = JSON.parseObject(responseStr, WeChatResponseDto.class);
        String openid;
        if (response != null) {
            if (response.getErrcode() == WeChatResponseCode.INVALID_CODE || response.getErrcode() == WeChatResponseCode.USED_CODE) {
                throw new CustomException("jsCode无效，请稍后再试。");
            }
            openid = response.getOpenid();
            if (openid == null) {
                throw new CustomException("无法获取用户ID，请稍后再试。");
            }
        } else {
            throw new CustomException("无法获取用户ID，请稍后再试。");
        }
        return openid;
    }
}
