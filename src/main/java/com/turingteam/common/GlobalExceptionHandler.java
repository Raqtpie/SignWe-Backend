package com.turingteam.common;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

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
        return ResponseResult.unauthorized(e.getMessage());
    }

    /**
     * 参数校验异常处理方法（Body参数）
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<String> exceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return ResponseResult.badRequest(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    /**
     * 参数校验异常处理方法（Query参数）
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<String> exceptionHandler(ConstraintViolationException e) {
        log.error(e.getMessage());
        return ResponseResult.badRequest(e.getMessage().split(":")[1].trim());
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
