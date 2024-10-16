package com.linyilinyi.user.client;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.model.vo.user.LoginVo;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/17
 * @ClassName: UserClient
 */
@FeignClient(value = "service-user")
public interface UserClient {

    @GetMapping("/user/getUserById/{id}")
    public Result<User> getUserById(@PathVariable Integer id);

    @GetMapping("/user/getByUsername/{username}")
    public Result<User> getByUsername(@PathVariable String username);

    @PostMapping("/user/login")
    public Result<String> login(@RequestBody LoginVo loginVo);

    @GetMapping("/user/getUserPermissions")
    public Result<List<String>> getUserPermissions(@RequestParam String username);


}
