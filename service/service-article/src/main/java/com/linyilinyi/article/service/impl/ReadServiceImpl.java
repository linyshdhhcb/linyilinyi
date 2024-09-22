package com.linyilinyi.article.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.article.mapper.ReadMapper;
import com.linyilinyi.article.service.ReadService;
import com.linyilinyi.model.entity.article.Read;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 阅读记录表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class ReadServiceImpl extends ServiceImpl<ReadMapper, Read> implements ReadService {

}
