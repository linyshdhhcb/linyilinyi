package com.linyilinyi.article.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.article.mapper.ArticleMapper;
import com.linyilinyi.article.service.ArticleService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.vo.article.ArticleQueryVo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/20
 * @ClassName: ArticleServiceImpl
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Override
    public PageResult<Article> getArticleList(long pageNo, long pageSize, ArticleQueryVo articleQueryVo) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(articleQueryVo.getTitle()), Article::getTitle, articleQueryVo.getTitle());
        queryWrapper.eq(Optional.ofNullable(articleQueryVo.getType()).isPresent(), Article::getType, articleQueryVo.getType());
        queryWrapper.eq(Optional.ofNullable(articleQueryVo.getUserId()).isPresent(), Article::getUserId, articleQueryVo.getUserId());
        queryWrapper.eq(StringUtils.isNotBlank(articleQueryVo.getImageMd5()), Article::getImageMd5, articleQueryVo.getImageMd5());
        queryWrapper.eq(Optional.ofNullable(articleQueryVo.getImageStatus()).isPresent(), Article::getImageStatus, articleQueryVo.getImageStatus());
        Page<Article> articlePage = new Page<>(pageNo, pageSize);
        Page<Article> page = articleMapper.selectPage(articlePage, queryWrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), pageNo, pageSize);
    }
}
