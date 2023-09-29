package com.turingteam.common;

import com.turingteam.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {
    /**
     * 异常处理方法
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler(CustomException.class)
    public ResponseResult<String> exceptionHandler(CustomException e) {
        log.error(e.getMessage());
        return ResponseResult.badRequest(e.getMessage());
    }

    /**
     * 未授权异常处理方法
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResponseResult<Map<String, String>> exceptionHandler(AuthorizationException e) {
        log.error(e.getMessage());
        String subject;
        try {
            subject = String.valueOf(BaseContext.getCurrentId());
        } catch (Exception ex) {
            return ResponseResult.unauthorized(e.getMessage());
        }
        String token = JwtUtil.generateToken(subject);
        return ResponseResult.unauthorized(e.getMessage(), Map.of("token", token));
    }

    /**
     * 未知异常处理方法
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<String> exceptionHandler(Exception e) {
        log.error(e.getMessage());
        return ResponseResult.fail("服务器发生异常，请联系管理员");
    }
}
