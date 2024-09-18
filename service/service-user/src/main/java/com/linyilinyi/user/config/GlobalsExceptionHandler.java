package com.linyilinyi.user.config;

import com.linyilinyi.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalsExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleValidationExceptions(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        String defaultMessage = exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return Result.fail(defaultMessage);
    }
}