package com.linyilinyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.dictionary.DictionaryType;

import java.util.List;

/**
 * <p>
 * 数据字典类型表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-10-21
 */
public interface DictionaryTypeService extends IService<DictionaryType> {

    String addDictionaryType(String type, String name);

    String deleteDictionaryType(List<Integer> ids);

    String updateDictionaryType(DictionaryType dictionaryType);

    List<DictionaryType> getDictionaryTypeList();


}
