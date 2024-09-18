package com.linyilinyi.video.controller;

import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.video.Video;
import com.linyilinyi.model.vo.video.VideoAddVo;
import com.linyilinyi.model.vo.video.VideoQueryVo;
import com.linyilinyi.model.vo.video.VideoVo;
import com.linyilinyi.video.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;



/**
 * @Description
 * @Author linyi
 * @Date 2024/9/14
 * @ClassName: VideoController
 */
@Tag(name = "视频管理接口")
@RestController
@RequestMapping("/video")
@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
public class VideoController {

    @Resource
    private VideoService videoService;

    @Operation(summary = "视频列表")
    @PostMapping("/list")
    public Result<PageResult> list(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                   @RequestParam(required = false, defaultValue = "5") long pageSize,
                                   @Valid @RequestBody VideoQueryVo videoQueryVo) {

        return Result.ok(videoService.getList(pageNo, pageSize, videoQueryVo));
    }

    @Operation(summary = "根据id查询视频")
    @GetMapping("/getVideoById/{id}")
    public Result<VideoVo> getVideoById(@Valid @PathVariable Long id) {
        return Result.ok(videoService.getVideoById(id));
    }

    @Operation(summary = "添加视频")
    @PostMapping("/addVideo")
    public Result<Video> addVideo(@Valid @RequestBody VideoAddVo videoAddVo) {
        return Result.ok(videoService.addVideo(videoAddVo));
    }

}
