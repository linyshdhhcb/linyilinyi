package com.linyilinyi.system.service.impl;

import com.linyilinyi.article.client.ArticleClient;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.entity.reviewer.Review;
import com.linyilinyi.model.entity.video.Video;
import com.linyilinyi.model.vo.article.ArticleQueryVo;
import com.linyilinyi.model.vo.video.VideoQueryVo;
import com.linyilinyi.model.vo.video.VideoVo;
import com.linyilinyi.system.mapper.ReviewMapper;
import com.linyilinyi.system.service.ReviewService;
import com.linyilinyi.user.client.UserClient;
import com.linyilinyi.video.client.VideoClient;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/18
 * @ClassName: ReviewServiceImpl
 */
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class ReviewServiceImpl implements ReviewService {

    @Resource
    private VideoClient videoClient;

    @Resource
    private ArticleClient articleClient;

    @Resource
    private UserClient userClient;

    @Resource
    private ReviewMapper reviewMapper;

    @Resource
    private HttpServletRequest request;

    @Override
    @Transactional
    public String video(Integer videoId, Integer status, String reason) {
        VideoVo data = videoClient.getVideoById(videoId).getData();
        Video video = new Video();
        BeanUtils.copyProperties(data, video);
        video.setVideoStart(status);
        video.setImageStart(status);
        video.setUpdateTime(LocalDateTime.now());
        Review review = new Review();
        review.setMediaId(videoId);
        review.setMediaType(11102);
        review.setStatus(status);
        review.setReviewer(userClient.getUserById(Integer.parseInt(request.getHeader("userid"))).getData().getUsername());
        review.setReviewDate(LocalDateTime.now());
        if (Optional.ofNullable(reason).isPresent()) {
            review.setRemarks(reason);
        }
        Result<String> result = videoClient.updateVideo(video);
        if (result.getCode() == 200) {
            int i = reviewMapper.insert(review);
            if (i != 1) {
                throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
            }
        }
        return "操作成功";
    }

    @Override
    public String article(Integer articleId, Integer status, String reason) {
        Article article = articleClient.getArticleById(articleId).getData();
        article.setArticleStatus(status);
        article.setImageStatus(status);
        article.setUpdateTime(LocalDateTime.now());
        Review review = new Review();
        review.setMediaId(articleId);
        review.setMediaType(11103);
        review.setStatus(status);
        review.setReviewer(userClient.getUserById(Integer.parseInt(request.getHeader("userid"))).getData().getUsername());
        review.setReviewDate(LocalDateTime.now());
        if (Optional.ofNullable(reason).isPresent()) {
            review.setRemarks(reason);
        }
        Result<String> result = articleClient.updateArticle(article);
        if (result.getCode() == 200) {
            int i = reviewMapper.insert(review);
            if (i != 1) {
                throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
            }
        }
        return "操作成功";
    }

    @Override
    public PageResult<?> getUnreviewedList(long pageNo, long pageSize, Integer mediaType, Integer status) {
        try {
            if (mediaType == 11102) {
                VideoQueryVo videoQueryVo = new VideoQueryVo();
                videoQueryVo.setVideoStart(status);
                return videoClient.list(pageNo, pageSize, videoQueryVo).getData();
            } else if (mediaType == 11103) {
                ArticleQueryVo articleQueryVo = new ArticleQueryVo();
                articleQueryVo.setArticleStatus(status);
                return articleClient.getArticleList(pageNo, pageSize, articleQueryVo).getData();
            }
        } catch (Exception e) {
            throw new LinyiException("获取未审核失败");
        }
        return null;
    }
}
