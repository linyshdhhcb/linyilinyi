package com.linyilinyi.system.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.dictionary.DictionaryType;
import com.linyilinyi.system.service.DictionaryTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p>
 * 数据字典类型表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Tag(name = "数据字典类型表")
@Slf4j
@Validated
@RestController
@RequestMapping("dictionaryType")
@SuppressWarnings({"unchecked", "rawtypes"})
public class DictionaryTypeController {

    @Resource
    private DictionaryTypeService dictionaryTypeService;

    @Operation(summary = "数据字典类型表新增")
    @PostMapping("addDictionaryType")
    public Result<String> addDictionaryType(@RequestParam String type,@RequestParam String name) {
        return Result.ok(dictionaryTypeService.addDictionaryType(type, name));
    }

    @Operation(summary = "数据字典类型表删除")
    @DeleteMapping("deleteDictionaryType/{ids}")
    public Result<String> deleteDictionaryType(@PathVariable List<Integer> ids) {
        return Result.ok(dictionaryTypeService.deleteDictionaryType(ids));
    }

    @Operation(summary = "根据id获取数据字典类型")
    @GetMapping("getDictionaryTypeById")
    public Result<DictionaryType> getDictionaryTypeById(@Positive(message="正整数") @RequestParam Integer id) {
        return Result.ok(dictionaryTypeService.getById(id));
    }

    @Operation(summary = "修改数据字典类型")
    @PutMapping("updateDictionaryType")
    public Result<String> updateDictionaryType(@RequestBody DictionaryType dictionaryType) {
        return Result.ok(dictionaryTypeService.updateDictionaryType(dictionaryType));
    }

    @Operation(summary = "获取全部数据类型列表")
    @GetMapping("getDictionaryTypeList")
    public Result<List<DictionaryType>> getDictionaryTypeList() {
        return Result.ok(dictionaryTypeService.getDictionaryTypeList());
    }
}
