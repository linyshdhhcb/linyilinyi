package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.model.vo.user.UserAddVo;
import com.linyilinyi.model.vo.user.UserQueryVo;
import com.linyilinyi.model.vo.user.UserUpdateVo;
import com.linyilinyi.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "用户管理接口")
@RequestMapping("user")
@Validated
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserController {

    @Resource
    private UserService userService;

    @Operation(summary = "根据id查询用户信息")
    @GetMapping("/getUserById/{id}")
    public Result<User> getUserById(@Valid @PathVariable Integer id){
        return Result.ok(userService.getUserById(id));
    }

    @Operation(summary = "根据id删除用户信息")
    @GetMapping("/deleteUserById/{ids}")
    public Result<String> deleteUserById(@Valid @PathVariable List<Integer> ids){
        return Result.ok(userService.deleteUserById(ids));
    }

    @Operation(summary = "根据id修改用户信息（不能修改password字段）")
    @PostMapping("/updateUser")
    public Result<String> updateUser(@RequestBody UserUpdateVo user){
        return Result.ok(userService.updateUser(user));
    }

    @Operation(summary = "添加用户")
    @PostMapping("addUser")
    public Result<String> addUser(@Valid @RequestBody UserAddVo userAddVo){
        return Result.ok(userService.addUser(userAddVo));
    }

    @Operation(summary = "查询用户列表")
    @GetMapping("/getUserList")
    public Result<PageResult<User>> getUserList(@RequestParam(required = false,defaultValue = "1") long pageNo,
                                                     @RequestParam(required = false,defaultValue = "5") long pageSize,
                                                     @RequestBody UserQueryVo userQueryVo){
        return Result.ok(userService.getUserList(pageNo,pageSize,userQueryVo));
    }
}