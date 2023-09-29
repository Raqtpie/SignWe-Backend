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
@Schema(name = "Chair", description = "座位")
@TableName("chair")
public class Chair implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @Schema(description = "座位id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @Schema(description = "座位状态", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean status;

    @Schema(description = "座位签到时间", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long time;

    @Schema(description = "当前落座用户id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer userId;
}
