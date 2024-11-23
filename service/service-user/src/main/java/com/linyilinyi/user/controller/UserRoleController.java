package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.user.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "用户角色管理模块")
@RestController
@RequestMapping("userRole")
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    @Operation(summary = "添加用户角色")
    @PostMapping("addUserRole")
    @Log(title = "用户角色管理", content = "添加用户角色")
    public Result<String> addUserRole(@RequestParam Integer userId, @RequestParam Long roleId) {
        return Result.ok(userRoleService.addUserRole(userId, roleId));
    }

    @Operation(summary = "删除用户角色")
    @PostMapping("deleteUserRoleById/{ids}")
    @Log(title = "用户角色管理", content = "删除用户角色")
    public Result<String> deleteUserRoleById(@PathVariable List<Long> ids) {
        return Result.ok(userRoleService.deleteUserRoleById(ids));
    }

    @Operation(summary = "根据用户id获取用户角色列表")
    @GetMapping("getUserRoleList")
    //@Log(title = "用户角色管理", content = "根据用户id获取用户角色列表")
    public Result<List<Role>> getUserRoleList(@RequestParam Integer userId) {
        return Result.ok(userRoleService.getUserRoleList(userId));
    }

}
