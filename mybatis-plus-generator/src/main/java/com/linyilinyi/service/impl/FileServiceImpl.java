package com.linyilinyi.service.impl;

import com.linyilinyi.entity.po.File;
import com.linyilinyi.mapper.FileMapper;
import com.linyilinyi.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

}
