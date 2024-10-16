package com.linyilinyi.user.controller;

import com.linyilinyi.user.service.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "菜单管理模块")
@RestController
@RequestMapping("menu")
public class MenuController {

    @Resource
    private MenuService menuService;
}
