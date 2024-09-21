package com.linyilinyi.article.controller;

import com.linyilinyi.article.service.ArticleDataService;
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
@RequestMapping("articleData")
public class ArticleDataController {

    @Resource
    private ArticleDataService articleDataService;
}
