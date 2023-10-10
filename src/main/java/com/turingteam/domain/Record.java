package com.turingteam.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Record", description = "打卡记录")
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("record")
public class Record implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @Schema(description = "记录id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;

    @Schema(description = "打卡时长", requiredMode = Schema.RequiredMode.AUTO)
    private Integer time;

    @Schema(description = "座位id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer chairId;

    @Schema(description = "打卡日期", requiredMode = Schema.RequiredMode.AUTO)
    private LocalDate date;
}
