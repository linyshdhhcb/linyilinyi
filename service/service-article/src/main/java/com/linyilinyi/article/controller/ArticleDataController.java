package com.linyilinyi.article.controller;

import com.linyilinyi.article.service.ArticleDataService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.article.ArticleData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 文章数据统计表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@RestController
@Tag(name = "文章数据统计管理")
@RequestMapping("articleData")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ArticleDataController {

    @Resource
    private ArticleDataService articleDataService;

    @Operation(summary = "分页获取全部文章数据列表")
    @PostMapping("getArticleDataList")
    @Log(title = "文章数据统计管理",content = "分页获取全部文章数据列表")
    public Result<PageResult<ArticleData>> getArticleDataList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                              @RequestParam(required = false, defaultValue = "10") long pageSize){
        return Result.ok(articleDataService.getArticleDataList(pageNo, pageSize));
    }

    @Operation(summary = "根据文章ID获取文章数据")
    @PostMapping("getArticleDataById/{id}")
    @Log(title = "文章数据统计管理",content = "根据文章ID获取文章数据")
    public Result<ArticleData> getArticleDataById(@PathVariable Integer id){
        return Result.ok(articleDataService.getArticleDataById(id));
    }

    @Operation(summary = "修改文章数据")
    @PostMapping("updateArticleData")
    @Log(title = "文章数据统计管理",content = "修改文章数据")
    public Result<String> updateArticleData(@RequestBody ArticleData articleData){
        return Result.ok(articleDataService.updateArticleData(articleData));
    }

    @Operation(summary = "添加文章数据")
    @PostMapping("addArticleData")
    @Log(title = "文章数据统计管理",content = "添加文章数据")
    public Result<String> addArticleData(@RequestParam Integer id,@RequestParam Integer status){
        return Result.ok(articleDataService.addArticleData(id,status));
    }


}
