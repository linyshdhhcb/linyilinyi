package com.linyilinyi.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyilinyi.model.entity.article.Article;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文章信息表 Mapper 接口
 * </p>
 *
 * @author linyi
 */
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<Article> getArticleListByIsDelete(Page<Article> articlePage);
}
