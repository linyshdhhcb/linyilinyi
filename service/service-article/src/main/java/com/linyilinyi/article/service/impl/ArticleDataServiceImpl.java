package com.linyilinyi.article.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyilinyi.article.mapper.ArticleDataMapper;
import com.linyilinyi.article.service.ArticleDataService;
import com.linyilinyi.article.service.ArticleService;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.entity.article.ArticleData;
import com.linyilinyi.model.entity.other.Leaderboard;
import com.linyilinyi.model.vo.other.Hot;
import com.linyilinyi.system.client.SystemClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Resource
    private ArticleService articleService;

    @Resource
    private SystemClient systemClient;

    @Resource
    private RedisTemplate redisTemplate;

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
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
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
    public String addArticleData(Integer id, Integer status, Integer count) {
        LambdaQueryWrapper<ArticleData> queryWrapper = new LambdaQueryWrapper<ArticleData>().eq(ArticleData::getArticleId, id);
        ArticleData articleData = articleDataMapper.selectOne(queryWrapper);
        switch (status) {
            case 22301 -> {
                articleData.setCommentCount(articleData.getCommentCount() + count);
                articleDataMapper.updateById(articleData);
                Hot hot = new Hot();
                hot.setCommentCount(articleData.getCommentCount());
                updateHot(id, status, hot);
                return "评论数" + count;
            }
            case 22302 -> {
                articleData.setReadCount(articleData.getReadCount() + count);
                articleDataMapper.updateById(articleData);
                Hot hot = new Hot();
                hot.setViewCount(articleData.getReadCount());
                systemClient.calculateHot(hot);
                updateHot(id, status, hot);
                return "播放数" + count;
            }
            case 22303 -> {
                articleData.setLikeCount(articleData.getLikeCount() + count);
                articleDataMapper.updateById(articleData);
                Hot hot = new Hot();
                hot.setLikeCount(articleData.getLikeCount());
                systemClient.calculateHot(hot);
                updateHot(id, status, hot);
                return "点赞数" + count;
            }
            case 22304 -> {
                articleData.setCollectCount(articleData.getCollectCount() + count);
                articleDataMapper.updateById(articleData);
                Hot hot = new Hot();
                hot.setCollectCount(articleData.getCollectCount());
                systemClient.calculateHot(hot);
                updateHot(id, status, hot);
                return "收藏数" + count;
            }
            case 22305 -> {
                articleData.setShareCount(articleData.getShareCount() + count);
                articleDataMapper.updateById(articleData);
                Hot hot = new Hot();
                hot.setShareCount(articleData.getShareCount());
                systemClient.calculateHot(hot);
                updateHot(id, status, hot);
                return "分享数" + count;
            }
            default -> throw new LinyiException("没有该类型");
        }
    }

    private void updateHot(Integer id, Integer status, Hot hot) {
        Integer data = systemClient.calculateHot(hot).getData();
        Leaderboard leaderboard = systemClient.getLeaderboardByTargetIdAndLeaderboardType(id, 11202).getData();
        if (Optional.ofNullable(leaderboard).isEmpty()) {
            throw new LinyiException("没有该排行榜");
        }
        Article articleById = articleService.getArticleById(id);
        //更新热度排行榜
        redisTemplate.opsForZSet().incrementScore("hot:" + "11202:" + id, JSON.toJSONString(articleById), data + leaderboard.getScore());
    }
}
