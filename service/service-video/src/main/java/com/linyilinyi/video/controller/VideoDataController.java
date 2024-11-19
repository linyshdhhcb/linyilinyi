package com.linyilinyi.video.controller;

import com.github.xiaoymin.knife4j.annotations.Ignore;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.video.VideoData;
import com.linyilinyi.video.service.VideoDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 视频数据统计表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "视频数据统计管理")
@RestController
@RequestMapping("videoData")
@SuppressWarnings({"unchecked", "rawtypes"})
public class VideoDataController {

    @Resource
    private VideoDataService videoDataService;

    @Operation(summary = "分页获取视频数据列表")
    @PostMapping("/getVideoDataList")
    @Log(title = "视频数据统计管理",content = "分页获取视频数据列表")
    public Result<PageResult<VideoData>> getVideoDataList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                          @RequestParam(required = false, defaultValue = "10") long pageSize) {
        return Result.ok(videoDataService.getVideoDataList(pageNo, pageSize));
    }

    @Operation(summary = "根据视频id查询视频数据")
    @GetMapping("/getVideoDataById/{videoId}")
    @Log(title = "视频数据统计管理",content = "根据视频id查询视频数据")
    public Result<VideoData> getVideoDataById(@NotNull(message = "videoId不能为空") @PathVariable Long videoId) {
        return Result.ok(videoDataService.getVideoDataById(videoId));
    }

    @Operation(summary = "修改视频数据")
    @PostMapping("updateVideoData")
    @Log(title = "视频数据统计管理",content = "修改视频数据")
    public Result<String> updateVideoData(@RequestBody VideoData videoData) {
        return Result.ok(videoDataService.updateVideoData(videoData));
    }


    @Operation(summary = "添加视频数据")
    @PostMapping("addVideoData")
    @Log(title = "视频数据统计管理",content = "添加视频数据")
    public Result<String> addData(@RequestParam Integer id,@RequestParam Integer status) {
        return Result.ok(videoDataService.addData(id,status));
    }
}
