package com.linyilinyi.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import feign.codec.DecodeException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Response;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
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
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * 自定义异常处理方法
     *
     * @param e
     * @return
     */
    @ExceptionHandler(LinyiException.class)
    @ResponseBody
    public Result error(LinyiException e) {
        e.printStackTrace();
        return Result.success(null, e.getCode(), e.getMessage());
    }

    @ExceptionHandler(DecodeException.class)
    @ResponseBody
    public Result error(DecodeException e) {
        e.printStackTrace();
        return Result.success(null, e.status(), e.getMessage());
    }

    /**
     * 拦截非法参数异常并返回相应错误结果
     *
     * 此方法使用Spring的异常处理机制来捕获IllegalArgumentException类型的异常，
     * 并返回一个包含错误信息的响应体主要用于处理参数校验失败的情况
     *
     * @param e 异常对象，用于获取异常信息并记录日志
     * @return 返回一个表示参数验证错误的结果对象
     */
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public Result llegalArgumentException(Exception e) {
        // 打印异常堆栈跟踪信息，便于开发者调试
        e.printStackTrace();
        // 记录错误日志，包括异常消息和详细的异常信息
        log.error("触发异常拦截: " + e.getMessage(), e);
        // 返回成功结果，但数据为空，状态码为参数验证错误
        return Result.success(null, ResultCodeEnum.ARGUMENT_VALID_ERROR);
    }

    /**
     * 处理数据绑定异常的全局异常处理器方法
     * 当控制器类中的方法参数使用@Valid注解进行校验失败时，会抛出BindException
     * 本方法负责捕获此类异常，并返回统一的错误信息响应
     *
     * @param exception 绑定异常对象，用于获取校验失败的详细信息
     * @return 返回包含错误信息的Result对象
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result error(BindException exception) {
        // 获取校验结果对象
        BindingResult result = exception.getBindingResult();
        // 创建一个错误信息的Map对象，用于存储字段名和对应的错误信息
        Map<String, Object> errorMap = new HashMap<>();
        // 获取所有校验失败的字段错误信息
        List<FieldError> fieldErrors = result.getFieldErrors();
        // 遍历所有字段错误信息，记录日志并填充错误信息Map
        fieldErrors.forEach(error -> {
            // 记录错误日志，包括字段名和错误信息
            log.error("field: " + error.getField() + ", msg:" + error.getDefaultMessage());
            // 将字段名和错误信息放入Map中
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        // 返回包含错误信息的Result对象，设置结果为成功，数据为错误信息Map，状态码为参数校验错误
        return Result.success(errorMap, ResultCodeEnum.ARGUMENT_VALID_ERROR);
    }

    /**
     * 处理方法参数验证异常
     * 当方法参数验证失败时，此方法将被调用以处理验证异常
     *
     * @param exception 方法参数验证异常对象，用于获取验证错误信息
     * @return 返回包含验证错误信息的Result对象
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleValidationExceptions(MethodArgumentNotValidException exception) {
        // 打印异常堆栈跟踪信息，用于调试和日志记录
        exception.printStackTrace();
        // 获取第一个字段错误的默认消息，这里假设至少存在一个字段错误
        String defaultMessage = exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        // 返回验证错误信息，通知客户端
        return Result.fail(400,defaultMessage);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<Result> handleValidationExceptions(MethodArgumentTypeMismatchException exception) {
        exception.printStackTrace();
        // 返回友好的错误信息
        Result result = new Result(HttpStatus.BAD_REQUEST.value(), "参数类型不匹配");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result handleConstraintViolationException(ConstraintViolationException ex) {
        int i = ex.getMessage().lastIndexOf(" ");
        return Result.fail(400,ex.getMessage().substring(i + 1));

    }

    /**
     * 处理HTTP消息不可读异常
     * 当客户端发送的请求消息无法被服务器读取时，会抛出HttpMessageNotReadableException异常
     * 本方法用于捕获该异常并返回统一的错误响应
     *
     * @param ex HttpMessageNotReadableException实例，包含异常信息
     * @return 返回一个包含错误信息的Result对象
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public Result<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return Result.success(ex.getMessage(), ResultCodeEnum.DATA_JSON_ERROR);
    }

    /**
     * 处理所有数据访问异常
     *
     * 此方法捕获所有继承自DataAccessException的异常，并返回一个包含错误信息的响应体
     * 主要用于处理数据库操作中的各种错误
     *
     * @param e 数据访问异常对象，用于获取异常信息并记录日志
     * @return 返回一个包含错误信息的响应体
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public Result<String> handleDataAccessException(DataAccessException e) {
        // 打印异常堆栈跟踪信息，便于开发者调试
        e.printStackTrace();
        // 记录错误日志，包括异常消息和详细的异常信息
        log.error("数据访问异常: " + e.getMessage(), e);
        // 返回包含错误信息的响应体
        return Result.success(e.getMessage(), ResultCodeEnum.DATABASE_ERROR);
    }

    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public Result<String> handleNotLoginException(NotLoginException ex) {
        // 记录详细的错误信息
        log.error("用户未登录，拦截请求", ex);
        // 返回未登录的提示信息
        return new Result<>(401,"用户未登录，请先登录");
    }
}
