package com.linyilinyi.article.service.impl;

import com.linyilinyi.article.mapper.ArticleDataMapper;
import com.linyilinyi.article.service.ArticleDataService;
import com.linyilinyi.model.entity.article.ArticleData;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章数据统计表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class ArticleDataServiceImpl extends ServiceImpl<ArticleDataMapper, ArticleData> implements ArticleDataService {

}
