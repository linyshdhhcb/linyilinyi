package com.linyilinyi.article.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyilinyi.article.mapper.ArticleDataMapper;
import com.linyilinyi.article.service.ArticleDataService;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.article.ArticleData;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Resource
    private ArticleDataMapper articleDataMapper;

    @Override
    public PageResult<ArticleData> getArticleDataList(long pageNo, long pageSize) {
        Page<ArticleData> articleDataPage = new Page<>();
        Page<ArticleData> page = articleDataMapper.selectPage(articleDataPage, null);
        return new PageResult<>(page.getRecords(),page.getTotal(),pageNo,pageSize);
    }

    @Override
    public ArticleData getArticleDataById(Integer id) {
        ArticleData articleData = articleDataMapper.selectById(id);
        if (Optional.ofNullable(articleData).isEmpty()){
            throw new LinyiException(ResultCodeEnum.DATA_ERROR);
        }
        return articleData;
    }
}
