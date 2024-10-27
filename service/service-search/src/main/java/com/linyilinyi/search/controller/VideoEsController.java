package com.linyilinyi.search.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.vo.article.ArticleEsQueryVo;
import com.linyilinyi.model.vo.video.VideoEsQueryVo;
import com.linyilinyi.model.vo.video.VideoQueryVo;
import com.linyilinyi.search.service.VideoEsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/24
 * @ClassName: VideoEsController
 */
@RestController
@RequestMapping("/es/search")
@SuppressWarnings({"unchecked", "rawtypes"})
public class VideoEsController {

    @Resource
    private VideoEsService videoEsService;

    @Operation(summary = "添加视频索引")
    @PostMapping("/addVideo")
    public Result<String> addVideoDoc() throws IOException {
        return Result.ok(videoEsService.addVideoDoc());
    }

    @Operation(summary = "添加文章索引")
    @PostMapping("/addArticle")
    public Result<String> addArticle() throws IOException {
        return Result.ok(videoEsService.addArticleDoc());
    }

    @Operation(summary = "DSL查询全部")
    @GetMapping("/search/{keyword}")
    public Result<List<Map<String, Object>>> search(@PathVariable String keyword) throws IOException {
        return Result.ok(videoEsService.searchAll(keyword));
    }

    @Operation(summary = "DSL查询视频")
    @PostMapping("/searchVideo")
    public Result<List<Map<String, Object>>> searchVideo(@RequestBody VideoEsQueryVo videoQueryVo) throws IOException {
        return Result.ok(videoEsService.searchVideo(videoQueryVo));
    }

    @Operation(summary = "DSL查询文章")
    @PostMapping("/searchArticle")
    public Result<List<Map<String, Object>>> search(@RequestBody ArticleEsQueryVo articleEsQueryVo) throws IOException {
        return Result.ok(videoEsService.searchArticle(articleEsQueryVo));
    }


}
