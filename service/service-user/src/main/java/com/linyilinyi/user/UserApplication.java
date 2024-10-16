package com.linyilinyi.user;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/14
 * @ClassName: UserApplication
 */
@SpringBootApplication(scanBasePackages = {"com.linyilinyi.common.exception", "com.linyilinyi.common.config","com.linyilinyi.user"})
@EnableDiscoveryClient
@EnableFeignClients("com.linyilinyi")
@MapperScan("com.linyilinyi.user.mapper")
@EnableCaching
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
