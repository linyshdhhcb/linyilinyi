package com.linyilinyi.video.service.impl;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.common.utils.SnowflakeIdWorker;
import com.linyilinyi.model.entity.file.File;
import com.linyilinyi.model.vo.file.FileQueryVo;
import com.linyilinyi.video.config.MinioVo;
import com.linyilinyi.video.mapper.FileMapper;
import com.linyilinyi.video.service.FileService;
import io.minio.*;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Resource
    private FileMapper fileMapper;

    @Resource
    private MinioClient minioClient;

    @Autowired
    private MinioVo minioVo;

    @Resource
    private RedisTemplate redisTemplate;

    private String bucketName;


    @Override
    public File uploadVideoFile(File file, String absolutePath, String bucketNamea) {

        String mimeType = getMimeType(file.getFileType().replace(".", ""));
        LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<File>().eq(File::getFileMd5, fileMd5(new java.io.File(absolutePath)));
        File file1 = fileMapper.selectOne(queryWrapper);
        if (file1 != null) {
            return file1;
        }
        bucketName = minioVo.getBucketName();
        file.setBucket(bucketName);
        file.setUserId(AuthContextUser.getUserId());
        file.setCreateTime(LocalDateTime.now());
        String object = bucketNamea + getPathTime() + fileMd5(new java.io.File(absolutePath)) + file.getFileType();
        file.setFileName(fileMd5(new java.io.File(absolutePath)) + file.getFileType());
        file.setFilePath(object);
        file.setFileUrl(minioVo.getEndpointUrl() + "/" + minioVo.getBucketName() + "/" + object);
        file.setFileMd5(fileMd5(new java.io.File(absolutePath)));
        // TODO 2024/9/18 文件类型此处写死，之后要改
        file.setFileType("11002");
        //文件上传minio
        /**
         * .bucket("my-bucketname") 设置存储桶名称。
         * .object("my-objectname") 设置对象名称（即存储在 MinIO 中的路径【包括文件名称】）。
         * .filename("my-video.avi") 设置本地文件路径。
         * .contentType("video/mp4") 设置内容类型（例如视频格式）。
         */
        boolean isSuccess = addFilesToMinIO(bucketName, object, absolutePath, mimeType);
        if (!isSuccess) {
            throw new LinyiException(ResultCodeEnum.FAIL);
        }
        int insert = fileMapper.insert(file);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.FAIL_VIDEO);
        }
        return file;
    }

    @Override
    public File uploadImageFile(File file, String absolutePath, String bucketNamea) {
        String mimeType = getMimeType(file.getFileType().replace(".", ""));
        LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<File>().eq(File::getFileMd5, fileMd5(new java.io.File(absolutePath)));
        File file1 = fileMapper.selectOne(queryWrapper);
        if (Optional.ofNullable(file1).isPresent()) {
            return file1;
        }
        bucketName = minioVo.getBucketName();
        file.setBucket(bucketName);
        file.setUserId(AuthContextUser.getUserId());
        file.setCreateTime(LocalDateTime.now());
        String object = bucketNamea + getPathTime() + fileMd5(new java.io.File(absolutePath)) + file.getFileType();
        file.setFileName(fileMd5(new java.io.File(absolutePath)) + file.getFileType());
        file.setFilePath(object);
        file.setFileUrl(minioVo.getEndpointUrl() + "/" + minioVo.getBucketName() + "/" + object);
        file.setFileMd5(fileMd5(new java.io.File(absolutePath)));
        // TODO 2024/9/18 文件类型此处写死，之后要改
        file.setFileType("11001");
        //文件上传minio
        /**
         * .bucket("my-bucketname") 设置存储桶名称。
         * .object("my-objectname") 设置对象名称（即存储在 MinIO 中的路径【包括文件名称】）。
         * .filename("my-video.avi") 设置本地文件路径。
         * .contentType("video/mp4") 设置内容类型（例如视频格式）。
         */
        boolean isSuccess = addFilesToMinIO(bucketName, object, absolutePath, mimeType);
        if (!isSuccess) {
            throw new LinyiException(ResultCodeEnum.FAIL);
        }
        file.setId(new SnowflakeIdWorker(0, 0).nextId());
        int insert = fileMapper.insert(file);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.FAIL_VIDEO);
        }
        return file;
    }

    @Override
    public PageResult<File> uploadFileList(long pageNo, long pageSize, FileQueryVo fileQueryVo) {
        if (Optional.ofNullable(fileQueryVo).isEmpty()) {
            log.error("查询条件为空");
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
        LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(fileQueryVo.getFileName()), File::getFileName, fileQueryVo.getFileName());
        queryWrapper.eq(StringUtils.isNotBlank(fileQueryVo.getFileType()), File::getFileType, fileQueryVo.getFileType());
        queryWrapper.eq(StringUtils.isNotBlank(fileQueryVo.getBucket()), File::getBucket, fileQueryVo.getBucket());
        queryWrapper.like(StringUtils.isNotBlank(fileQueryVo.getFileUrl()), File::getFileUrl, fileQueryVo.getFileUrl());
        queryWrapper.like(StringUtils.isNotBlank(fileQueryVo.getFilePath()), File::getFilePath, fileQueryVo.getFilePath());
        queryWrapper.eq(StringUtils.isNotBlank(fileQueryVo.getFileMd5()), File::getFileMd5, fileQueryVo.getFileMd5());
        queryWrapper.eq(fileQueryVo.getUserId() != null, File::getUserId, fileQueryVo.getUserId());
        queryWrapper.eq(Optional.ofNullable(fileQueryVo.getStart()).isPresent(), File::getStart, fileQueryVo.getStart());
        //queryWrapper.eq(fileQueryVo.getStart() != null, File::getStart, fileQueryVo.getStart());
        queryWrapper.gt(fileQueryVo.getMin() != null, File::getFileSize, fileQueryVo.getMin());
        queryWrapper.lt(fileQueryVo.getMax() != null, File::getFileSize, fileQueryVo.getMax());
        queryWrapper.gt(fileQueryVo.getStartTime() != null, File::getCreateTime, fileQueryVo.getStartTime());
        queryWrapper.lt(fileQueryVo.getEndTime() != null, File::getCreateTime, fileQueryVo.getEndTime());
        Page<File> filePage = new Page<>(pageNo, pageSize);
        Page<File> page = fileMapper.selectPage(filePage, queryWrapper);
        log.info("分页查询结果：" + page);
        return new PageResult<>(page.getRecords(), page.getTotal(), pageNo, pageSize);
    }

    @Override
    public String isDeleteFile(Long id) {
        File byId = getById(id);
        int i = fileMapper.deleteById(byId);
        if (i != 1) {
            log.error("删除文件失败");
            throw new LinyiException(ResultCodeEnum.FAIL);
        }
        return "删除成功";
    }

    @Override
    public PageResult<File> getDeleteFileList(long pageNo, long pageSize, Long id) {
        Page<File> filePage = new Page<>(pageNo, pageSize);
        IPage<File> iPage = fileMapper.getDeleteFileList(filePage, id);
        return new PageResult<>(iPage.getRecords(), iPage.getTotal(), pageNo, pageSize);
    }

    @Override
    @Transactional
    public String deleteFiles(List<Long> ids) {
        int i = 0;
        File file = null;
        List<String> s = new ArrayList<>();
        for (Long id : ids) {
            List<File> items = getDeleteFileList(1, 1, id).getItems();
            file = items.size() == 0 ? getById(id) : items.get(0);
            if (file == null) {
                log.error("文件不存在，id为：{}", id);
                continue;
            }
            try {
                minioClient.removeObject(
                        RemoveObjectArgs.builder().bucket(file.getBucket()).object(file.getFilePath()).build());
                fileMapper.isDelete(id);
                log.info("删除文件{}成功", file.getFileName());
                s.add(file.getFileName());
                i++;
            } catch (Exception e) {
                log.error("删除文件{}失败，原因：{}", file.getFileName(), e.getMessage(), "删除中断");
            }
        }
        String result = i == ids.size() ? "全部" + ids.size() + "条数据。" + "全部删除成功" : "全部" + ids.size() + "条数据。" + "删除成功了" + s.toString() + "，共" + i + "条数据。";
        return result;
    }

    @Override
    public Boolean checkFileMd5(String md5) {
        LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<File>().eq(StringUtils.isNotBlank(md5), File::getFileMd5, md5);
        return Optional.ofNullable(fileMapper.selectOne(queryWrapper)).isEmpty() ? false : true;
    }

    /**
     * 使用流式计算文件md5
     *
     * @param file
     * @return
     */
    @Override
    public String fileMd5(java.io.File file) {
        // 通过FileInputStream读取文件内容，并在完成后自动关闭流
        try (FileInputStream fis = new FileInputStream(file)) {
            // 获取MD5消息摘要实例
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // 创建一个8192字节的缓冲区，用于提高读取性能
            byte[] buffer = new byte[32768];
            int numRead;

            // 循环读取文件内容直到结束
            while ((numRead = fis.read(buffer)) != -1) {
                // 更新消息摘要
                digest.update(buffer, 0, numRead);
            }

            // 完成摘要生成
            byte[] md5Bytes = digest.digest();
            StringBuilder hexString = new StringBuilder();
            // 构建MD5的十六进制字符串表示
            for (byte b : md5Bytes) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            // 返回MD5十六进制字符串
            String string = hexString.toString();
            //十六进制字符串的第一个字符是0，而在这种情况下可能会省略掉0
            if (string.length() != 32) {
                string = "0" + string;
            }
            return string;
        } catch (IOException e) {
            // 处理文件读取错误
            System.err.println("文件读取错误: " + e.getMessage());
            return null;
        } catch (NoSuchAlgorithmException e) {
            // 处理算法未找到异常
            System.err.println("算法未找到: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String uploadChunk(String absolutePath, String md5, int chunkNumber) {
        //获取分块文件的路径
        String path = "/chunk/" + md5 + "/" + chunkNumber;
        //获取mimeType
        String mimeType = getMimeType(null);
        //分块上传minio
        boolean b = addFilesToMinIO(minioVo.getBucketName(), path, absolutePath, mimeType);
        if (b) {
            File file = new File();
            file.setId(new SnowflakeIdWorker(0, 0).nextId());
            file.setFileName(String.valueOf(chunkNumber));
            file.setFileType(null);
            file.setBucket(minioVo.getBucketName());
            file.setFileUrl(minioVo.getEndpointUrl() + path);
            file.setFilePath(minioVo.getBucketName() + "/chunk/" + md5);
            file.setFileSize(new java.io.File(absolutePath).length() / 1024.0);
            file.setFileMd5(fileMd5(new java.io.File(absolutePath)));
            file.setUserId(AuthContextUser.getUserId());
            file.setFileType("11003");
            file.setStart(10001);
            file.setCreateTime(LocalDateTime.now());
            int insert = fileMapper.insert(file);
            redisTemplate.opsForValue().set("chunk:" + md5 + ":" + chunkNumber + ":", JSONObject.toJSONString(file));
            if (insert != 1) {
                throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
            }
        }
        return b ? "分块上传成功" : "分块上传失败";

    }

    @Override
    public Boolean checkChunk(String md5, int chunkNumber) {
        //获取分块文件存储路径
        String path = "/chunk/" + md5 + "/" + chunkNumber;
        try {
            File file = null;
            String s = (String) redisTemplate.opsForValue().get("chunk:" + md5 + ":" + chunkNumber + ":");
            if (s != null) {
                file = JSONObject.parseObject(s, File.class);
            } else {
                LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<File>().eq(File::getFileMd5, md5).eq(File::getFileName, chunkNumber);
                file = fileMapper.selectOne(queryWrapper);
            }
            GetObjectResponse getChunk = minioClient.getObject(GetObjectArgs.builder().bucket(minioVo.getBucketName()).object(path).build());
            return file != null && getChunk != null ? true : false;
        } catch (Exception e) {
            throw new LinyiException(201, "检化分块是否存在失败");
        }
    }

    @Override
    @Transactional
    public String mergeChunk(String md5, String fileName, int chunkCount) {
        String path = "/chunk/" + md5 + "/";
        List<ComposeSource> sources = Stream.iterate(0, i -> ++i)
                .limit(chunkCount)
                .map(i -> ComposeSource.builder().bucket(minioVo.getBucketName()).object(path + i).build())
                .collect(Collectors.toList());
        File file = new File();
        //获取文件类型
        file.setFileType(fileName.substring(fileName.lastIndexOf(".")));
        String object =minioVo.getBucketName()+"/"+ "video" + getPathTime() + md5 + file.getFileType();
        try {
            minioClient.composeObject(ComposeObjectArgs.builder().bucket(minioVo.getBucketName()).object(object).sources(sources).build());
        } catch (Exception e) {
            throw new LinyiException(201, "合并分块失败");
        }
        //插入上传文件信息
        file.setFileUrl(minioVo.getEndpointUrl() + "/" + object);
        file.setId(new SnowflakeIdWorker(0, 0).nextId());
        file.setStart(10001);
        file.setFilePath(object);
        file.setFileName(md5 + fileName.substring(fileName.lastIndexOf(".")));
        file.setUserId(AuthContextUser.getUserId());
        file.setFileMd5(md5);
        file.setBucket(minioVo.getBucketName());
        file.setCreateTime(LocalDateTime.now());
        file.setFileType("11002");
        file.setFileSize(getMinioFileSize("chunk/" + md5 + "/"));
        int insert = fileMapper.insert(file);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        List<Long> ids = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();
        for (int i = 0; i < chunkCount; i++) {
            File file1 = JSONObject.parseObject((String) redisTemplate.opsForValue().get("chunk:" + md5 + ":" + i + ":"), File.class);
            ids.add(file1.getId());
            keys.add("chunk:" + md5 + ":" + i + ":");
        }
        Long delete = redisTemplate.delete(keys);
        log.info("redis删除分块信息成功！{}条",delete);
        String s = deleteFiles(ids);
        log.info("mysql删除分块信息成功！{}",s);
        //删除分片和文件夹
        Boolean b = deleteMinioFile("chunk/" + md5 + "/");
        if (!b){
            log.error("删除分片失败");
        }
        return "成功";
    }

    private Boolean deleteMinioFile(String s) {
        try {
            minioClient.listObjects(ListObjectsArgs.builder().bucket(minioVo.getBucketName()).prefix(s).recursive(true).build()).forEach(item -> {
                try {
                    minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioVo.getBucketName()).object(item.get().objectName()).build());
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


    /*----------------------------------------------------------功能类---------------------------------------------------------------------------------------*/

    private boolean addFilesToMinIO(String bucketName, String object, String absolutePath, String mimeType) {
        try {
            // 获取当前系统时间和服务器时间
            ZonedDateTime clientTime = ZonedDateTime.now(ZoneId.systemDefault());
            ZonedDateTime serverTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));

            // 计算时间差
            long timeDifferenceSeconds = Math.abs(ChronoUnit.SECONDS.between(clientTime, serverTime));
            if (timeDifferenceSeconds > 60) {
                log.error("客户端时间与服务器时间相差{}秒，请检查客户端时间是否正确", timeDifferenceSeconds);
                return false;
            }
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(object)
                            .filename(absolutePath)
                            .contentType(mimeType)
                            .build()
            );
            log.info("上传文件成功,bucket:{},objectName:{},filename：{}", bucketName, object, absolutePath);
            return true;
        } catch (Exception e) {
            log.error("上传文件出错,bucket:{},objectName:{},filename：{},错误信息:{}", bucketName, object, absolutePath, e.getMessage());
            return false;
        }
    }

    public String getPathTime() {
        return "/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replace("-", "/") + "/";
    }

    /**
     * 根据文件扩展名获取对应的MIME类型。
     * <p>
     * 该方法首先检查传入的扩展名是否为null，如果是，则将其置为空字符串。
     * 然后，利用ContentInfoUtil工具类尝试找到与扩展名匹配的ContentInfo对象。
     * 如果找到了匹配项，则获取其MIME类型；如果没有找到，或者传入的扩展名为空，则默认返回application/octet-stream。
     * </p>
     *
     * @param extension 文件的扩展名，不包含dot（.）。
     * @return 对应于文件扩展名的MIME类型，如果找不到则返回默认的application/octet-stream。
     */
    private String getMimeType(String extension) {
        // 将null扩展名置为空字符串
        if (extension == null) {
            extension = "";
        }
        // 根据扩展名查找匹配的ContentInfo
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        // 默认MIME类型
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        // 如果找到匹配项，则使用匹配项的MIME类型
        if (extensionMatch != null) {
            mimeType = extensionMatch.getMimeType();
        }
        return mimeType;
    }
}
