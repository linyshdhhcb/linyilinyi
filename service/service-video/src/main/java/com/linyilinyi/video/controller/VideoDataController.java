package com.linyilinyi.video.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.video.VideoData;
import com.linyilinyi.video.service.VideoDataService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 视频数据统计表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@RestController
@RequestMapping("videoData")
public class VideoDataController {

    @Resource
    private VideoDataService videoDataService;

    @Operation(summary = "根据视频id查询视频数据")
    @GetMapping("/getVideoDataById/{videoId}")
    public Result<VideoData> getVideoDataById(@NotNull(message = "videoId不能为空") @PathVariable Long videoId) {
        return Result.ok(videoDataService.getVideoDataById(videoId));
    }
}
