package com.linyilinyi.service.impl;

import com.linyilinyi.entity.po.Read;
import com.linyilinyi.mapper.ReadMapper;
import com.linyilinyi.service.ReadService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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
