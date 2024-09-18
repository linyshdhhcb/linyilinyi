package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}