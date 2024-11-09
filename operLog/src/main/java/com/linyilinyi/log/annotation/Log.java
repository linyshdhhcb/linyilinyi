package com.linyilinyi.log.annotation;

import com.linyilinyi.common.enums.BusinessType;

import java.lang.annotation.*;

/**
 * @Description 自定义登录操作日志记录注解
 * @Author linyi
 * @Date 2024/11/7
 * @ClassName: Log
 */
//Target注解决定 MyLog 注解可以加在哪些成分上，如加在类身上，或者属性身上，或者方法身上等成分
@Target({ElementType.PARAMETER,ElementType.METHOD})
//Retention注解括号中的"RetentionPolicy.RUNTIME"意思是让 记录日志 这个注解的生命周期一直程序运行时都存在
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    public String title() default "";

    /**
     * 功能
     */
    public String content() default "";
}
