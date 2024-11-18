package com.linyilinyi.system.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.system.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Resource
    private ReviewService reviewService;

    @Operation(summary = "视频审核")
    @PostMapping("video")
    public Result<String> video(@RequestParam String videoId,@RequestParam Integer status,@RequestParam(required = false) String reason) {
        return reviewService.video(videoId, status,reason);
    }
}
