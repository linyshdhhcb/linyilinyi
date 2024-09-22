package com.linyilinyi.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.file.File;
import com.linyilinyi.model.vo.file.FileQueryVo;

import java.util.List;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-17
 */
public interface FileService extends IService<File> {

    File uploadVideoFile(File file, String absolutePath, String bucketNamea);

    File uploadImageFile(File file, String absolutePath, String bucketNamea);

    PageResult<File> uploadFileList(long pageNo, long pageSize, FileQueryVo fileQueryVo);


    String isDeleteFile(Long id);

    PageResult<File> getDeleteFileList(long pageNo, long pageSize, Long id);

    String deleteFiles(List<Long> ids);

    Boolean checkFileMd5(String md5);

    String fileMd5(java.io.File file);

    String uploadChunk(String absolutePath, String md5, int chunkNumber);

    Boolean checkChunk(String md5, int chunkNumber);

    File mergeChunk(String md5, String fileName, int chunkCount);
}
