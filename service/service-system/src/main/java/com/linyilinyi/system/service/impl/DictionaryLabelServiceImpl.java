package com.linyilinyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.dictionary.DictionaryLabel;
import com.linyilinyi.model.vo.dictionary.DictionaryLabelAddVo;
import com.linyilinyi.model.vo.dictionary.DictionaryLabelQueryVo;
import com.linyilinyi.system.mapper.DictionaryLabelMapper;
import com.linyilinyi.system.service.DictionaryLabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 数据字典内容表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class DictionaryLabelServiceImpl extends ServiceImpl<DictionaryLabelMapper, DictionaryLabel> implements DictionaryLabelService {
    @Resource
    private DictionaryLabelMapper dictionaryLabelMapper;

    @Override
    public PageResult<DictionaryLabel> pageList(long pageNo, long pageSize, DictionaryLabelQueryVo dictionaryLabelQueryVo) {
        LambdaQueryWrapper<DictionaryLabel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(dictionaryLabelQueryVo.getLabel()), DictionaryLabel::getLabel, dictionaryLabelQueryVo.getLabel());
        queryWrapper.eq(Optional.ofNullable(dictionaryLabelQueryVo.getCode()).isPresent(), DictionaryLabel::getCode, dictionaryLabelQueryVo.getCode());
        queryWrapper.eq(Optional.ofNullable(dictionaryLabelQueryVo.getDictionaryId()).isPresent(), DictionaryLabel::getDictionaryId, dictionaryLabelQueryVo.getDictionaryId());
        queryWrapper.eq(Optional.ofNullable(dictionaryLabelQueryVo.getCreateUserId()).isPresent(), DictionaryLabel::getCreateUserId, dictionaryLabelQueryVo.getCreateUserId());
        queryWrapper.eq(Optional.ofNullable(dictionaryLabelQueryVo.getUpdateUserId()).isPresent(), DictionaryLabel::getUpdateUserId, dictionaryLabelQueryVo.getUpdateUserId());
        queryWrapper.gt(Optional.ofNullable(dictionaryLabelQueryVo.getStartTime()).isPresent(), DictionaryLabel::getCreateTime, dictionaryLabelQueryVo.getStartTime());
        queryWrapper.lt(Optional.ofNullable(dictionaryLabelQueryVo.getEndTime()).isPresent(), DictionaryLabel::getCreateTime, dictionaryLabelQueryVo.getEndTime());
        Page<DictionaryLabel> dictionaryLabelPage = new Page<>(pageNo, pageSize);
        Page<DictionaryLabel> labelPage = dictionaryLabelMapper.selectPage(dictionaryLabelPage, queryWrapper);
        return new PageResult<>(labelPage.getRecords(), labelPage.getTotal(), pageNo, pageSize);
    }

    @Override
    public String addDictionaryLabel(DictionaryLabelAddVo dictionaryLabelAddVo) {
        DictionaryLabel dictionaryLabel = new DictionaryLabel();
        BeanUtils.copyProperties(dictionaryLabelAddVo, dictionaryLabel);
        dictionaryLabel.setCreateTime(LocalDateTime.now());
        if (dictionaryLabelMapper.insert(dictionaryLabel) != 1) {
            throw new RuntimeException("添加失败");
        }
        return "添加成功";
    }

    @Override
    public String deleteDictionaryLabel(List<Integer> ids) {
        ids=ids.stream().filter(id -> id > 0).toList();
        int i = dictionaryLabelMapper.deleteBatchIds(ids);
        if (i<=0){
            throw new LinyiException("删除失败");
        }
        return i+"条数据删除成功";
    }


}
