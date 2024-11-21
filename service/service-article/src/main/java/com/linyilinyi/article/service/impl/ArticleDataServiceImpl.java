package com.linyilinyi.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyilinyi.article.mapper.ArticleDataMapper;
import com.linyilinyi.article.service.ArticleDataService;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.model.entity.article.ArticleData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
@SuppressWarnings({"unchecked", "rawtypes"})
public class ArticleDataServiceImpl extends ServiceImpl<ArticleDataMapper, ArticleData> implements ArticleDataService {

    @Resource
    private ArticleDataMapper articleDataMapper;

    @Override
    public PageResult<ArticleData> getArticleDataList(long pageNo, long pageSize) {
        Page<ArticleData> articleDataPage = new Page<>();
        Page<ArticleData> page = articleDataMapper.selectPage(articleDataPage, null);
        return new PageResult<>(page.getRecords(), page.getTotal(), pageNo, pageSize);
    }

    @Override
    public ArticleData getArticleDataById(Integer id) {
        ArticleData articleData = articleDataMapper.selectById(id);
        if (Optional.ofNullable(articleData).isEmpty()) {
            throw new LinyiException(ResultCodeEnum.DATA_ERROR);
        }
        return articleData;
    }

    @Override
    public String updateArticleData(ArticleData articleData) {
        articleData.setUpdateTime(LocalDateTime.now());
        int i = articleDataMapper.updateById(articleData);
        if (i != 1) {
            throw new LinyiException(ResultCodeEnum.UPDATE_FAIL);
        }
        return "修改成功";
    }

    @Override
    public String addArticleData(Integer id, Integer status) {
        LambdaQueryWrapper<ArticleData> queryWrapper = new LambdaQueryWrapper<ArticleData>().eq(ArticleData::getArticleId, id);
        ArticleData articleData = articleDataMapper.selectOne(queryWrapper);
        switch (status) {
            case 22301 -> {
                articleData.setCommentCount(articleData.getCommentCount() + 1);
                articleDataMapper.updateById(articleData);
                return "评论数+1";
            }
            case 22302 -> {
                articleData.setReadCount(articleData.getReadCount() + 1);
                articleDataMapper.updateById(articleData);
                return "播放数+1";
            }
            case 22303 -> {
                articleData.setLikeCount(articleData.getLikeCount() + 1);
                articleDataMapper.updateById(articleData);
                return "点赞数+1";
            }
            case 22304 -> {
                articleData.setCollectCount(articleData.getCollectCount() + 1);
                articleDataMapper.updateById(articleData);
                return "收藏数+1";
            }
            case 22305 -> {
                articleData.setShareCount(articleData.getShareCount() + 1);
                articleDataMapper.updateById(articleData);
                return "分享数+1";
            }
            default -> throw new LinyiException("没有该类型");
        }
    }
}
