package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.vo.user.FanVo;
import com.linyilinyi.user.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Result<String> addLikes(@NotNull(message = "targetId不能为空") @PathVariable Integer targetId,@NotNull(message = "targetType不能为空") @PathVariable Integer targetType) {
        return Result.ok(likesService.addLikes(targetId,targetType));
    }

    @Operation(summary = "判断是否点赞过")
    @GetMapping("/isLikes/{targetId}/{targetType}")
    public Result<Boolean> isLikes(@NotNull(message = "targetId不能为空") @PathVariable Integer targetId,@NotNull(message = "targetType不能为空") @PathVariable Integer targetType) {
        return Result.ok(likesService.isLikes(targetId,targetType));
    }


}
