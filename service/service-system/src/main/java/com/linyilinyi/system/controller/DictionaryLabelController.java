package com.linyilinyi.system.controller;

import com.linyilinyi.system.service.DictionaryLabelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 数据字典内容表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Tag(name = "数据字典内容管理器")
@Slf4j
@RestController
@RequestMapping("dictionaryLabel")
public class DictionaryLabelController {

    @Resource
    private DictionaryLabelService dictionaryLabelService;
}
