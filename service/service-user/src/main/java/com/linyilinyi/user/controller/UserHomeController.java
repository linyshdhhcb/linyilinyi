package com.linyilinyi.user.controller;

import com.linyilinyi.user.service.UserHomeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 用户主页表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "用户信息管理模块")
@RestController
@RequestMapping("userHome")
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserHomeController {

    @Resource
    private UserHomeService userHomeService;
}
