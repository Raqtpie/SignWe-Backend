package com.turingteam.domain.wechat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatMessageRequest {
    private String template_id;

    private String page;

    private String touser;

    private String miniprogram_state;

    private String lang;

    private WeChatMessageData data;
}
