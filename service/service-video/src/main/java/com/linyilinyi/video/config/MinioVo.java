package com.linyilinyi.video.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/18
 * @ClassName: MinioVo
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioVo {

    private String endpointUrl;

    private String accessKey;

    private String secretKey;

    private String bucketName;



}
