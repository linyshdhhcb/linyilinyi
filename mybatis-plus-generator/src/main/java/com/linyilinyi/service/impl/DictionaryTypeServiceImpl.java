package com.linyilinyi.service.impl;

import com.linyilinyi.entity.po.DictionaryType;
import com.linyilinyi.mapper.DictionaryTypeMapper;
import com.linyilinyi.service.DictionaryTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 数据字典类型表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class DictionaryTypeServiceImpl extends ServiceImpl<DictionaryTypeMapper, DictionaryType> implements DictionaryTypeService {

}
