package com.linyilinyi.video.controller;

import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.file.File;
import com.linyilinyi.model.vo.file.FileQueryVo;
import com.linyilinyi.video.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;


/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Tag(name = "文件管理接口")
@Slf4j
@RestController
@RequestMapping("/file")
@SuppressWarnings({"unchecked", "rawtypes"})
public class FileController {

    @Resource
    private FileService fileService;

    @Operation(summary = "分页查询上传文件列表")
    @PostMapping("/uploadFileList")
    public Result<PageResult<File>> uploadFileList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                   @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                   @Valid @RequestBody FileQueryVo fileQueryVo) {
        return Result.ok(fileService.uploadFileList(pageNo, pageSize, fileQueryVo));
    }

    @Operation(summary = "根据id查询文件信息（回显使用）")
    @GetMapping("/getFileById/{id}")
    public Result<File> getFileById(@NotNull(message = "id不能为空") @PathVariable Long id) {
        return Result.ok(fileService.getById(id));
    }


    @Operation(summary = "视频文件上传（视频不大于1G）")
    @PostMapping("/uploadVideoFile")
    //Spring Web MVC 的依赖下的一种处理上传文件的类
    public Result<File> uploadVideoFile(@RequestPart("filedata") MultipartFile filedata) {
        try {

            if (!filedata.getContentType().startsWith("video/")) {
                throw new LinyiException("文件类型不正确,只能上传视频");
            }
            if (filedata.getSize() > 1024 * 1024 * 1024) {
                return Result.fail("视频大小超过1G");
            }
            File file = new File();
            //getSize()：获取文件大小（以字节为单位）
            file.setFileSize(Double.parseDouble(new DecimalFormat("#.##").format((double) filedata.getSize() / 1024)));
            //getContentType()：获取文件的 MIME 类型（例如：image/jpeg）
            String contentType = filedata.getContentType();
            file.setFilePath(contentType);
            file.setFileType("." + contentType.substring(contentType.indexOf("/") + 1, contentType.length()));
            //创建一个临时文件存储上传文件
            java.io.File tempFile = java.io.File.createTempFile("minio", ".temp");
            // 上传完成后自动删除临时文件
            tempFile.deleteOnExit();
            //将filedata的内容写入到File中
            filedata.transferTo(tempFile);
            //获取临时文件的绝对路径
            String absolutePath = tempFile.getAbsolutePath();
            String bucketNamea = null;
            if (filedata.getContentType().startsWith("video/")) {
                bucketNamea = "video";
            }
            File file1 = fileService.uploadVideoFile(file, absolutePath, bucketNamea);
            return Result.ok(file1);
        } catch (IOException e) {
            return Result.fail("视频文件上传失败" + e.getMessage());
        }
    }

    @Operation(summary = "上传图片（图片不大于3M）")
    @PostMapping("/uploadImageFile")
    public Result<File> uploadImageFile(@RequestPart("filedata") MultipartFile filedata) {
        try {
            if (!filedata.getContentType().startsWith("image/")) {
                return Result.fail("文件类型不正确,只能上传图片");
            }
            if (filedata.getSize() > 3 * 1024 * 1024) {
                return Result.fail("图片大小超过3M");
            }
            File file = new File();
            //getSize()：获取文件大小（以字节为单位）
            file.setFileSize(Double.parseDouble(new DecimalFormat("#.##").format((double) filedata.getSize() / 1024)));
            //getContentType()：获取文件的 MIME 类型（例如：image/jpeg）
            String contentType = filedata.getContentType();
            file.setFilePath(contentType);
            file.setFileType("." + contentType.substring(contentType.indexOf("/") + 1, contentType.length()));
            //创建一个临时文件存储上传文件
            java.io.File tempFile = java.io.File.createTempFile("minio", ".temp");
            // 上传完成后自动删除临时文件
            tempFile.deleteOnExit();
            //将filedata的内容写入到File中
            filedata.transferTo(tempFile);
            //获取临时文件的绝对路径
            String absolutePath = tempFile.getAbsolutePath();
            String bucketNamea = null;
            if (filedata.getContentType().startsWith("image/")) {
                bucketNamea = "image";
            }
            return Result.ok(fileService.uploadImageFile(file, absolutePath, bucketNamea));
        } catch (Exception e) {
            return Result.fail("图片文件上传失败" + e.getMessage());
        }
    }

    @Operation(summary = "逻辑删除文件信息")
    @DeleteMapping("/isDeleteFile/{id}")
    public Result<String> isDeleteFile(@NotNull(message = "id不能为空") @PathVariable Long id) {
        return Result.ok(fileService.isDeleteFile(id));
    }

    @Operation(summary = "获取逻辑删除文件列表【也用于根据id回显逻辑删除的文件信息】")
    @PostMapping("/getDeleteFileList")
    public Result<PageResult<File>> getDeleteFileList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                      @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                      @RequestParam(required = false) Long id) {
        return Result.ok(fileService.getDeleteFileList(pageNo, pageSize, id));
    }

    @Operation(summary = "物理删除上传文件")
    @DeleteMapping("/deleteFile/{ids}")
    public Result<String> deleteFile(@NotNull(message = "id不能为空") @PathVariable List<Long> ids) {
        return Result.ok(fileService.deleteFiles(ids));
    }

    @Operation(summary = "检查文件md5（已存在，未存在，传输部分）")
    @GetMapping("/checkFileMd5/{md5}")
    public Result<Boolean> checkFileMd5(@NotBlank(message = "md5不能为空") @PathVariable String md5) {
        return Result.ok(fileService.checkFileMd5(md5));
    }

    @Operation(summary = "计算文件md5")
    @PostMapping("/getFileMd5")
    public Result<String> getFileMd5(@RequestPart("filedata") MultipartFile filedata) {
        try {
            long l = System.currentTimeMillis();
            //创建临时文件
            java.io.File tempFile = java.io.File.createTempFile("minio", ".temp");
            //上传后自动删除
            tempFile.deleteOnExit();
            //将filedata内容写入tempfile
            long l1 = System.currentTimeMillis();
            filedata.transferTo(tempFile);
            String absolutePath = tempFile.getAbsolutePath();
            String s = fileService.fileMd5(new java.io.File(absolutePath));
            System.out.println("#################文件计算md5耗时：" + (System.currentTimeMillis() - l));
            return Result.ok(s);
        } catch (IOException e) {
            throw new LinyiException("文件计算md5失败" + e.getMessage());
        }
    }

    @Operation(summary = "分片上传（md5是整个文件的）")
    @PostMapping("/uploadChunk")
    public Result<String> uploadChunk(@RequestPart("file") MultipartFile file,
                                      @NotBlank(message = "md5不能为空") @RequestParam("md5") String md5,
                                      @NotNull(message = "分块编号不能为空") @RequestParam("chunkNumber") int chunkNumber){
        try {
            java.io.File tempFile = java.io.File.createTempFile("minio", ".temp");
            file.transferTo(tempFile);
            String absolutePath = tempFile.getAbsolutePath();
            return Result.ok(fileService.uploadChunk(absolutePath, md5, chunkNumber));
        } catch (Exception e) {
            throw new LinyiException("文件分片上传失败" + e.getMessage());
        }

    }

    @Operation(summary = "检查分块是否存在")
    @GetMapping("/checkChunk/{md5}/{chunkNumber}")
    public Result<Boolean> checkChunk(@NotBlank(message = "md5不能为空") @PathVariable String md5,
                                      @NotNull(message = "分块序号不能为空") @PathVariable int chunkNumber) {
        return Result.ok(fileService.checkChunk(md5, chunkNumber));
    }



}
