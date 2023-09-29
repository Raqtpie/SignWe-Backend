package com.turingteam.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "User", description = "用户信息")
public class UserDto {

    @Schema(name = "id", description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(name = "name", description = "班级", requiredMode = Schema.RequiredMode.REQUIRED)
    private String className;

}
