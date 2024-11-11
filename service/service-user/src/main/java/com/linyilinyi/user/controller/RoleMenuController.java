package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.user.Menu;
import com.linyilinyi.model.vo.user.AddRoleMenu;
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
    @Log(title = "角色管理", content = "给角色分配权限")
    public Result<String> addRoleMenu(@RequestBody AddRoleMenu addRoleMenu) {
        return Result.ok(roleMenuService.addRoleMenu(addRoleMenu));
    }

    @Operation(summary = "删除角色菜单")
    @DeleteMapping("deleteRoleMenu/{ids}")
    @Log(title = "角色管理", content = "删除角色菜单")
    public Result<String> deleteRoleMenu(@PathVariable List<Long> ids) {
        return Result.ok(roleMenuService.deleteRoleMenu(ids));
    }
}
