package com.turingteam.controller;

import com.turingteam.common.AuthorizationException;
import com.turingteam.common.BaseContext;
import com.turingteam.common.ResponseResult;
import com.turingteam.domain.User;
import com.turingteam.domain.dto.UserDto;
import com.turingteam.service.UserService;
import com.turingteam.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "用户管理", description = "用户管理")
@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param userDto 用户信息
     * @return 注册结果
     */
    @Operation(summary = "注册")
    @PostMapping("/register")
    public ResponseResult<Object> register(@RequestBody @Validated UserDto userDto) {
        userService.register(userDto);
        return ResponseResult.success("注册成功");
    }

    /**
     * 登录
     * @param jsCode 微信提供的code
     * @return 登录结果
     */
    @Operation(summary = "登录")
    @Parameter(name = "jsCode", description = "登录时获取的code，可通过wx.login获取", required = true)
    @GetMapping("/login")
    public ResponseResult<Map<String, String>> login(@NotBlank(message = "jsCode不能为空") String jsCode) {
        String token = userService.login(jsCode);
        return ResponseResult.success(Map.of("token", token));
    }

    /**
     * 获取用户信息
     * @param url 头像url
     * @return 用户信息
     */
    @Operation(summary = "修改头像")
    @Parameters({
            @Parameter(name = "url", description = "头像url", required = true),
            @Parameter(name = "Authorization", description = "Token", in = ParameterIn.HEADER, schema = @Schema(type = "string"), required = true)
    })
    @PutMapping("/updateAvatar")
    public ResponseResult<Object> updateAvatar(@NotBlank(message = "url不能为空") String url) {
        String userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);
        user.setAvatarUrl(url);
        userService.updateById(user);
        return ResponseResult.success("修改成功");
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @Operation(summary = "获取用户信息")
    @Parameter(name = "Authorization", description = "Token", in = ParameterIn.HEADER, schema = @Schema(type = "string"), required = true)
    @GetMapping("/getUserInfo")
    public ResponseResult<User> getUserInfo() {
        String userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);
        if (user == null) {
            throw new AuthorizationException("token无效");
        }
        return ResponseResult.success(user);
    }

    /**
     * 更新token
     * @return token
     */
    @Operation(summary = "更新token")
    @Parameter(name = "Authorization", description = "Token", in = ParameterIn.HEADER, schema = @Schema(type = "string"), required = true)
    @PutMapping("/updateToken")
    public ResponseResult<Map<String, String>> updateToken() {
        String userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);
        if (user == null) {
            return ResponseResult.fail("token无效");
        }
        String token = JwtUtil.generateToken(String.valueOf(userId));
        return ResponseResult.success(Map.of("token", token));
    }

    // TODO 管理层：管理用户
}
