package com.linyilinyi.common.exception;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import feign.codec.DecodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 全局异常处理器
 * @Author linyi
 * @Date 2024/9/13
 * @ClassName: GlobalExceptionHandler
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * 自定义异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(LinyiException.class)
    @ResponseBody
    public Result error(LinyiException e){
        e.printStackTrace();
        return Result.success(null,e.getCode(), e.getMessage());
    }

    @ExceptionHandler(DecodeException.class)
    @ResponseBody
    public Result error(DecodeException e){
        e.printStackTrace();
        return Result.success(null,e.status(),  e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public Result llegalArgumentException(Exception e) {
        e.printStackTrace();
        log.error("触发异常拦截: " + e.getMessage(), e);
        return Result.success(null, ResultCodeEnum.ARGUMENT_VALID_ERROR);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result error(BindException exception) {
        BindingResult result = exception.getBindingResult();
        Map<String, Object> errorMap = new HashMap<>();
        List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(error -> {
            log.error("field: " + error.getField() + ", msg:" + error.getDefaultMessage());
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return Result.success(errorMap, ResultCodeEnum.ARGUMENT_VALID_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleValidationExceptions(MethodArgumentNotValidException exception) {
       exception.printStackTrace();
        String defaultMessage = exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return Result.fail(defaultMessage);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public Result<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return Result.success(ex.getMessage(),ResultCodeEnum.DATA_JSON_ERROR);
    }

}
