package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.vo.user.FanVo;
import com.linyilinyi.user.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 点赞表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "点赞管理模块")
@RestController
@RequestMapping("likes")
@SuppressWarnings({"unchecked", "rawtypes"})
public class LikesController {

    @Resource
    private LikesService likesService;

    @Operation(summary = "点赞/取消")
    @GetMapping("/addLikes/{targetId}/{targetType}")
    @Log(title = "点赞管理", content = "点赞/取消")
    public Result<String> addLikes(@NotNull(message = "targetId不能为空") @PathVariable Integer targetId, @NotNull(message = "targetType不能为空") @PathVariable Integer targetType) {
        return Result.ok(likesService.addLikes(targetId, targetType));
    }

    @Operation(summary = "判断是否点赞过")
    @GetMapping("/isLikes/{targetId}/{targetType}")
    @Log(title = "点赞管理", content = "判断是否点赞过")
    public Result<Boolean> isLikes(@RequestHeader("userid") Object userId, @NotNull(message = "targetId不能为空") @PathVariable Integer targetId, @NotNull(message = "targetType不能为空") @PathVariable Integer targetType) {
        return Result.ok(likesService.isLikes(targetId, targetType, Integer.parseInt(userId.toString())));
    }
}
