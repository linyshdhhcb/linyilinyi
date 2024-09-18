package com.linyilinyi.video.service.impl;


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
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
            file = items.size()==0 ? getById(id) : items.get(0);
            if (file == null){
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

    private String fileMd5(java.io.File file) {
        try (FileInputStream fis = new FileInputStream(file)) { // 使用 file 直接创建 FileInputStream
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192]; // 使用较大的缓冲区以提高读取效率
            int numRead;

            while ((numRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, numRead);
            }

            byte[] md5Bytes = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : md5Bytes) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (IOException e) {
            System.err.println("文件读取错误: " + e.getMessage());
            return null;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("算法未找到: " + e.getMessage());
            return null;
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
