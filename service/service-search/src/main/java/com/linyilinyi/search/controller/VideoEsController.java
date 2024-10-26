package com.linyilinyi.search.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.vo.article.ArticleQueryVo;
import com.linyilinyi.model.vo.video.VideoEsVo;
import com.linyilinyi.model.vo.video.VideoQueryVo;
import com.linyilinyi.search.service.VideoEsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
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
@RequestMapping("/es/video")
@SuppressWarnings({"unchecked", "rawtypes"})
public class VideoEsController {

    @Resource
    private VideoEsService videoEsService;

    @PostMapping("/add")
    public String addVideoDoc() throws IOException {
        return videoEsService.addVideoDoc();
    }

    @Operation(summary = "DSL查询全部")
    @GetMapping("/search/{keyword}")
    public Result<List<Map<String, Object>>> search(@PathVariable String keyword) throws IOException {
        return Result.ok(videoEsService.searchAll(keyword));
    }

    @Operation(summary = "DSL查询视频")
    @PostMapping("/searchVideo")
    public Result<List<Map<String, Object>>> searchVideo(@RequestBody VideoQueryVo videoQueryVo) throws IOException {
        return Result.ok(videoEsService.searchVideo(videoQueryVo));
    }

    @Operation(summary = "DSL查询文章")
    @PostMapping("/searchArticle")
    public Result<List<Map<String, Object>>> search(@RequestBody ArticleQueryVo articleQueryVo) throws IOException {
        return Result.ok(videoEsService.searchArticle(articleQueryVo));
    }


}
