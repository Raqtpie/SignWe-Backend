package com.turingteam.domain.wechat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatResponse {

    private String openid;

    private String session_key;

    private String unionid;

    private int errcode;

    private String errmsg;
}
