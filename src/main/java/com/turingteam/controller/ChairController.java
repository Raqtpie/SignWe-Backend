package com.turingteam.controller;

import com.turingteam.common.BaseContext;
import com.turingteam.common.ResponseResult;
import com.turingteam.domain.Chair;
import com.turingteam.service.ChairService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "座位管理", description = "座位管理")
public class ChairController {
    @Autowired
    private ChairService chairService;

    /**
     * 获取座位信息
     * @return 座位信息
     */
    @Operation(summary = "获取座位信息")
    @GetMapping("/getChair")
    public ResponseResult<List<Chair>> getChair() {
        List<Chair> chairList = chairService.list();
        return ResponseResult.success(chairList);
    }

    /**
     * 根据id获取座位信息
     * @return 座位信息
     */
    @Operation(summary = "根据id获取座位信息")
    @GetMapping("/getChairById")
    public ResponseResult<Chair> getChairById(Integer id) {
        Chair chair = chairService.getById(id);
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
            @Parameter(name = "Authorization", description = "Token", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    })
    @PutMapping("/work")
    public ResponseResult<Object> work(Integer chairId) {
        Integer userId = BaseContext.getCurrentId();
        Chair chair = chairService.getById(chairId);
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
            @Parameter(name = "Authorization", description = "Token", in = ParameterIn.HEADER, schema = @Schema(type = "string"))
    })
    @PutMapping("/life")
    public ResponseResult<Object> life(Integer chairId) {
        int userId = BaseContext.getCurrentId();
        chairService.life(chairId, userId);
        return ResponseResult.success("签退成功");
    }

    // TODO 管理层：新增/删除/修改座位信息
}
