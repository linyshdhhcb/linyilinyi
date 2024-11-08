package com.linyilinyi.system.client;

import com.linyilinyi.model.entity.log.OperLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/8
 * @ClassName: SystemClient
 */
@FeignClient(name = "service-system")
public interface SystemClient {

    @PostMapping("/log/add")
    public void operLog(@RequestBody OperLog operLog);
}
