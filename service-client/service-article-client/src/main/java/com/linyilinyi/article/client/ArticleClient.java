package com.linyilinyi.article.client;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.vo.article.ArticleQueryVo;
import io.swagger.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/26
 * @ClassName: ArticleClient
 */
@FeignClient(value = "service-article")
public interface ArticleClient {

    /**
     * 分页查询文章列表
     * @param pageNo 页数
     * @param pageSize 每页数量
     * @param articleQueryVo 查询参数
     * @return
     */
    @PostMapping("/article/getArticleList")
    public Result<PageResult<Article>> getArticleList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                      @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                      @RequestBody ArticleQueryVo articleQueryVo);
}
