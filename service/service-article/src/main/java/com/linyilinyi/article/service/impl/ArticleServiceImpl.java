package com.linyilinyi.article.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.article.mapper.ArticleMapper;
import com.linyilinyi.article.service.ArticleService;
import com.linyilinyi.model.entity.article.Article;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/20
 * @ClassName: ArticleServiceImpl
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
