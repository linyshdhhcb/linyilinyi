package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.model.vo.user.RoleQueryVo;
import com.linyilinyi.user.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
@Tag(name = "角色管理接口")
@RestController
@RequestMapping("role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Operation(summary = "新增角色")
    @PostMapping("addRole")
    public Result<String> addRole(@RequestParam String name, @RequestParam String code) {
        roleService.addRole(name,code);
        return Result.success(ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除角色")
    @RequestMapping("deleteRoleById")
    public Result<String> deleteRoleById(List<Integer> ids) {
        return Result.ok(roleService.deleteRoleById(ids));
    }

    @Operation(summary = "获取角色列表")
    @RequestMapping("getRoleList")
    public Result<PageResult<Role>> getRoleList(@RequestParam(required = false,defaultValue = "1") long pageNo,
                                                    @RequestParam(required = false,defaultValue = "5") long pageSize,
                                                    RoleQueryVo roleQueryVo) {
        return Result.ok(roleService.getRoleList(pageNo, pageSize, roleQueryVo));
    }

    @Operation(summary = "修改角色")
    @RequestMapping("updateRole")
    public Result updateRole(Role role) {
        roleService.updateRole(role);
        return Result.ok();
    }

    @Operation(summary = "根据id获取角色详情（回显）")
    @RequestMapping("getRoleById")
    public Result<Role> getRoleById(Integer id) {
        return Result.ok(roleService.getById(id));
    }

//    @Operation(summary = "获取角色下拉列表")
//    @RequestMapping("getRoleSelectList")
//    public List<RoleSelectVo> getRoleSelectList() {
//        return roleService.getRoleSelectList();
//    }
//
//    @Operation(summary = "获取角色菜单树")
//    @RequestMapping("getRoleMenuTree")
//    public List<MenuTreeVo> getRoleMenuTree(Integer roleId) {
//        return roleService.getRoleMenuTree(roleId);
//    }
//
//    @Operation(summary = "获取角色权限树")
//    @RequestMapping("getRolePermissionTree")
//    public List<PermissionTreeVo> getRolePermissionTree(Integer roleId) {
//        return roleService.getRolePermissionTree(roleId);
//    }
//
//    @Operation(summary = "获取角色菜单权限树")
//    @RequestMapping("getRoleMenuPermissionTree")
//    public List<MenuPermissionTreeVo> getRoleMenuPermissionTree(Integer roleId) {
//        return roleService.getRoleMenuPermissionTree(roleId);
//    }
//
//    @Operation(summary = "获取角色下拉树")
//    @RequestMapping("getRoleTree")
//    public List<RoleTreeVo> getRoleTree() {
//        return roleService.getRoleTree();
//    }
//
//    @Operation(summary = "获取角色下拉树")
//    @RequestMapping("getRoleTreeByUserId")
//    public List<RoleTreeVo> getRoleTreeByUserId(Integer userId) {
//        return roleService.getRoleTreeByUserId(userId);
//    }




}
