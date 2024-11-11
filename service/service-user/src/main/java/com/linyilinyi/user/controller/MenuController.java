package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.user.Menu;
import com.linyilinyi.model.vo.user.MenuAdd;
import com.linyilinyi.user.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "菜单管理模块")
@RestController
@RequestMapping("menu")
@SuppressWarnings({"unchecked", "rawtypes"})
public class MenuController {

    @Resource
    private MenuService menuService;

    @Operation(summary = "新增菜单")
    @PostMapping("addMenu")
    @Log(title = "菜单管理",content = "新增菜单")
    public Result addMenu(@RequestBody MenuAdd menuAdd) {
        menuService.addMenu(menuAdd);
        return Result.ok();
    }

    @Operation(summary = "批量删除菜单数据")
    @DeleteMapping("deleteMenu/{ids}")
    @Log(title = "菜单管理",content = "批量删除菜单数据")
    public Result<String> deleteMenu(@PathVariable List<Long> ids) {
        return Result.ok(menuService.deleteMenu(ids));
    }

    @Operation(summary = "根据id查询菜单信息（回显）")
    @GetMapping("getMenuById/{id}")
    @Log(title = "菜单管理",content = "根据id查询菜单信息（回显）")
    public Result<Menu> getMenuById(@PathVariable Integer id) {
        return Result.ok(menuService.getMenuById(id));
    }

    @Operation(summary = "修改菜单信息")
    @PutMapping("updateMenu")
    @Log(title = "菜单管理",content = "修改菜单信息")
    public Result updateMenu(@RequestBody Menu menu) {
        menuService.updateMenu(menu);
        return Result.ok();
    }

    @Operation(summary = "获取菜单树型列表")
    @GetMapping("getMenuList")
    @Log(title = "菜单管理",content = "获取菜单树型列表")
    public Result<List<Menu>> getMenuList() {
        return Result.ok(menuService.getMenuList());
    }

    @Operation(summary = "根据角色id获取菜单")
    @GetMapping("getMenuListByRoleId/{roleId}")
    @Log(title = "菜单管理",content = "根据角色id获取菜单")
    public Result<List<Menu>> getMenuListByRoleId(@PathVariable Long roleId) {
        return Result.ok(menuService.getMenuListByRoleId(roleId));
    }

    @Operation(summary = "根据角色名称code获取菜单")
    @GetMapping("getMenuListByRoleCode/{roleCode}")
    @Log(title = "菜单管理",content = "根据角色名称code获取菜单")
    public Result<List<Menu>> getMenuListByRoleCode(@PathVariable String roleCode) {
        return Result.ok(menuService.getMenuListByRoleCode(roleCode));
    }


}
