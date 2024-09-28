package com.linyilinyi.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/28
 * @ClassName: AuthApplication
 */


@SpringBootApplication(scanBasePackages = {"com.linyilinyi.common.exception", "com.linyilinyi.common.config","com.linyilinyi.auth"})
@EnableDiscoveryClient
@EnableFeignClients("com.linyilinyi")
public class AuthApplication {
    public static void main(String[] args) {

        SpringApplication.run(AuthApplication.class, args);
    }
}
