package com.linyilinyi.article.controller;

import com.linyilinyi.article.service.ReadService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.article.Read;
import com.linyilinyi.model.vo.article.ReadAddVo;
import com.linyilinyi.model.vo.article.ReadQueryVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 阅读记录表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@RestController
@RequestMapping("read")
public class ReadController {

    @Resource
    private ReadService readService;

    @Operation(summary = "分页获取文章历史记录")
    @RequestMapping("getReadList")
    public Result<PageResult<Read>> getReadList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                @RequestBody ReadQueryVo readQueryVo) {
        return Result.ok(readService.getReadList(pageNo, pageSize,readQueryVo));
    }

    @Operation(summary = "获取当前用户全部阅读记录")
    @GetMapping("getReadListByUserId")
    public Result<List<Read>> getReadListByUserId() {
        return Result.ok(readService.getReadListByUserId());
    }

    @Operation(summary = "添加阅读记录")
    @PostMapping("addRead")
    public Result<String> addRead(@RequestBody ReadAddVo readAddVo) {
        return Result.ok(readService.addRead(readAddVo));
    }

    @Operation(summary = "删除阅读记录")
    @DeleteMapping("deleteRead/{ids}")
    public Result<String> deleteRead(@PathVariable List<Integer> ids) {
        return Result.ok(readService.deleteRead(ids));
    }

}
