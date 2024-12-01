package com.linyilinyi.system.client;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.log.OperLog;
import com.linyilinyi.model.vo.log.OperLogQueryVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.CompletableFuture;

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

    @PostMapping("/log/page")
    public Result<PageResult<OperLog>> pageList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                @RequestBody OperLogQueryVo operLogQueryVo);
}
