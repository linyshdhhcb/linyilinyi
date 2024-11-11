package com.linyilinyi.search.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.video.Video;
import com.linyilinyi.model.vo.article.ArticleEsQueryVo;
import com.linyilinyi.model.vo.user.UserQueryVo;
import com.linyilinyi.model.vo.video.VideoEsQueryVo;
import com.linyilinyi.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/24
 * @ClassName: SearchController
 */
@Tag(name = "搜索管理")
@RestController
@RequestMapping("/es/search")
@SuppressWarnings({"unchecked", "rawtypes"})
public class SearchController {

    @Resource
    private SearchService videoEsService;

    @Operation(summary = "添加视频索引")
    @PostMapping("/addVideo")
    @Log(title = "视频管理",content = "添加视频索引")
    public Result<String> addVideoDoc() throws IOException {
        return Result.ok(videoEsService.addVideoDoc());
    }

    @Operation(summary = "添加文章索引")
    @PostMapping("/addArticle")
    @Log(title = "文章管理",content = "添加文章索引")
    public Result<String> addArticle() throws IOException {
        return Result.ok(videoEsService.addArticleDoc());
    }

    @Operation(summary = "用户搜索")
    @PostMapping("/addUser")
    @Log(title = "用户管理",content = "添加用户索引")
    public Result<String> addUser() throws IOException {
        return Result.ok(videoEsService.addUser());
    }

    @Operation(summary = "DSL查询全部")
    @GetMapping("/search/{keyword}")
    @Log(title = "搜索管理",content = "DSL查询全部")
    public Result<List<Map<String, Object>>> search(@PathVariable String keyword) throws IOException {
        return Result.ok(videoEsService.searchAll(keyword));
    }

    @Operation(summary = "DSL查询视频")
    @PostMapping("/searchVideo")
    @Log(title = "视频管理",content = "DSL查询视频")
    public Result<List<Map<String, Object>>> searchVideo(@RequestBody VideoEsQueryVo videoQueryVo) throws IOException {
        return Result.ok(videoEsService.searchVideo(videoQueryVo));
    }

    @Operation(summary = "DSL查询文章")
    @PostMapping("/searchArticle")
    @Log(title = "文章管理",content = "DSL查询文章")
    public Result<List<Map<String, Object>>> search(@RequestBody ArticleEsQueryVo articleEsQueryVo) throws IOException {
        return Result.ok(videoEsService.searchArticle(articleEsQueryVo));
    }

    @Operation(summary = "DSL查询用户")
    @PostMapping("/searchUser")
    @Log(title = "用户管理",content = "DSL查询用户")
    public Result<List<Map<String, Object>>> searchUser(@RequestBody UserQueryVo userQueryVo) throws IOException {
        return Result.ok(videoEsService.searchUser(userQueryVo));
    }

    @Operation(summary = "获取视频在es中最新的数据")
    @GetMapping("/getLatestVideo")
    @Log(title = "视频管理",content = "获取视频在es中最新的数据")
    public Result<Video> getLatestVideo() throws IOException {
        return Result.ok(videoEsService.getLatestVideo());
    }


}
