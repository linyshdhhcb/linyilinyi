package com.linyilinyi.service.impl;

import com.linyilinyi.entity.po.DictionaryLabel;
import com.linyilinyi.mapper.DictionaryLabelMapper;
import com.linyilinyi.service.DictionaryLabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 数据字典内容表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class DictionaryLabelServiceImpl extends ServiceImpl<DictionaryLabelMapper, DictionaryLabel> implements DictionaryLabelService {

}
