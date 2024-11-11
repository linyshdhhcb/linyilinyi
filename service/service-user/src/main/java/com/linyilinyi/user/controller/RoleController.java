package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.model.vo.user.RoleQueryVo;
import com.linyilinyi.user.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "角色管理模块")
@RestController
@RequestMapping("role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Operation(summary = "新增角色")
    @PostMapping("addRole")
    @Log(title = "角色管理",content = "新增角色")
    public Result<String> addRole(@RequestParam String name, @RequestParam String code) {
        roleService.addRole(name, code);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("deleteRoleById/{ids}")
    @Log(title = "角色管理",content = "删除角色")
    public Result<String> deleteRoleById(@PathVariable List<Long> ids) {
        return Result.ok(roleService.deleteRoleById(ids));
    }

    @Operation(summary = "分页条件查询角色列表")
    @PostMapping("getRoleList")
    @Log(title = "角色管理",content = "分页条件查询角色列表")
    public Result<PageResult<Role>> getRoleList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                @RequestBody RoleQueryVo roleQueryVo) {
        return Result.ok(roleService.getRoleList(pageNo, pageSize, roleQueryVo));
    }

    @Operation(summary = "修改角色")
    @PutMapping("updateRole")
    @Log(title = "角色管理",content = "修改角色")
    public Result updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return Result.ok();
    }

    @Operation(summary = "根据id获取角色详情（回显）")
    @GetMapping("getRoleById/{id}")
    @Log(title = "角色管理",content = "根据id获取角色详情（回显）")
    public Result<Role> getRoleById(@PathVariable Long id) {
        return Result.ok(roleService.getById(id));
    }

    @Operation(summary = "获取角色列表")
    @GetMapping("getRoleList")
    @Log(title = "角色管理",content = "获取角色列表")
    public Result<List<Role>> getRoleList() {
        return Result.ok(roleService.getList());
    }

}
