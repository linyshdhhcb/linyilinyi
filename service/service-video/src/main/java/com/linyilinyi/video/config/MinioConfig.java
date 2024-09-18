package com.linyilinyi.video.config;

import io.minio.MinioClient;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author linyi
 * @Date 2024/5/14
 * @ClassName: MinioConfig
 */
@Component
public class MinioConfig {
//    /**
//     * URL
//     */
//    @Value("${minio.endpointUrl}")
//    private String endpoint;
//    /**
//     * 账号
//     */
//    @Value("${minio.accessKey}")
//    private String accessKey;
//    /**
//     * 密码
//     */
//    @Value("${minio.secretKey}")
//    private String secretKey;
    @Autowired
    private MinioVo minioVo;

    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint(minioVo.getEndpointUrl())
                        .credentials(minioVo.getAccessKey(), minioVo.getSecretKey())
                        .build();
        return minioClient;

//        @Bean
//        public MinioClient minioClient() {
//
//            MinioClient minioClient =
//                    MinioClient.builder()
//                            .endpoint(endpoint)
//                            .credentials(accessKey, secretKey)
//                            .build();
//            return minioClient;
        /**
         * 官方提供的模板
         */
//        MinioClient minioClient =
//                MinioClient.builder()
//                        .endpoint("https://play.min.io")
//                        .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
//                        .build();
    }


}

