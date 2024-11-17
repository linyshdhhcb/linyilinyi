package com.linyilinyi.log.aspect;

import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.common.utils.IpUtil;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.log.OperLog;
import com.linyilinyi.system.client.SystemClient;
import com.linyilinyi.user.client.UserClient;
import jakarta.annotation.Resource;//package com.linyilinyi.log.aspect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Map;


import com.alibaba.fastjson.JSON;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.log.OperLog;
import com.linyilinyi.common.utils.IpUtil;
import com.linyilinyi.system.client.SystemClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/7
 * @ClassName: LogAspect
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    private SystemClient systemClient;

    @Resource
    private UserClient userClient;


    //记录操作执行时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@annotation(com.linyilinyi.log.annotation.Log)")
    public void operLogPoinCut() {
    }

    @Before("operLogPoinCut()")
    public void beforBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
    }

    @Pointcut("execution(* com.linyilinyi.*.controller.*Controller.*(..))")
    public void operExceptionLogPoinCut() {
    }

    @AfterReturning(value = "operLogPoinCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        long l = System.currentTimeMillis();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        try {
            //从切面入点通过放射机制获取织入点的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取方法
            Method method = signature.getMethod();
            //获取操作
            Log log = method.getAnnotation(Log.class);

            OperLog operLog = new OperLog();

            if (Optional.ofNullable(operLog).isPresent()) {
                operLog.setTitle(log.title());
                operLog.setContent(log.content());
            }
            //将入参转成json
            String jsonString = JSON.toJSONString(joinPoint.getArgs());
            //获取请求类名
            String className = joinPoint.getTarget().getClass().getName();
            //获取请求方法名
            String name = method.getName();
            String methoname = className + "." + name + "()";
            //获取ip
            String ip = IpUtil.getIpAddress(request);

            //获取用户名
            Integer userId = AuthContextUser.getUserId();

            String username = CompletableFuture.supplyAsync(()->{
                return userClient.getUserById(userId).getData().getUsername();
            }).get();
//            String username = userClient.getUserById(userId).getData().getUsername();
            operLog.setMethod(methoname); //设置请求方法
            operLog.setRequestMethod(request.getMethod());//设置请求方式
            operLog.setRequestParam(jsonString); // 请求参数
            operLog.setResponseResult(JSON.toJSONString(result)); // 返回结果
            operLog.setOperName(username);
            operLog.setIp(ip); // IP地址
            operLog.setIpLocation(IpUtil.address(ip)); // IP归属地（真是环境中可以调用第三方API根据IP地址，查询归属地）
            operLog.setRequestUrl(request.getRequestURI()); // 请求URI
            operLog.setOperTime(LocalDateTime.now()); // 时间
            operLog.setStatus(0);//操作状态（0正常 1异常）
            Long takeTime = System.currentTimeMillis() - startTime.get();//记录方法执行耗时时间（单位：毫秒）
            operLog.setTakeTime(takeTime);
            //异步调用
            CompletableFuture.runAsync(()->{
                systemClient.operLog(operLog);
            });
//            systemClient.operLog(operLog);
        } catch (Exception e) {
            throw new LinyiException("日志记录出错");
        }finally {

            startTime.remove();
        }
    }

    @AfterThrowing(pointcut = "operExceptionLogPoinCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        try {
            //从切面入点通过放射机制获取织入点的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取方法
            Method method = signature.getMethod();
            //获取操作
            Log log = method.getAnnotation(Log.class);

            OperLog operLog = new OperLog();
            if (Optional.ofNullable(operLog).isPresent()) {
                operLog.setTitle(log.title());
                operLog.setContent(log.content());
            }
            //将入参转成json
            String jsonString = JSON.toJSONString(joinPoint.getArgs());
            //获取请求类名
            String className = joinPoint.getTarget().getClass().getName();
            //获取请求方法名
            String name = method.getName();
            String methoname = className + "." + name + "()";
            //获取ip
            String ip = IpUtil.getIpAddress(request);

            //获取用户名
            Integer userId = AuthContextUser.getUserId();
            String username = userClient.getUserById(userId).getData().getUsername();

            operLog.setMethod(methoname); //设置请求方法
            operLog.setRequestMethod(request.getMethod());//设置请求方式
            operLog.setRequestParam(jsonString); // 请求参数
            operLog.setOperName(username);
            operLog.setIp(ip); // IP地址
            operLog.setIpLocation(IpUtil.address(ip)); // IP归属地（真是环境中可以调用第三方API根据IP地址，查询归属地）
            operLog.setRequestUrl(request.getRequestURI()); // 请求URI
            operLog.setOperTime(LocalDateTime.now()); // 时间
            operLog.setStatus(1);//操作状态（0正常 1异常）
            Long takeTime = System.currentTimeMillis() - startTime.get();//记录方法执行耗时时间（单位：毫秒）
            operLog.setTakeTime(takeTime);
            operLog.setErrorMsg(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
            systemClient.operLog(operLog);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 转换异常信息为字符串
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        message = substring(message, 0, 2000);
        return message;
    }

    //字符串截取
    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        } else {
            if (end < 0) {
                end += str.length();
            }

            if (start < 0) {
                start += str.length();
            }
            if (end > str.length()) {
                end = str.length();
            }
            if (start > end) {
                return "";
            } else {
                if (start < 0) {
                    start = 0;
                }
                if (end < 0) {
                    end = 0;
                }
                return str.substring(start, end);
            }
        }
    }

}

