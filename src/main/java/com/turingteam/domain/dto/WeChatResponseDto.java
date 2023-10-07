package com.turingteam.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatResponseDto {

    private String openid;

    private String session_key;

    private String unionid;

    private int errcode;

    private String errmsg;
}
