package com.linyilinyi.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyilinyi.model.entity.article.ArticleData;

import java.util.List;

/**
 * <p>
 * 文章数据统计表 Mapper 接口
 * </p>
 *
 * @author linyi
 */
public interface ArticleDataMapper extends BaseMapper<ArticleData> {

    int deleteArticleDataByPhysical(List<Integer> ids);
}
