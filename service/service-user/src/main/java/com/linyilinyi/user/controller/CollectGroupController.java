package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.collect.Collect;
import com.linyilinyi.model.entity.collect.CollectGroup;
import com.linyilinyi.user.service.CollectGroupService;
import com.mysql.cj.protocol.Message;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 收藏夹表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@RestController
@RequestMapping("collectGroup")
public class CollectGroupController {

    @Resource
    private CollectGroupService collectGroupService;

    @Operation(summary = "获取收藏夹列表")
    public Result<List<CollectGroup>> getCollectGroupList() {
        return Result.ok(collectGroupService.getCollectGroupList());
    }

    @Operation(summary = "添加收藏夹")
    @PostMapping("/addCollectGroup")
    public Result<String> addCollectGroup(@NotBlank(message = "名称不能为空") @RequestParam String name, @RequestParam(required = false) Integer status) {
        return Result.ok(collectGroupService.addCollectGroup(name, status));
    }

    @Operation(summary = "删除收藏夹")
    @DeleteMapping("/deleteCollectGroup/{id}")
    public Result<String> deleteCollectGroup(@NotNull(message = "id不能为空") @PathVariable Integer id) {
        return Result.ok(collectGroupService.deleteCollectGroup(id));
    }

    @Operation(summary = "修改收藏夹")
    @PutMapping("/updateCollectGroup")
    public Result<String> updateCollectGroup(@Valid @RequestBody CollectGroup collectGroup) {
        return Result.ok(collectGroupService.updateCollectGroup(collectGroup));
    }

    @Operation(summary = "根据id获取收藏夹信息")
    @GetMapping("/getCollectGroup/{id}")
    public Result<CollectGroup> getCollectGroup(@NotNull(message = "id不能为空") @PathVariable Integer id) {
        return Result.ok(collectGroupService.getById(id));
    }
}
