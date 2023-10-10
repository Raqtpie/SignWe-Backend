package com.turingteam.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    private String userId;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private Boolean canOperate;
}
