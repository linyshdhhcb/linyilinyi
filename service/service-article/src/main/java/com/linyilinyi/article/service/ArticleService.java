package com.linyilinyi.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.vo.article.ArticleAddVo;
import com.linyilinyi.model.vo.article.ArticleQueryVo;

import java.util.List;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/20
 * @ClassName: ArticleService
 */
public interface ArticleService extends IService<Article> {

    PageResult<Article> getArticleList(long pageNo, long pageSize, ArticleQueryVo articleQueryVo);

    Article getArticleById(Integer id);

    String addArticle(ArticleAddVo articleAddVo);

    String deleteArticle(List<Integer> ids);


    String updateArticle(Article article);

}
