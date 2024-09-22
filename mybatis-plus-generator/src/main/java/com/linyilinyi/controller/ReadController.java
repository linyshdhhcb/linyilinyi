package com.linyilinyi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.linyilinyi.service.ReadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 阅读记录表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@RestController
@RequestMapping("read")
public class ReadController {

    @Autowired
    private ReadService  readService;
}
