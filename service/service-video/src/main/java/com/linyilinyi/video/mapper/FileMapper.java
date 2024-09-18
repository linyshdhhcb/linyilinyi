package com.linyilinyi.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyilinyi.model.entity.file.File;

/**
 * <p>
 * 文件表 Mapper 接口
 * </p>
 *
 * @author linyi
 */
public interface FileMapper extends BaseMapper<File> {
    IPage<File> getDeleteFileList(Page<File> filePage, Long id);

    void isDelete(Long id);
}
