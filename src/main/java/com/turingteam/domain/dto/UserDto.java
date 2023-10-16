package com.turingteam.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "User", description = "用户信息")
public class UserDto {
    @Schema(name = "jsCode", description = "登录时获取的 code，可通过wx.login获取", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "jsCode不能为空")
    private String jsCode;

    @Schema(name = "name", description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,8}$", message = "姓名格式错误")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Schema(name = "className", description = "班级", requiredMode = Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}12\\d{2,3}$", message = "班级格式错误")
    @NotBlank(message = "班级不能为空")
    private String className;

}
