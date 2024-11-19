package com.linyilinyi.system.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.vo.video.VideoVo;
import com.linyilinyi.system.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private ReviewService reviewService;

    @Operation(summary = "视频审核")
    @PostMapping("video")
    public Result<String> video(@RequestParam Integer videoId, @RequestParam Integer status, @RequestParam(required = false) String reason) {
        return Result.ok(reviewService.video(videoId, status, reason));
    }

    @Operation(summary = "文章审核")
    @PostMapping("article")
    public Result<String> article(@RequestParam Integer articleId, @RequestParam Integer status, @RequestParam(required = false) String reason) {
        return Result.ok(reviewService.article(articleId, status, reason));
    }

    @Operation(summary = "获取审核列表")
    @PostMapping("getUnreviewedArticleList")
    public Result<PageResult<?>> getUnreviewedArticleList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                                @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                                @RequestParam Integer mediaType,@RequestParam Integer status) {
        return Result.ok(reviewService.getUnreviewedList(pageNo,pageSize,mediaType,status));
    }
}
