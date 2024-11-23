package com.linyilinyi.gateway.config;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.converter.HttpMessageConverter;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class WebConfig {

    /**
     * 由于网关没有引入springMVC依赖，所以使用feign的时候需要手动装配messageConverters
     *
     * @param converters
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    /**
     * 配置并返回一个键解析器（KeyResolver），用于获取用户的唯一键（例如IP地址）
     * 此方法使用Spring框架的@Bean注解，表明该方法会创建并配置一个Bean对象（在这里是一个KeyResolver）
     * 这个Bean对象可以被Spring容器管理，并可以注入到其他需要的地方
     *
     * @return KeyResolver 一个功能为解析用户键的解析器，这里解析的是用户请求的远程地址（IP）
     */
    @Bean
    KeyResolver userKeyResolver() {
        // 返回一个KeyResolver，它是一个函数式接口，因此可以使用Lambda表达式
        // 这个Lambda表达式接收一个exchange（交换）对象，表示的是HTTP请求和响应的交换
        // 从exchange中获取请求对象，然后获取请求的远程地址（IP），作为用户键的值
        // 使用Mono.just包装这个值，使其成为一个立即发射该值的Mono对象
        // Objects.requireNonNull确保远程地址不为空，如果为空则抛出NullPointerException
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
    }
}