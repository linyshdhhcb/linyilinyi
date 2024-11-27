package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.vo.user.FanVo;
import com.linyilinyi.model.vo.user.FollowVo;
import com.linyilinyi.user.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p>
 * 粉丝表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "粉丝管理")
@RestController
@RequestMapping("follow")
@SuppressWarnings({"unchecked", "rawtypes"})
public class FollowController {

    @Resource
    private FollowService followService;

    @Operation(summary = "获取该用户所有粉丝")
    @GetMapping("/getFansList")
    @Log(title = "粉丝管理", content = "获取该用户所有粉丝")
    public Result<List<FanVo>> getFansList() {
        return Result.ok(followService.getFansList());
    }

    @Operation(summary = "获取该用户关注")
    @GetMapping("/getFollowList")
    @Log(title = "粉丝管理", content = "获取该用户关注")
    public Result<List<FollowVo>> getFollowList() {
        return Result.ok(followService.getFollowList());
    }

    @Operation(summary = "关注/取消用户")
    @GetMapping("/addFollow/{id}")
    @Log(title = "粉丝管理", content = "关注/取消用户")
    public Result<String> addFollow(@NotNull(message = "id不能为空") @PathVariable Integer id) {
        return Result.ok(followService.addFollow(id));
    }

    @Operation(summary = "判断是否关注了")
    @GetMapping("/isFollow")
    @Log(title = "粉丝管理", content = "判断是否关注了")
    public Result<Boolean> isFollow(@NotNull(message = "用户id不能为空") @RequestParam Integer fanId, @NotNull(message = "查询id不能为空") @RequestParam Integer idolId) {
        return Result.ok(followService.isFollow(fanId, idolId));
    }
}
