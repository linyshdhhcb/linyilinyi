package com.linyilinyi.article.controller;

import com.linyilinyi.article.service.ArticleDataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 文章数据统计表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@RestController
@Tag(name = "文章数据统计表")
@RequestMapping("articleData")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ArticleDataController {

    @Resource
    private ArticleDataService articleDataService;
}
