package com.linyilinyi.gateway.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.linyilinyi.common.exception.LinyiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LinyiException.class)
    public ResponseEntity<String> handleLinyiException(LinyiException ex) {
        // 记录详细的错误信息
        log.error("发生 LinyiException，类型={}", ex.getClass().getName(), ex);
        // 返回友好的错误响应
        return new ResponseEntity<>("发生内部错误，请联系管理员", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotLoginException.class)
    public ResponseEntity<String> handleNotLoginException(NotLoginException ex) {
        // 记录详细的错误信息
        log.error("用户未登录，拦截请求", ex);
        // 返回未登录的提示信息
        return new ResponseEntity<>("用户未登录，请先登录", HttpStatus.UNAUTHORIZED);
    }
}
