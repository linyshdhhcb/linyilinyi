package com.linyilinyi.article.controller;

import com.linyilinyi.article.service.ArticleService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.vo.article.ArticleAddVo;
import com.linyilinyi.model.vo.article.ArticleQueryVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/20
 * @ClassName: ArticleController
 */
@Slf4j
@RestController
@Tag(name = "文章信息管理")
@RequestMapping("/article")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ArticleController {

    @Resource
    private ArticleService articleService;


    @Operation(summary = "分页获取文章列表")
    @PostMapping("/getArticleList")
    @Log(title = "文章管理",content = "分页获取文章列表")
    public Result<PageResult<Article>> getArticleList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                      @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                      @RequestBody ArticleQueryVo articleQueryVo) {
        return Result.ok(articleService.getArticleList(pageNo, pageSize, articleQueryVo));
    }

    @Operation(summary = "根据id获取文章信息（回显使用）")
    @GetMapping("/getArticleById/{id}")
    @Log(title = "文章管理",content = "根据id获取文章信息（回显使用）")
    public Result<Article> getArticleById(@NotNull(message = "id不能为空") @PathVariable Integer id) {
        return Result.ok(articleService.getArticleById(id));
    }


    @Operation(summary = "添加文章")
    @PostMapping("/addArticle")
    @Log(title = "文章管理",content = "添加文章")
    public Result<String> addArticle(@Valid @RequestBody ArticleAddVo articleAddVo) {
        return Result.ok(articleService.addArticle(articleAddVo));
    }

    @Operation(summary = "删除文章")
    @DeleteMapping("/deleteArticle/{ids}")
    @Log(title = "文章管理",content = "逻辑删除文章")
    public Result<String> deleteArticle(@PathVariable List<Integer> ids) {
        return Result.ok(articleService.deleteArticle(ids));
    }

    @Operation(summary = "修改文章")
    @PutMapping("/updateArticle")
    @Log(title = "文章管理",content = "修改文章")
    public Result<String> updateArticle(@Valid @RequestBody Article article) {
        return Result.ok(articleService.updateArticle(article));
    }

    @Operation(summary = "获取逻辑删除文章列表")
    @PostMapping("/getArticleListByIsDelete")
    @Log(title = "文章管理",content = "获取逻辑删除文章列表")
    public Result<PageResult<Article>> getArticleListByIsDelete(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                                @RequestParam(required = false, defaultValue = "5") long pageSize) {
        return Result.ok(articleService.getArticleListByIsDelete(pageNo,pageSize));
    }

    @Operation(summary = "物理删除文章")
    @DeleteMapping("/deleteArticleByPhysical/{ids}")
    @Log(title = "文章管理",content = "物理删除文章")
    public Result<String> deleteArticleByPhysical(@PathVariable List<Integer> ids) {
        return Result.ok(articleService.deleteArticleByPhysical(ids));
    }

}
