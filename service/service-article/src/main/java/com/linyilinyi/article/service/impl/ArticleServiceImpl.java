package com.linyilinyi.article.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.article.mapper.ArticleDataMapper;
import com.linyilinyi.article.mapper.ArticleMapper;
import com.linyilinyi.article.service.ArticleService;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.entity.article.ArticleData;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.model.vo.article.ArticleAddVo;
import com.linyilinyi.model.vo.article.ArticleQueryVo;
import com.linyilinyi.user.client.UserClient;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/20
 * @ClassName: ArticleServiceImpl
 */
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private UserClient userClient;

    @Resource
    private ArticleDataMapper articleDataMapper;

    @Override
    public PageResult<Article> getArticleList(long pageNo, long pageSize, ArticleQueryVo articleQueryVo) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(articleQueryVo.getTitle()), Article::getTitle, articleQueryVo.getTitle());
        queryWrapper.eq(Optional.ofNullable(articleQueryVo.getType()).isPresent(), Article::getType, articleQueryVo.getType());
        queryWrapper.eq(Optional.ofNullable(articleQueryVo.getUserId()).isPresent(), Article::getUserId, articleQueryVo.getUserId());
        queryWrapper.eq(StringUtils.isNotBlank(articleQueryVo.getUsername()), Article::getUsername, articleQueryVo.getUsername());
        queryWrapper.like(StringUtils.isNotBlank(articleQueryVo.getNickname()), Article::getNickname, articleQueryVo.getNickname());
        queryWrapper.eq(StringUtils.isNotBlank(articleQueryVo.getImageMd5()), Article::getImageMd5, articleQueryVo.getImageMd5());
        queryWrapper.eq(Optional.ofNullable(articleQueryVo.getArticleStatus()).isPresent(), Article::getImageStatus, articleQueryVo.getArticleStatus());
        Page<Article> articlePage = new Page<>(pageNo, pageSize);
        Page<Article> page = articleMapper.selectPage(articlePage, queryWrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), pageNo, pageSize);
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleMapper.selectById(id);
    }

    @Override
    public String addArticle(ArticleAddVo articleAddVo) {
        if (Optional.ofNullable(articleAddVo).isEmpty()) {
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
        Article article = new Article();
        BeanUtils.copyProperties(articleAddVo, article);
        article.setUserId(AuthContextUser.getUserId());
        User data = userClient.getUserById(AuthContextUser.getUserId()).getData();
        article.setUsername(data.getUsername());
        article.setNickname(data.getNickname());
        article.setCreateTime(LocalDateTime.now());
        int insert = articleMapper.insert(article);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        //创建文章信息统计表
        ArticleData articleData = new ArticleData();
        articleData.setArticleId(article.getId());
        articleData.setCreateTime(LocalDateTime.now());
        int insert1 = articleDataMapper.insert(articleData);
        if (insert1 != 1) {
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        return "添加成功";
    }

    @Override
    @Transactional
    public String deleteArticle(List<Integer> ids) {
        if (Optional.ofNullable(ids).isEmpty()) {
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
        //判断删除数据是否合法
        for (Integer id : ids) {
            if (id <= 0) {
                throw new LinyiException(ResultCodeEnum.VALID_ERROR);
            }
        }
        int i = articleMapper.deleteBatchIds(ids);
        int i1 = articleDataMapper.deleteBatchIds(ids);
        if (i != ids.size() && i1 != ids.size()) {
            throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
        }
        return "删除成功";
    }

    @Override
    public String updateArticle(Article article) {
        if (Optional.ofNullable(article).isEmpty()) {
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
        article.setUpdateTime(LocalDateTime.now());
        int i = articleMapper.updateById(article);
        if (i != 1) {
            throw new LinyiException(ResultCodeEnum.UPDATE_FAIL);
        }
        return "修改成功";
    }

    @Override
    public PageResult<Article> getArticleListByIsDelete(long pageNo, long pageSize) {
        Page<Article> articlePage = new Page<>(pageNo, pageSize);
        IPage<Article> iPage = articleMapper.getArticleListByIsDelete(articlePage);
        return new PageResult<>(iPage.getRecords(), iPage.getTotal(), pageNo, pageSize);
    }

    @Override
    public String deleteArticleByPhysical(List<Integer> ids) {
        for (Integer id : ids) {
            if (id <= 0) {
                throw new LinyiException(ResultCodeEnum.VALID_ERROR);
            }
        }
        articleMapper.deleteArticleByPhysical(ids);

        return "删除成功";

    }
}
