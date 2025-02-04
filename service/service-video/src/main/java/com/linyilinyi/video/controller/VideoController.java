package com.linyilinyi.video.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.video.Video;
import com.linyilinyi.model.vo.video.VideoAddVo;
import com.linyilinyi.model.vo.video.VideoQueryVo;
import com.linyilinyi.model.vo.video.VideoVo;
import com.linyilinyi.video.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @Resource
    private HttpServletRequest request;

    @Operation(summary = "视频列表")
    @PostMapping("/list")
    @Log(title = "视频管理",content = "视频列表")
    public Result<PageResult> list(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                   @RequestParam(required = false, defaultValue = "5") long pageSize,
                                    @RequestBody VideoQueryVo videoQueryVo) {

        return Result.ok(videoService.getList(pageNo, pageSize, videoQueryVo));
    }

    @Operation(summary = "根据id查询视频")
    @GetMapping("/getVideoById/{id}")
    @Log(title = "视频管理",content = "根据id查询视频")
    public Result<VideoVo> getVideoById(@Valid @PathVariable Integer id) {
        return Result.ok(videoService.getVideoById(id));
    }

    @Operation(summary = "添加视频")
    @PostMapping("/addVideo")
    @Log(title = "视频管理",content = "添加视频")
    public Result<Video> addVideo(@Valid @RequestBody VideoAddVo videoAddVo) {
        return Result.ok(videoService.addVideo(videoAddVo));
    }

    @Operation(summary = "逻辑删除视频")
    @DeleteMapping("/deleteVideo/{ids}")
    @Log(title = "视频管理",content = "逻辑删除视频")
    public Result<String> deleteVideo(@NotNull(message = "ids不能为空") @PathVariable List<Integer> ids) {
        return Result.ok(videoService.deleteVideo(ids));
    }

    @Operation(summary = "修改视频信息")
    @PutMapping("/updateVideo")
    @Log(title = "视频管理",content = "修改视频信息")
    public Result<String> updateVideo(@Valid @RequestBody Video video) {
        return Result.ok(videoService.updateVideo(video));
    }

    @Operation(summary = "根据id获取用户的全部视频列表")
    @GetMapping("/getVideoListByUserId/{userId}")
    @Log(title = "视频管理",content = "根据id获取用户的全部视频列表")
    public Result<List<Video>> getVideoListByUserId(@NotNull(message = "userId不能为空") @PathVariable Integer userId) {
        return Result.ok(videoService.getVideoListByUserId(userId));
    }

    @Operation(summary = "获取当前用户的全部视频列表")
    @GetMapping("/getVideoList")
    @Log(title = "视频管理",content = "获取当前用户的全部视频列表")
    public Result<List<Video>> getVideoList() {
        return Result.ok(videoService.getVideoListByUserId(Integer.parseInt(request.getHeader("userid"))));
    }

    @Operation(summary = "获取token信息")
    @GetMapping(value = "/getToken")
    public Result<Integer> getByToken(HttpServletRequest request) {
        return Result.ok(videoService.getByToken(request));
    }

}
