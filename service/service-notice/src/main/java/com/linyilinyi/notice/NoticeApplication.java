package com.linyilinyi.notice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.linyilinyi.common.exception", "com.linyilinyi.common.config","com.linyilinyi.notice"})
@EnableDiscoveryClient
@EnableFeignClients("com.linyilinyi")
@MapperScan("com.linyilinyi.notice.mapper")
public class NoticeApplication {
    public static void main(String[] args) {
       SpringApplication.run(NoticeApplication.class, args);
    }
}