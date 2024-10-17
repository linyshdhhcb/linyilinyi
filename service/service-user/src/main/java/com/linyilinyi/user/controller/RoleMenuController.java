package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.user.Menu;
import com.linyilinyi.user.service.RoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 角色菜单表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "角色菜单管理模块")
@RestController
@RequestMapping("roleMenu")
public class RoleMenuController {

    @Resource
    private RoleMenuService roleMenuService;

    @Operation(summary = "给角色分配权限")
    @PostMapping("addRoleMenu")
    public Result<String> addRoleMenu(@RequestParam Long roleId, @RequestParam List<Long> menuId) {
        return Result.ok(roleMenuService.addRoleMenu(roleId, menuId));
    }

    @Operation(summary = "删除角色菜单")
    @DeleteMapping("deleteRoleMenu/{ids}")
    public Result<String> deleteRoleMenu(@PathVariable List<Long> ids) {
        return Result.ok(roleMenuService.deleteRoleMenu(ids));
    }
}
