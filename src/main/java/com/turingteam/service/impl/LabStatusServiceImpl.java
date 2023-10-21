package com.turingteam.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turingteam.dao.LabStatusDao;
import com.turingteam.domain.LabStatus;
import com.turingteam.domain.User;
import com.turingteam.domain.wechat.WeChatGetAccessTokenResponse;
import com.turingteam.domain.wechat.WeChatMessageData;
import com.turingteam.domain.wechat.WeChatMessageRequest;
import com.turingteam.domain.wechat.WeChatResponse;
import com.turingteam.service.LabStatusService;
import com.turingteam.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class LabStatusServiceImpl extends ServiceImpl<LabStatusDao, LabStatus> implements LabStatusService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @Value("${wx.template_id}")
    private String templateId;

    @Value("${wx.page}")
    private String page;

    @Value("${wx.miniprogram_state}")
    private String miniProgramState;

    @Value("${wx.lang}")
    private String lang;

    @Override
    public void sendMsg(List<String> userIdList, String operator) {
        String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        String responseStr = restTemplate.getForObject(getAccessTokenUrl, String.class);
        if (responseStr == null) {
            log.error("获取access_token失败, responseStr为空, 日期: {}", LocalDate.now());
        }
        WeChatGetAccessTokenResponse response = JSON.parseObject(responseStr, WeChatGetAccessTokenResponse.class);
        for (String userId : userIdList) {
            String accessToken = null;
            if (response != null) {
                accessToken = response.getAccess_token();
            }
            String sendMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;
            LocalDateTime now = LocalDateTime.now();
            String pattern = "yyyy-MM-dd HH:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            String DateTime = now.format(formatter);
            HashMap<String, String> thing5 = new HashMap<>();
            thing5.put("value", "实验室已开门，开门人：" + operator);
            HashMap<String, String> date2 = new HashMap<>();
            date2.put("value", DateTime);
            WeChatMessageRequest weChatMessageRequestDto = new WeChatMessageRequest(templateId, page, userId, miniProgramState, lang, new WeChatMessageData(thing5, date2));
            String requestBody = JSON.toJSONString(weChatMessageRequestDto);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody);
            ResponseEntity<String> sendMsgResponse = restTemplate.exchange(sendMsgUrl, HttpMethod.POST, requestEntity, String.class);
            String body = sendMsgResponse.getBody();
            WeChatResponse weChatResponse = JSON.parseObject(body, WeChatResponse.class);
            /*
              43101: 用户未订阅消息
              43107: 订阅消息能力封禁
             */
            if (weChatResponse != null && (weChatResponse.getErrcode() == 43101 || weChatResponse.getErrcode() == 43107)) {
                User user = userService.getById(userId);
                user.setSubscribeFlag(false);
                userService.updateById(user);
                log.info("发送订阅消息失败, userId: {}, 日期: {}", userId, LocalDate.now());
            }else if (weChatResponse != null && weChatResponse.getErrcode() != 0) {
                log.error("发送订阅消息失败, userId: {}, errcode: {}, errmsg: {}, 日期: {}", userId, weChatResponse.getErrcode(), weChatResponse.getErrmsg(), LocalDate.now());
            }
        }
    }
}
