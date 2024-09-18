package com.linyilinyi.user.controller;

import com.linyilinyi.user.service.UserHomeService;
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
@RestController
@RequestMapping("userHome")
public class UserHomeController {

    @Autowired
    private UserHomeService userHomeService;
}
