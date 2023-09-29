package com.turingteam.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer id;

    @Schema(description = "用户班级", requiredMode = Schema.RequiredMode.REQUIRED)
    private String className;

    @Schema(description = "用户姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "用户头像url", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String avatarUrl;

    @Schema(description = "用户权限", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer permission;

    @TableField(exist = false)
    @Schema(hidden = true)
    private String permissionString;
}
