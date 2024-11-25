package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.collect.Collect;
import com.linyilinyi.model.vo.video.VideoVo;
import com.linyilinyi.user.service.CollectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p>
 * 收藏表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "收藏管理模块")
@RestController
@RequestMapping("collect")
@SuppressWarnings({"unchecked", "rawtypes"})
public class CollectController {

    @Resource
    private CollectService collectService;

    @Operation(summary = "根据收藏夹ID获取收藏夹里全部收藏列表")
    @GetMapping("getCollectList/{collectGroupId}")
    @Log(title = "收藏管理",content = "根据收藏夹ID获取收藏夹里全部收藏列表")
    public Result<List<VideoVo>> getCollectList(@NotNull(message = "收藏夹ID不能为空") @PathVariable Integer collectGroupId) {
        return Result.ok(collectService.getCollectList(collectGroupId));
    }

    @Operation(summary = "根据收藏夹ID添加收藏")
    @PostMapping("/addCollect")
    @Log(title = "收藏管理",content = "根据收藏夹ID添加收藏")
    public Result<String> addCollect(@NotNull(message = "收藏夹ID不能为空") @RequestParam Integer collectGroupId, @NotNull(message = "收藏对象id不能为空") @RequestParam Integer targetId,@NotNull(message = "收藏对象类型不能为空") @RequestParam Integer targetType) {
        return Result.ok(collectService.addCollect(collectGroupId, targetId,targetType));
    }

    @Operation(summary = "根据收藏对象ID删除收藏")
    @DeleteMapping("/deleteCollectList/{ids}")
    @Log(title = "收藏管理",content = "根据收藏对象ID删除收藏")
    public Result<String> deleteCollectList(@NotNull(message = "收藏对象id不能为空") @PathVariable List<Integer> ids) {
        return Result.ok(collectService.deleteCollectList(ids));
    }

    @Operation(summary = "修改收藏对象")
    @PutMapping("/updateCollect")
    @Log(title = "收藏管理",content = "修改收藏对象")
    public Result<String> updateCollect(@Valid @RequestBody Collect collect) {
        return Result.ok(collectService.updateCollect(collect));
    }

    @Operation(summary = "根据id查询收藏对象（回显）")
    @GetMapping("/getCollectById/{id}")
    @Log(title = "收藏管理",content = "根据id查询收藏对象（回显）")
    public Result<Collect> getCollectById(@PathVariable Integer id) {
        return Result.ok(collectService.getById(id));
    }

    @Operation(summary = "判读该对象是否收藏")
    @GetMapping("/isCollect/{targetId}")
    @Log(title = "收藏管理",content = "判读该对象是否收藏")
    public Result<Boolean> isCollect(@NotNull(message = "收藏对象id不能为空") @PathVariable Integer targetId,@NotNull(message = "收藏对象类型不能为空") @RequestParam Integer targetType) {
        return Result.ok(collectService.isCollect(targetId,targetType));
    }
}
