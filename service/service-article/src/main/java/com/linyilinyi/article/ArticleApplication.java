package com.linyilinyi.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/20
 * @ClassName: ArticleApplication
 */
@SpringBootApplication(scanBasePackages = {"com.linyilinyi.common.exception", "com.linyilinyi.common.config","com.linyilinyi.article"})
@EnableDiscoveryClient
@EnableFeignClients("com.linyilinyi")
@MapperScan("com.linyilinyi.article.mapper")
public class ArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
    }
}
