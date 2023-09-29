package com.turingteam.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_record")
public class UserRecord implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer userId;

    @Schema(description = "总时长", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer totalTime;
}
