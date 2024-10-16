package com.linyilinyi.gateway.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 注册 Sa-Token全局过滤器
 * @Author linyi
 * @Date 2024/9/13
 * @ClassName: SaTokenConfigure
 */
@Slf4j
@Configuration
public class SaTokenConfigure {

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")    /* 拦截全部path */
                // 开放地址
                .addExclude("/favicon.ico")
                .addExclude("/user/login")
                .addExclude("/user/doLogin")
                .addExclude("/user/register")
                .addExclude("/user/logout")
                .addExclude("/user/getUserPermissions")
                //swagger和knife4j
                .addExclude("/swagger-ui/**")
                .addExclude("/swagger-resources/**")
                .addExclude("/v3/**")
                .addExclude("/webjars/**")
                .addExclude("/doc.html")
                //gateway聚合API接口文档
                .addExclude("/lyly/**")

                // 鉴权方法：每次访问进入
                .setAuth(obj -> {
                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                    SaRouter.match("/**", "/user/doLogin", r -> StpUtil.checkLogin());

                    // 权限认证 -- 不同模块, 校验不同权限
                    SaRouter.match("/**/*", r -> StpUtil.checkPermission("admin"));
                    SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
                })
                // 异常处理方法：每次setAuth函数出现异常时进入
                .setError(e -> {
                    return SaResult.error(e.getMessage());
                })
                .setBeforeAuth(r -> {
                    // ---------- 设置一些安全响应头 ----------
                    SaHolder.getResponse()
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*")
                    ;
                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(s -> log.info("*******OPTIONS预检请求，不做处理"))
                            .back();
                });
    }

    /**
     * 由于网关没有引入springMVC依赖，所以使用feign的时候需要手动装配messageConverters
     * @param converters
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }
}
