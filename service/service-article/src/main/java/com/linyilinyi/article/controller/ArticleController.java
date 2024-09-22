package com.linyilinyi.article.controller;

import com.linyilinyi.article.service.ArticleService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.vo.article.ArticleAddVo;
import com.linyilinyi.model.vo.article.ArticleQueryVo;
import com.linyilinyi.model.vo.article.ArticleUpdateAdd;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
@RequestMapping("/article")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Operation(summary = "分页获取文章列表")
    @RequestMapping("/getArticleList")
    public Result<PageResult<Article>> getArticleList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                      @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                      @RequestBody ArticleQueryVo articleQueryVo) {
        return Result.ok(articleService.getArticleList(pageNo, pageSize, articleQueryVo));
    }


    @Operation(summary = "添加文章")
    @RequestMapping("/addArticle")
    public Result<String> addArticle(@RequestBody ArticleAddVo articleAddVo) {
        return Result.ok(articleService.addArticle(articleAddVo));
    }

    @Operation(summary = "删除文章")
    @RequestMapping("/deleteArticle/{ids}")
    public Result<String> deleteArticle(@PathVariable List<Integer> ids) {
        return Result.ok(articleService.deleteArticle(ids));
    }

//    @Operation(summary = "修改文章")
//    @RequestMapping("/updateArticle")
//    public Result<String> updateArticle(@RequestBody ArticleUpdateAdd articleUpdateAdd) {
//        return Result.ok(articleService.updateArticle(articleUpdateAdd));
//    }
}
