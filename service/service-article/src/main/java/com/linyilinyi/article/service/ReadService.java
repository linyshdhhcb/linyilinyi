package com.linyilinyi.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.article.Read;
import com.linyilinyi.model.vo.article.ReadAddVo;
import com.linyilinyi.model.vo.article.ReadQueryVo;

import java.util.List;

/**
 * <p>
 * 阅读记录表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-22
 */
public interface ReadService extends IService<Read> {

    PageResult<Read> getReadList(long pageNo, long pageSize, ReadQueryVo readQueryVo);

    List<Read> getReadListByUserId();

    String addRead(ReadAddVo readAddVo);

    String deleteRead(List<Integer> ids);
}
