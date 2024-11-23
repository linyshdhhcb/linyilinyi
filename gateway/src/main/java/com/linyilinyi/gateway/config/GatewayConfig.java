package com.linyilinyi.gateway.config;

import com.linyilinyi.gateway.filter.AuthGlobalFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public GlobalFilter userIdGlobalFilter() {
        return new AuthGlobalFilter();
    }
}
