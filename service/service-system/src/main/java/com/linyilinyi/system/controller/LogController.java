package com.linyilinyi.system.controller;

import com.linyilinyi.model.entity.log.OperLog;
import com.linyilinyi.system.service.OperLogService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/8
 * @ClassName: LogController
 */
@Slf4j
@RestController
@RequestMapping("/log")
@SuppressWarnings({"unchecked", "rawtypes"})
public class LogController {

    @Resource
    private OperLogService operLogService;

    @Operation(summary = "操作日志记录")
    @PostMapping("add")
    public void operLog(@RequestBody OperLog operLog){
        operLogService.save(operLog);
    }
}
