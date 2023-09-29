package com.turingteam.controller;

import com.turingteam.common.BaseContext;
import com.turingteam.common.ResponseResult;
import com.turingteam.domain.User;
import com.turingteam.service.UserService;
import com.turingteam.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "用户管理", description = "用户管理")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param user 用户信息
     * @return 注册结果
     */
    @Operation(summary = "注册")
    @Parameters({
            @Parameter(name = "className", description = "用户班级", required = true),
            @Parameter(name = "name", description = "用户姓名", required = true),
    })
    @PostMapping("/register")
    public ResponseResult<Object> register(@RequestBody User user) {
        user.setId(null);
        user.setPermission(0);
        userService.save(user);
        return ResponseResult.success("注册成功");
    }

    /**
     * 登录
     * @param id 用户id
     * @return 登录结果
     */
    @Operation(summary = "登录")
    @Parameter(name = "id", description = "用户id", required = true)
    @GetMapping("/login")
    public ResponseResult<Map<String, String>> login(Integer id) {
        String token = JwtUtil.generateToken(String.valueOf(id));
        return ResponseResult.success(Map.of("token", token));
    }

    /**
     * 获取用户信息
     * @param url 头像url
     * @return 用户信息
     */
    @Operation(summary = "修改头像")
    @Parameter(name = "url", description = "头像url", required = true)
    @PutMapping("/updateAvatar")
    public ResponseResult<Object> updateAvatar(String url) {
        Integer userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);
        user.setAvatarUrl(url);
        userService.updateById(user);
        return ResponseResult.success("修改成功");
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/getUserInfo")
    public ResponseResult<User> getUserInfo() {
        Integer userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);
        return ResponseResult.success(user);
    }

    // TODO 管理层：管理用户
}
