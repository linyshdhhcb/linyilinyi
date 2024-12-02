package com.linyilinyi.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.article.ArticleData;

/**
 * <p>
 * 文章数据统计表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-20
 */
public interface ArticleDataService extends IService<ArticleData> {


    PageResult<ArticleData> getArticleDataList(long pageNo, long pageSize);

    ArticleData getArticleDataById(Integer id);

    String updateArticleData(ArticleData articleData);

    String addArticleData(Integer id, Integer status, Integer count);

}
