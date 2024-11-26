package com.linyilinyi.system.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.system.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Description 审核模块
 * @Author linyi
 * @Date 2024/11/18
 * @ClassName: ReviewController
 */
@Tag(name = "审核模块")
@RestController
@RequestMapping("review")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "视频审核")
    @PostMapping("video")
    @Log(title = "审核模块", content = "视频审核")
    public Result<String> video(@RequestParam Integer videoId, @RequestParam Integer status, @RequestParam(required = false) String reason) {
        return Result.ok(reviewService.video(videoId, status, reason));
    }

    @Operation(summary = "文章审核")
    @PostMapping("article")
    @Log(title = "审核模块", content = "文章审核")
    public Result<String> article(@RequestParam Integer articleId, @RequestParam Integer status, @RequestParam(required = false) String reason) {
        return Result.ok(reviewService.article(articleId, status, reason));
    }

    @Operation(summary = "获取审核列表")
    @PostMapping("getUnreviewedArticleList")
    @Log(title = "审核模块", content = "获取未审核文章列表")
    public Result<PageResult<?>> getUnreviewedArticleList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                                @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                                @RequestParam Integer mediaType,@RequestParam Integer status) {
        return Result.ok(reviewService.getUnreviewedList(pageNo,pageSize,mediaType,status));
    }
}
