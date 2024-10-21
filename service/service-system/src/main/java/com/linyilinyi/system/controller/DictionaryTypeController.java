package com.linyilinyi.system.controller;

import com.linyilinyi.system.service.DictionaryTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 数据字典类型表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Tag(name = "数据字典类型表")
@Slf4j
@RestController
@RequestMapping("dictionaryType")
@SuppressWarnings({"unchecked", "rawtypes"})
public class DictionaryTypeController {

    @Resource
    private DictionaryTypeService dictionaryTypeService;
}
