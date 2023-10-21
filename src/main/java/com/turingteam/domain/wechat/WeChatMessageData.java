package com.turingteam.domain.wechat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatMessageData {
    private Map<String, String> thing5;

    private Map<String, String> date2;
}
