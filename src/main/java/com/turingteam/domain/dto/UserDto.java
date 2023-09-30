package com.turingteam.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "User", description = "用户信息")
public class UserDto {
    @Schema(name = "id", description = "微信提供的用户唯一id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "id不能为空")
    private String id;

    @Schema(name = "name", description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Schema(name = "className", description = "班级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "班级不能为空")
    private String className;

}
