package com.linyilinyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.dictionary.DictionaryLabel;
import com.linyilinyi.model.vo.dictionary.DictionaryLabelQueryVo;

/**
 * <p>
 * 数据字典内容表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-10-21
 */
public interface DictionaryLabelService extends IService<DictionaryLabel> {


    PageResult<DictionaryLabel> pageList(long pageNo, long pageSize, DictionaryLabelQueryVo dictionaryLabelQueryVo);
}
