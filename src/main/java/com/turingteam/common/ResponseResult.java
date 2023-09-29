package com.turingteam.common;

import com.turingteam.constants.ResponseResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 通用的返回结果
 * @author Franz Li
 */
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ResponseResult", description = "通用的返回结果")
public class ResponseResult<T> {

    @Schema(description = "状态码")
    Integer code;

    @Schema(description = "消息")
    String msg;

    @Schema(description = "数据")
    T data;

    public static <T> ResponseResult<T> success(T data) {
        return ResponseResult.<T>builder()
                .code(ResponseResultCode.SUCCESS.code)
                .data(data)
                .build();
    }

    public static <T> ResponseResult<T> success(String msg) {
        return ResponseResult.<T>builder()
                .code(ResponseResultCode.SUCCESS.code)
                .msg(msg)
                .build();
    }

    public static <T> ResponseResult<T> success(T data,String msg) {
        return ResponseResult.<T>builder()
                .code(ResponseResultCode.SUCCESS.code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static <T> ResponseResult<T> badRequest() {
        return ResponseResult.<T>builder()
                .code(ResponseResultCode.BAD_REQUEST.code)
                .msg("bad request")
                .build();
    }

    public static <T> ResponseResult<T> badRequest(String msg) {
        return ResponseResult.<T>builder()
                .code(ResponseResultCode.BAD_REQUEST.code)
                .msg(msg)
                .build();
    }

    public static <T> ResponseResult<T> unauthorized(String msg) {
        return ResponseResult.<T>builder()
                .code(ResponseResultCode.UNAUTHORIZED.code)
                .msg(msg)
                .build();
    }

    public static <T> ResponseResult<T> unauthorized(String msg, T data) {
        return ResponseResult.<T>builder()
                .code(ResponseResultCode.UNAUTHORIZED.code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static <T> ResponseResult<T> fail() {
        return ResponseResult.<T>builder()
                .code(ResponseResultCode.ERROR.code)
                .msg("fail")
                .build();
    }

    public static <T> ResponseResult<T> fail(Throwable e) {
        return ResponseResult.<T>builder()
                .code(ResponseResultCode.ERROR.code)
                .msg(e.getMessage())
                .build();
    }

    public static <T> ResponseResult<T> fail(String msg) {
        return ResponseResult.<T>builder()
                .code(ResponseResultCode.ERROR.code)
                .msg(msg)
                .build();
    }

}

