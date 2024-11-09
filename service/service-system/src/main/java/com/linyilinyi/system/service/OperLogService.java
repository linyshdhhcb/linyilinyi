package com.linyilinyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.log.OperLog;
import com.linyilinyi.model.vo.log.OperLogQueryVo;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-11-08
 */
public interface OperLogService extends IService<OperLog> {

    PageResult<OperLog> pageList(OperLogQueryVo operLogQueryVo, long pageNo, long pageSize);
}
