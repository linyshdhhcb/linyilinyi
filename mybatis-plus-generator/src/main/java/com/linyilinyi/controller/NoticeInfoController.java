package com.linyilinyi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.linyilinyi.service.NoticeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 存储各种信息的通用表，包括私信、通知、公告和客服消息 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@RestController
@RequestMapping("noticeInfo")
public class NoticeInfoController {

    @Autowired
    private NoticeInfoService  noticeInfoService;
}
