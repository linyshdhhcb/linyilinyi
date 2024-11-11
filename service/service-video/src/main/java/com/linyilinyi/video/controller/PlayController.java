package com.linyilinyi.video.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.video.Play;
import com.linyilinyi.model.vo.video.PlayAddVo;
import com.linyilinyi.model.vo.video.PlayQueryVo;
import com.linyilinyi.video.service.PlayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 历史播放表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "历史播放管理")
@RestController
@RequestMapping("play")
@SuppressWarnings({"unchecked", "rawtypes"})
public class PlayController {

    @Resource
    private PlayService playService;

    @Operation(summary = "获取全部是视频历史观看记录列表")
    @PostMapping("getPlayList")
    @Log(title = "历史播放管理", content = "获取全部是视频历史观看记录列表")
    public Result<PageResult<Play>> getPlayList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                @RequestBody PlayQueryVo playQueryVo) {
        return Result.ok(playService.getPlayList(pageNo, pageSize, playQueryVo));
    }

    @Operation(summary = "获取当前用户全部播放记录")
    @GetMapping("getPlayListByUser")
    @Log(title = "历史播放管理", content = "获取当前用户全部播放记录")
    public Result<List<Play>> getPlayListByUser() {
        return Result.ok(playService.getPlayListByUser());
    }

    @Operation(summary = "添加视频播放记录")
    @PostMapping("addPlay")
    @Log(title = "历史播放管理", content = "添加视频播放记录")
    public Result<String> addPlay(@RequestBody PlayAddVo playAddVo) {
        return Result.ok(playService.addPlay(playAddVo));
    }

    @Operation(summary = "删除视频播放记录")
    @DeleteMapping("deletePlay/{ids}")
    @Log(title = "历史播放管理", content = "删除视频播放记录")
    public Result<String> deletePlay(@PathVariable List<Integer> ids) {
        return Result.ok(playService.deletePlay(ids));
    }
}
