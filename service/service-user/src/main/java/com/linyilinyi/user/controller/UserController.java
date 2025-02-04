package com.linyilinyi.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.model.vo.code.Code;
import com.linyilinyi.model.vo.user.*;
import com.linyilinyi.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
    public Result<User> getUserById(@Valid @PathVariable Integer id) {
        return Result.ok(userService.getUserById(id));
    }

    @Operation(summary = "根据id删除用户信息")
    @GetMapping("/deleteUserById/{ids}")
    @Log(title = "用户管理", content = "根据id删除用户信息")
    public Result<String> deleteUserById(@Valid @PathVariable List<Integer> ids) {
        return Result.ok(userService.deleteUserById(ids));
    }

    @Operation(summary = "根据id修改用户信息（不能修改password字段）")
    @PostMapping("/updateUser")
    @Log(title = "用户管理", content = "根据id修改用户信息")
    public Result<String> updateUser(@RequestBody UserUpdateVo user) {
        return Result.ok(userService.updateUser(user));
    }

    @Operation(summary = "添加用户")
    @PostMapping("addUser")
    @Log(title = "用户管理", content = "添加用户")
    public Result<String> addUser(@Valid @RequestBody UserAddVo userAddVo) {
        return Result.ok(userService.addUser(userAddVo));
    }

    @Operation(summary = "查询用户列表")
    @PostMapping("/getUserList")
    @Log(title = "用户管理", content = "查询用户列表")
    public Result<PageResult<User>> getUserList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                @RequestBody UserQueryVo userQueryVo,
                                                @RequestHeader(value = "USERID", required = false) String userId) {
        return Result.ok(userService.getUserList(pageNo, pageSize, userQueryVo));
    }

    @Operation(summary = "根据用户名查询用户信息")
    @GetMapping("/getByUsername/{username}")
    @Log(title = "用户管理", content = "根据用户名查询用户信息")
    public Result<User> getByUsername(@NotBlank(message = "账号不能为空") @PathVariable String username) {
        return Result.ok(userService.getByUsername(username));
    }

    @Operation(summary = "用户登录（成功返回token）")
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        log.info("登录请求：{}", loginVo);
        return Result.ok(userService.login(loginVo));
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    @Log(title = "用户管理", content = "用户注册")
    public Result<String> register(@RequestBody UserRegisterVo userRegisterVo) {
        return Result.ok(userService.register(userRegisterVo));
    }

    @Operation(summary = "获取用户权限")
    @GetMapping("/getUserPermissions")
    @Log(title = "用户管理", content = "获取用户权限")
    public Result<List<String>> getUserPermissions(@RequestParam String username) {
        return null;
    }


    @Operation(summary = "查询登录状态(登录：true，未登录：false)")
    @GetMapping("/isLogin")
    @Log(title = "用户管理", content = "查询登录状态(登录：true，未登录：false)")
    public Result<Boolean> isLogin() {
        boolean b = StpUtil.isLogin() ? true : false;
        return Result.ok(b);
    }

    @Operation(summary = "获取token")
    @GetMapping("/getToken")
    public Result<String> tokenInfo() {
        return Result.ok(StpUtil.getTokenValue());
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    @Log(title = "用户管理", content = "用户登出")
    public Result<String> logout() {
        AuthContextUser.removeUserId();
        StpUtil.logout();
        return Result.ok("退出成功");
    }

    @Operation(summary = "忘记密码")
    @PostMapping("/forgetPassword")
    @Log(title = "用户管理", content = "忘记密码")
    public Result<String> forgetPassword(@RequestBody ForgetPasswordVo forgetPasswordVo) {
        return Result.ok(userService.forgetPassword(forgetPasswordVo));
    }

    @Operation(summary = "获取用户注册验证码")
    @GetMapping("/getRegisterCode")
    @Log(title = "用户管理", content = "获取用户注册验证码")
    public Result<Code> getRegisterCode(@RequestParam String mail) {
        return Result.ok(userService.getRegisterCode(mail));
    }

    @Operation(summary = "获取用户验证码")
    @GetMapping("/getUserCode")
    @Log(title = "用户管理", content = "获取用户验证码")
    public Result<Code> getUserCode(@RequestParam String mail) {
        return Result.ok(userService.getUserCode(mail));
    }

}