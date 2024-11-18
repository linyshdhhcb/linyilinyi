package com.linyilinyi.system.controller;

import com.linyilinyi.system.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
