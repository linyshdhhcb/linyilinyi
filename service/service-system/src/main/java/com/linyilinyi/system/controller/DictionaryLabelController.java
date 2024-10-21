package com.linyilinyi.system.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.dictionary.DictionaryLabel;
import com.linyilinyi.model.vo.article.ArticleQueryVo;
import com.linyilinyi.model.vo.dictionary.DictionaryLabelAddVo;
import com.linyilinyi.model.vo.dictionary.DictionaryLabelQueryVo;
import com.linyilinyi.system.service.DictionaryLabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.DispatcherType;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
@SuppressWarnings({"unchecked", "rawtypes"})
public class DictionaryLabelController {

    @Resource
    private DictionaryLabelService dictionaryLabelService;

    @Operation(summary = "数据字典内容表分页查询")
    @PostMapping("page")
    public Result<PageResult<DictionaryLabel>> pageList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                       @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                       @RequestBody DictionaryLabelQueryVo dictionaryLabelQueryVo) {
       return Result.ok(dictionaryLabelService.pageList(pageNo,pageSize,dictionaryLabelQueryVo));
    }

    @Operation(summary = "数据字典内容表新增")
    @GetMapping("addDictionaryLabel")
    public Result<String> addDictionaryLabel(@RequestBody DictionaryLabelAddVo dictionaryLabelAddVo) {
        return Result.ok(dictionaryLabelService.addDictionaryLabel(dictionaryLabelAddVo));
    }

    @Operation(summary = "数据字典内容表删除")
    @DeleteMapping("deleteDictionaryLabel/{ids}")
    public Result<String> deleteDictionaryLabel(@PathVariable List<Integer> ids) {
        return Result.ok(dictionaryLabelService.deleteDictionaryLabel(ids));
    }

    @Operation(summary = "数据字典内容表修改")
    @PutMapping("updateDictionaryLabel")
    public Result<String> updateDictionaryLabel(@RequestBody DictionaryLabel dictionaryLabel) {
        return Result.ok(dictionaryLabelService.updateDictionaryLabel(dictionaryLabel));
    }

    @Operation(summary = "根据id获取数据字典内容")
    @GetMapping("getDictionaryLabelById/{id}")
    public Result<DictionaryLabel> getDictionaryLabelById(@PathVariable Integer id) {
        return Result.ok(dictionaryLabelService.getById(id));
    }
}
