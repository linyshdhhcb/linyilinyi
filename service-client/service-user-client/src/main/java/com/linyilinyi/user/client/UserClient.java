package com.linyilinyi.user.client;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.user.User;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
