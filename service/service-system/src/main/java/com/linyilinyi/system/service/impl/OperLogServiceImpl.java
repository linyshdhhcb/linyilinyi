package com.linyilinyi.system.service.impl;


import com.linyilinyi.model.entity.log.OperLog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.system.mapper.OperLogMapper;
import com.linyilinyi.system.service.OperLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLog> implements OperLogService {

}
