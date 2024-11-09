package com.linyilinyi.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.log.OperLog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.model.vo.log.OperLogQueryVo;
import com.linyilinyi.system.mapper.OperLogMapper;
import com.linyilinyi.system.service.OperLogService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLog> implements OperLogService {

    @Resource
    private OperLogMapper operLogMapper;

    @Override
    public PageResult<OperLog> pageList(OperLogQueryVo operLogQueryVo, long pageNo, long pageSize) {
        LambdaQueryWrapper<OperLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(operLogQueryVo.getTitle()), OperLog::getTitle, operLogQueryVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(operLogQueryVo.getContent()), OperLog::getContent, operLogQueryVo.getContent());
        queryWrapper.eq(StringUtils.isNotBlank(operLogQueryVo.getMethod()), OperLog::getMethod, operLogQueryVo.getMethod());
        queryWrapper.eq(StringUtils.isNotBlank(operLogQueryVo.getRequestMethod()), OperLog::getRequestMethod, operLogQueryVo.getRequestMethod());
        queryWrapper.eq(StringUtils.isNotBlank(operLogQueryVo.getOperName()), OperLog::getOperName, operLogQueryVo.getOperName());
        queryWrapper.like(StringUtils.isNotBlank(operLogQueryVo.getRequestUrl()), OperLog::getRequestUrl, operLogQueryVo.getRequestUrl());
        queryWrapper.eq(StringUtils.isNotBlank(operLogQueryVo.getIp()), OperLog::getIp, operLogQueryVo.getIp());
        queryWrapper.like(StringUtils.isNotBlank(operLogQueryVo.getIpLocation()), OperLog::getIpLocation, operLogQueryVo.getIpLocation());
        queryWrapper.eq(Optional.ofNullable(operLogQueryVo.getStatus()).isPresent(), OperLog::getStatus, operLogQueryVo.getStatus());
        queryWrapper.like(StringUtils.isNotBlank(operLogQueryVo.getErrorMsg()), OperLog::getErrorMsg, operLogQueryVo.getErrorMsg());
        queryWrapper.gt(operLogQueryVo.getStartTime() != null, OperLog::getOperTime, operLogQueryVo.getStartTime());
        queryWrapper.lt(operLogQueryVo.getEndTime() != null, OperLog::getOperTime, operLogQueryVo.getEndTime());
        queryWrapper.gt(operLogQueryVo.getMinTakeTime() != null, OperLog::getTakeTime, operLogQueryVo.getMinTakeTime());
        queryWrapper.lt(operLogQueryVo.getMaxTakeTime() != null, OperLog::getTakeTime, operLogQueryVo.getMaxTakeTime());
        Page<OperLog> operLogPage = new Page<>(pageNo,pageSize);
        Page<OperLog> logPage = operLogMapper.selectPage(operLogPage, queryWrapper);
        return new PageResult<>(logPage.getRecords(), logPage.getTotal(),pageNo , pageSize);
    }
}
