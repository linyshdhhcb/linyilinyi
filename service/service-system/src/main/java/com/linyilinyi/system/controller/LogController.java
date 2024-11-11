package com.linyilinyi.system.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.log.OperLog;
import com.linyilinyi.model.vo.log.OperLogQueryVo;
import com.linyilinyi.system.service.OperLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/8
 * @ClassName: LogController
 */
@Tag(name = "操作日志记录管理")
@Slf4j
@RestController
@RequestMapping("/log")
@SuppressWarnings({"unchecked", "rawtypes"})
public class LogController {

    @Resource
    private OperLogService operLogService;

    @Operation(summary = "操作日志记录")
    @PostMapping("add")
    @Log(title = "操作日志记录管理",content = "操作日志记录")
    public void operLog(@RequestBody OperLog operLog){
        operLogService.save(operLog);
    }

    @Operation(summary = "分页查询日志记录")
    @PostMapping("page")
    @Log(title = "操作日志记录管理",content = "分页查询日志记录")
    public Result<PageResult<OperLog>> pageList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                       @RequestParam(required = false, defaultValue = "5") long pageSize,
                                       @RequestBody OperLogQueryVo operLogQueryVo){
        return Result.ok(operLogService.pageList(operLogQueryVo,pageNo,pageSize));
    }
}
