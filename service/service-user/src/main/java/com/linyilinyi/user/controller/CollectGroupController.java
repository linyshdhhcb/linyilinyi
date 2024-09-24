package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.collect.Collect;
import com.linyilinyi.model.entity.collect.CollectGroup;
import com.linyilinyi.user.service.CollectGroupService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public Result<List<CollectGroup>> getCollectGroupList(){
        return Result.ok(collectGroupService.getCollectGroupList());
    }

    //@Operation(summary = "添加收藏夹")
}
