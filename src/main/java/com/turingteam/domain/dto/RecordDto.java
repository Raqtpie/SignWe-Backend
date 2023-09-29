package com.turingteam.domain.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Hidden
public class RecordDto {
    private Integer userId;

    private String username;

    private String className;

    private Integer totalTime;
}
