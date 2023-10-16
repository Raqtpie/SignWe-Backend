package com.turingteam.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.turingteam.common.AuthorizationException;
import com.turingteam.common.BaseContext;
import com.turingteam.common.CustomException;
import com.turingteam.common.ResponseResult;
import com.turingteam.domain.Chair;
import com.turingteam.domain.LabStatus;
import com.turingteam.service.ChairService;
import com.turingteam.service.LabStatusService;
import com.turingteam.utils.JwtUtil;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "座位管理", description = "座位管理")
@Validated
public class ChairController {
    @Autowired
    private ChairService chairService;

    @Autowired
    private LabStatusService labStatusService;

    /**
     * 获取座位信息
     * @return 座位信息
     */
    @Operation(summary = "获取座位信息")
    @GetMapping("/getChair")
    public ResponseResult<List<Chair>> getChair() {
        List<Chair> chairList = chairService.list();
        chairList.forEach(chair -> chair.setUserId(null));
        return ResponseResult.success(chairList);
    }

    /**
     * 根据id获取座位信息
     * @return 座位信息
     */
    @Operation(summary = "根据id获取座位信息")
    @Parameters({
            @Parameter(name = "id", description = "座位id", required = true),
            @Parameter(name = "Authorization", description = "Token", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    })
    @GetMapping("/getChairById")
    public ResponseResult<Chair> getChairById(@NotNull(message = "id不能为空") Integer id, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && StringUtils.isBlank(authorization)) {
            throw new AuthorizationException("传入Authorization的情况下，Token不能为空");
        }
        String userId = null;
        if (authorization != null) {
            userId = JwtUtil.extractSubject(authorization);
            if (!JwtUtil.isTokenEffective(authorization) || JwtUtil.isTokenExpired(authorization)) {
                userId = null;
            }
        }
        Chair chair = chairService.getChairById(id, userId);
        chair.setUserId(null);
        return ResponseResult.success(chair);
    }

    /**
     * 签到
     * @param chairId 座位id
     * @return 签到结果
     */
    @Operation(summary = "签到")
    @Parameters({
            @Parameter(name = "chairId", description = "座位id", required = true),
            @Parameter(name = "Authorization", description = "Token", in = ParameterIn.HEADER, schema = @Schema(type = "string"), required = true)
    })
    @PutMapping("/work")
    public ResponseResult<Object> work(@NotNull(message = "chairId不能为空") Integer chairId) {
        LabStatus labStatus = labStatusService.getById(1);
        if (!labStatus.getStatus()) {
            throw new CustomException("实验室未开放");
        }
        String userId = BaseContext.getCurrentId();
        Chair chair = chairService.getById(chairId);
        if (userId.equals(chair.getUserId()) && chair.getStatus()) {
            throw new CustomException("不能重复签到");
        }
        if (chair.getStatus()) {
            throw new CustomException("该座位已被占用");
        }
        LambdaQueryWrapper<Chair> chairLambdaQueryWrapper = new LambdaQueryWrapper<>();
        chairLambdaQueryWrapper.eq(Chair::getUserId, userId);
        chairLambdaQueryWrapper.eq(Chair::getStatus, true);
        if (chairService.getOne(chairLambdaQueryWrapper) != null) {
            throw new CustomException("不能重复签到");
        }
        chair.setStatus(true);
        chair.setTime(System.currentTimeMillis());
        chair.setUserId(userId);
        chairService.updateById(chair);
        return ResponseResult.success("签到成功");
    }

    /**
     * 签退
     * @param chairId 座位id
     * @return 签退结果
     */
    @Operation(summary = "签退")
    @Parameters({
            @Parameter(name = "chairId", description = "座位id", required = true),
            @Parameter(name = "Authorization", description = "Token", in = ParameterIn.HEADER, schema = @Schema(type = "string"), required = true)
    })
    @PutMapping("/life")
    public ResponseResult<Object> life(@NotNull(message = "chairId不能为空") Integer chairId) {
        String userId = BaseContext.getCurrentId();
        chairService.life(chairId, userId);
        return ResponseResult.success("签退成功");
    }

    // TODO 管理层：新增/删除/修改座位信息
}
