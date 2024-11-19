package com.linyilinyi.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/19
 * @ClassName: DataController
 */
@Tag(name = "数据管理")
@Slf4j
@Validated
@RestController
@RequestMapping("dictionaryLabel")
@SuppressWarnings({"unchecked", "rawtypes"})
public class DataController {
}
