package com.linyilinyi.gateway.filter;

import com.linyilinyi.common.utils.AuthContextUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 全局Filter，统一处理会员登录与外部不允许访问的服务
 * </p>
 */

@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String AUTH_PATH = "/log/add";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("【{}】---> 服务调用请求：{}", now, exchange.getRequest().getURI());
        try {
            String string = exchange.getRequest().getURI().toString();
            boolean b = string.contains(AUTH_PATH);
            log.info("调用线程{}", Thread.currentThread().getName());
            // 获取 satoken Cookie
            List<HttpCookie> satokenCookies = exchange.getRequest().getCookies().get("satoken");
            if (!b) {
                if (satokenCookies == null || satokenCookies.isEmpty()) {
                    log.warn("未找到Cookie的satoken");
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    log.info("直接返回 Mono.empty()，终止请求处理");
                    return Mono.empty();  // 未找到satoken时，直接返回，避免继续调用过滤器
                }
                // 获取 satoken 值
                String satokenValue = satokenCookies.get(0).getValue();
                // 从 Redis 获取 userId
                Object userId = redisTemplate.opsForValue().get("satoken:login:token:" + satokenValue);
                if (userId == null) {
                    log.warn("Redis 未找到对应的 userId，satoken: {}", satokenValue);
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    log.info("Redis未找到userId，直接返回 Mono.empty()，终止请求处理");
                    return Mono.empty();  // Redis未找到userId时，直接返回，避免继续调用过滤器
                }
                // 添加 userId 到请求头
                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("USERID", userId.toString())
                        .build();
                ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                return chain.filter(modifiedExchange);  // 正常情况下，继续执行过滤器链
            }
            if (b) {
                return chain.filter(exchange);
            }
        } catch (RedisConnectionFailureException e) {
            log.error("Redis 连接失败: {}", e.getMessage(), e);
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("处理Token时发生异常：{}", e.getMessage(), e);
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return Mono.empty();  // 如果出现异常，不再继续调用过滤器链
    }

    @Override
    public int getOrder() {
        // 过滤器执行顺序，值越小，优先级越高
        return 0;
    }
}
