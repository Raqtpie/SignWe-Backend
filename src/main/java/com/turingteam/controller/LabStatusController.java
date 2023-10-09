package com.turingteam.controller;

import com.turingteam.common.BaseContext;
import com.turingteam.common.ResponseResult;
import com.turingteam.domain.LabStatus;
import com.turingteam.service.LabStatusService;
import com.turingteam.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "实验室状态管理", description = "实验室状态管理")
@RestController
public class LabStatusController {
    @Autowired
    private LabStatusService labStatusService;

    @Autowired
    private UserService userService;

    /**
     * 获取实验室状态
     * @return 实验室状态
     */
    @Operation(summary = "获取实验室状态")
    @GetMapping("/getLabStatus")
    public ResponseResult<LabStatus> getLabStatus() {
        LabStatus labStatus = labStatusService.getById(1);
        return ResponseResult.success(labStatus);
    }

    @Operation(summary = "开放实验室")
    @Parameter(name = "Authorization", description = "Token", in = ParameterIn.HEADER, schema = @Schema(type = "string"), required = true)
    @PutMapping("/openLab")
    public ResponseResult<LabStatus> openLab() {
        LabStatus labStatus = labStatusService.getById(1);
        labStatus.setStatus(true);
        labStatus.setOperator(userService.getById(BaseContext.getCurrentId()).getName());
        labStatusService.updateById(labStatus);
        return ResponseResult.success(labStatus, "开放实验室成功");
    }

    @Operation(summary = "关闭实验室")
    @Parameter(name = "Authorization", description = "Token", in = ParameterIn.HEADER, schema = @Schema(type = "string"), required = true)
    @PutMapping("/closeLab")
    public ResponseResult<LabStatus> closeLab() {
        LabStatus labStatus = labStatusService.getById(1);
        labStatus.setStatus(false);
        labStatus.setOperator(userService.getById(BaseContext.getCurrentId()).getName());
        labStatusService.updateById(labStatus);
        return ResponseResult.success(labStatus, "关闭实验室成功");
    }
}
