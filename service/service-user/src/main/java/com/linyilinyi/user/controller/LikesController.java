package com.linyilinyi.user.controller;

import com.linyilinyi.user.service.LikesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 点赞表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "点赞管理模块")
@RestController
@RequestMapping("likes")
@SuppressWarnings({"unchecked", "rawtypes"})
public class LikesController {

    @Resource
    private LikesService likesService;
}
