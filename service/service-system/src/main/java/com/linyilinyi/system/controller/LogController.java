package com.linyilinyi.system.controller;

import com.alibaba.fastjson2.JSON;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.log.OperLog;
import com.linyilinyi.model.vo.log.OperLogQueryVo;
import com.linyilinyi.system.client.SystemClient;
import com.linyilinyi.system.service.OperLogService;
import com.linyilinyi.user.client.UserClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;

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

    @Resource
    private UserClient userClient;
    @Resource
    private HttpServletRequest request;

    @Operation(summary = "操作日志记录")
    @PostMapping("add")
    public void operLog(@RequestBody OperLog operLog) {
        try {
            operLogService.save(operLog);
        } catch (Exception e) {
            throw new LinyiException("日志记录出错" + e.getMessage());
        }
    }

    @Operation(summary = "分页查询日志记录")
    @PostMapping("page")
    @Log(title = "操作日志记录管理", content = "分页查询日志记录")
    public Result<PageResult<OperLog>> pageList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                @RequestBody OperLogQueryVo operLogQueryVo) {
        return Result.ok(operLogService.pageList(operLogQueryVo, pageNo, pageSize));
    }

    @Operation(summary = "通过token信息获取用户ID")
    @GetMapping(value = "/getByToken")
    public Result<Integer> getByToken() {
        return Result.ok(operLogService.getByToken(request));
    }

    @GetMapping("/getRequesUserId")
    public Result<Integer> getRequesUserId() {
        Enumeration<String> userid = request.getHeaders("userid");
        return Result.ok(Integer.parseInt(userid.nextElement()));
    }
}
