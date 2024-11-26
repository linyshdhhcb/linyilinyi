package com.linyilinyi.system.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.log.annotation.Log;
import com.linyilinyi.model.entity.dictionary.DictionaryLabel;
import com.linyilinyi.model.vo.dictionary.DictionaryLabelAddVo;
import com.linyilinyi.model.vo.dictionary.DictionaryLabelQueryVo;
import com.linyilinyi.model.vo.dictionary.DictionaryTypeTreeList;
import com.linyilinyi.system.service.DictionaryLabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p>
 * 数据字典内容表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Tag(name = "数据字典内容管理")
@Slf4j
@Validated
@RestController
@RequestMapping("dictionaryLabel")
@SuppressWarnings({"unchecked", "rawtypes"})
public class DictionaryLabelController {

    @Resource
    private DictionaryLabelService dictionaryLabelService;

    @Operation(summary = "数据字典内容表分页查询")
    @PostMapping("page")
    @Log(title = "数据字典内容管理器",content = "数据字典内容表分页查询")
    public Result<PageResult<DictionaryLabel>> pageList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                       @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                       @RequestBody DictionaryLabelQueryVo dictionaryLabelQueryVo) {
       return Result.ok(dictionaryLabelService.pageList(pageNo,pageSize,dictionaryLabelQueryVo));
    }

    @Operation(summary = "数据字典内容表新增")
    @PostMapping("addDictionaryLabel")
    @Log(title = "数据字典内容管理器",content = "数据字典内容表新增")
    public Result<String> addDictionaryLabel(@RequestBody DictionaryLabelAddVo dictionaryLabelAddVo) {
        return Result.ok(dictionaryLabelService.addDictionaryLabel(dictionaryLabelAddVo));
    }

    @Operation(summary = "数据字典内容表删除")
    @DeleteMapping("deleteDictionaryLabel/{ids}")
    @Log(title = "数据字典内容管理器",content = "数据字典内容表删除")
    public Result<String> deleteDictionaryLabel(@PathVariable List<Integer> ids) {
        return Result.ok(dictionaryLabelService.deleteDictionaryLabel(ids));
    }

    @Operation(summary = "数据字典内容表修改")
    @PutMapping("updateDictionaryLabel")
    @Log(title = "数据字典内容管理器",content = "数据字典内容表修改")
    public Result<String> updateDictionaryLabel(@RequestBody DictionaryLabel dictionaryLabel) {
        return Result.ok(dictionaryLabelService.updateDictionaryLabel(dictionaryLabel));
    }

    @Operation(summary = "根据id获取数据字典内容")
    @GetMapping("getDictionaryLabelById/{id}")
    @Log(title = "数据字典内容管理器",content = "根据id获取数据字典内容")
    public Result<DictionaryLabel> getDictionaryLabelById(@Positive(message = "id必须为正整数")@PathVariable Integer id) {
        return Result.ok(dictionaryLabelService.getById(id));
    }

    @Operation(summary = "根据数据类型id获取数据字典内容列表（不填获取全部）")
    @GetMapping("getDictionaryLabelTreeList")
    @Log(title = "数据字典内容管理器",content = "根据数据类型id获取数据字典内容列表（不填获取全部）")
    public Result<List<DictionaryTypeTreeList>> getDictionaryLabelTreeList(@RequestParam(required = false) Integer dictionaryTypeId) {
        return Result.ok(dictionaryLabelService.getDictionaryLabelTreeList(dictionaryTypeId));
    }

}
