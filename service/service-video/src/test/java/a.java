import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.video.VideoApplication;
import com.linyilinyi.video.config.MinioVo;
import com.linyilinyi.video.service.VideoService;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/17
 * @ClassName: a
 */

@SpringBootTest(classes = VideoApplication.class)
@Slf4j
public class a {

    @Resource
    private VideoService videoService;
    @Resource
    private MinioVo minioVo;
    @Resource
    private MinioClient minioClient;
    @Test
    public void test(){
        Double minioFileSize = getMinioFileSize("chunk/8f11928d4f7bc9d752fffb49cee6dcf2/");
        System.out.println(minioFileSize);

        deleteMinioFile("chunk/8f11928d4f7bc9d752fffb49cee6dcf2/");
    }


    private Double getMinioFileSize(String object) {
        try {
            double sum = 0.0;
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(minioVo.getBucketName()).prefix(object).recursive(true).build());
            for (Result<Item> result : results) {
                System.out.println(result.get().lastModified() + "\t" + result.get().size() + "\t" + result.get().objectName());
                sum += result.get().size() / 1024.0;
            }
            return sum;
        } catch (Exception e) {
            throw new LinyiException("获取文件大小失败" + e.getMessage());
        }
    }

    private Boolean deleteMinioFile(String s) {
        try {
            minioClient.listObjects(ListObjectsArgs.builder().bucket(minioVo.getBucketName()).prefix(s).recursive(true).build()).forEach(item -> {
                try {
                    minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioVo.getBucketName()).object(item.get().objectName()).build());
                    log.info("**********************" + item.get());
                    log.info(item.get().objectName() + "删除minio分块成功");
                } catch (Exception e) {
                    throw new LinyiException("删除文件失败" + e.getMessage());
                }
            });
            return true;
        } catch (Exception e) {
            throw new LinyiException("删除文件失败" + e.getMessage());
        }
    }





}
