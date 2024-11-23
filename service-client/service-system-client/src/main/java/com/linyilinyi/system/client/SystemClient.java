package com.linyilinyi.system.client;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.log.OperLog;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/8
 * @ClassName: SystemClient
 */
@FeignClient(value = "service-system")
public interface SystemClient {

    @PostMapping("/log/add")
    public void operLog(@RequestBody OperLog operLog);


    @GetMapping(value = "/log/getByToken")
    public Result<Integer> getByToken(HttpServletRequest request);
}
