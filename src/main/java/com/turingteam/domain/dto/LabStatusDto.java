package com.turingteam.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "LabStatus", description = "更改实验室状态返回类")
public class LabStatusDto {
    @Schema(name = "status", description = "实验室状态")
    private Boolean status;

    @Schema(name = "operator", description = "操作人")
    private String operator;
}
