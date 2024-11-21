package com.linyilinyi.gateway.filter;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.linyilinyi.common.utils.AuthContextUser;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 全局Filter，统一处理会员登录与外部不允许访问的服务
 * </p>
 *
 */


@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("【"+now+"】"+"---> 服务调用请求："+exchange.getRequest().getURI());
       // ServerHttpRequest request = exchange.getRequest();
        // 获取所有的Cookies
       // MultiValueMap<String, HttpCookie> cookies = request.getCookies();
//        String satoken = cookies.get("satoken").get(0).getValue();
//        Integer i = (Integer) redisTemplate.opsForValue().get("satoken:login:token:" + satoken);
//        AuthContextUser.setUserId(i);
//        if (i == null) {
//          throw new RuntimeException("未登录");
//        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 过滤器执行顺序，值越小，优先级越高
        return 0;
    }
}