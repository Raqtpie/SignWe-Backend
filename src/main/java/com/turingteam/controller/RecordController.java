package com.turingteam.controller;

import com.turingteam.common.BaseContext;
import com.turingteam.common.ResponseResult;
import com.turingteam.domain.Record;
import com.turingteam.domain.UserRecord;
import com.turingteam.domain.dto.RecordDto;
import com.turingteam.service.RecordService;
import com.turingteam.service.UserRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "记录管理", description = "记录管理")
@RestController
public class RecordController {
    @Autowired
    private RecordService recordService;

    @Autowired
    private UserRecordService userRecordService;

    /**
     * 获取排行榜
     * @return 排行榜
     */
    @Operation(summary = "获取排行榜")
    @GetMapping("/getRecord")
    public ResponseResult<List<RecordDto>> getRecord() {
        List<RecordDto> userRecordList = userRecordService.getList();
        return ResponseResult.success(userRecordList);
    }

    /**
     * 根据用户id获取记录
     * @return 记录列表（时间降序）
     */
    @Operation(summary = "根据用户id获取记录")
    @Parameter(
            name = "Authorization",
            description = "Token",
            in = ParameterIn.HEADER,
            schema = @Schema(type = "string")
    )
    @GetMapping("/getRecordByUserId")
    public ResponseResult<List<Record>> getRecordByUserId() {
        Integer userId = BaseContext.getCurrentId();
        List<Record> recordList = recordService.getRecordByUserId(userId);
        return ResponseResult.success(recordList);
    }

    /**
     * 根据用户id获取总时间
     * @return 总时间
     */
    @Operation(summary = "根据用户id获取总时间")
    @Parameter(
            name = "Authorization",
            description = "Token",
            in = ParameterIn.HEADER,
            schema = @Schema(type = "string")
    )
    @GetMapping("/getTotalTimeByUserId")
    public ResponseResult<UserRecord> getTotalTimeByUserId() {
        Integer userId = BaseContext.getCurrentId();
        UserRecord userRecord = userRecordService.getTotalTimeByUserId(userId);
        return ResponseResult.success(userRecord);
    }

    /**
     * 获取昨日记录
     * @return 昨日记录
     */
    @Operation(summary = "获取昨日记录")
    @GetMapping("/getRecordYesterday")
    public ResponseResult<List<RecordDto>> getRecordYesterday() {
        List<RecordDto> recordDtoList = userRecordService.getRecordYesterday();
        return ResponseResult.success(recordDtoList);
    }

    // TODO 管理层：删除特定id当天记录，强制签退某个座位
}