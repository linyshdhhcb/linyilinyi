package com.linyilinyi.video;

import com.linyilinyi.video.config.MinioVo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.text.MessageFormat;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/13
 * @ClassName: VideoApplication
 */

@SpringBootApplication(scanBasePackages = {"com.linyilinyi.common.exception", "com.linyilinyi.video"})
@EnableDiscoveryClient
@EnableFeignClients("com.linyilinyi")
@MapperScan("com.linyilinyi.video.mapper")
public class VideoApplication {
    public static void main(String[] args) {
       SpringApplication.run(VideoApplication.class, args);
    }
}
