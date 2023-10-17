package com.turingteam.domain.wechat;

import lombok.Data;

@Data
public class WeChatGetAccessTokenResponse {
    private String access_token;

    private String expires_in;
}
