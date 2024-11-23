package com.linyilinyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.dictionary.DictionaryLabel;
import com.linyilinyi.model.entity.dictionary.DictionaryType;
import com.linyilinyi.model.vo.dictionary.DictionaryLabelAddVo;
import com.linyilinyi.model.vo.dictionary.DictionaryLabelQueryVo;
import com.linyilinyi.model.vo.dictionary.DictionaryTypeTreeList;
import com.linyilinyi.system.mapper.DictionaryLabelMapper;
import com.linyilinyi.system.service.DictionaryLabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.system.service.DictionaryTypeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Resource
    private DictionaryTypeService dictionaryTypeService;

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
        dictionaryLabel.setCreateUserId(AuthContextUser.getUserId());
        if (dictionaryLabelMapper.insert(dictionaryLabel) != 1) {
            throw new RuntimeException("添加失败");
        }
        return "添加成功";
    }

    @Override
    public String deleteDictionaryLabel(List<Integer> ids) {
        ids = ids.stream().filter(id -> id > 0).toList();
        int i = dictionaryLabelMapper.deleteBatchIds(ids);
        if (i <= 0) {
            throw new LinyiException("删除失败");
        }
        return i + "条数据删除成功";
    }

    @Override
    public String updateDictionaryLabel(DictionaryLabel dictionaryLabel) {
        dictionaryLabel.setUpdateTime(LocalDateTime.now());
        dictionaryLabel.setUpdateUserId(AuthContextUser.getUserId());
        int i = dictionaryLabelMapper.updateById(dictionaryLabel);
        if (i != 1) {
            throw new LinyiException("修改失败");
        }
        return "修改成功";
    }

    @Override
    public List<DictionaryTypeTreeList> getDictionaryLabelTreeList(Integer dictionaryTypeId) {
        List<DictionaryTypeTreeList> dictionaryTypeTreeLists = new ArrayList<>();
        if (Optional.ofNullable(dictionaryTypeId).isPresent()) {
            DictionaryType byId = dictionaryTypeService.getById(dictionaryTypeId);
            DictionaryTypeTreeList dictionaryTypeTreeList = new DictionaryTypeTreeList();
            BeanUtils.copyProperties(byId, dictionaryTypeTreeList);
            List<DictionaryLabel> dictionaryLabels = dictionaryLabelMapper.selectList(new LambdaQueryWrapper<DictionaryLabel>().eq(DictionaryLabel::getDictionaryId, dictionaryTypeId));
            dictionaryTypeTreeList.setDictionaryLabelChild(dictionaryLabels);
            dictionaryTypeTreeLists.add(dictionaryTypeTreeList);
        } else if (Optional.ofNullable(dictionaryTypeId).isEmpty()) {
            List<DictionaryType> dictionaryTypeList = dictionaryTypeService.getDictionaryTypeList();
            dictionaryTypeList.stream().forEach(e -> {
                List<DictionaryLabel> dictionaryLabels = dictionaryLabelMapper.selectList(new LambdaQueryWrapper<DictionaryLabel>().eq(DictionaryLabel::getDictionaryId, e.getId()));
                DictionaryTypeTreeList dictionaryTypeTreeList = new DictionaryTypeTreeList();
                BeanUtils.copyProperties(e, dictionaryTypeTreeList);
                dictionaryTypeTreeList.setDictionaryLabelChild(dictionaryLabels);
                dictionaryTypeTreeLists.add(dictionaryTypeTreeList);

            });
            return dictionaryTypeTreeLists;
        }
        throw new LinyiException("参数错误");
    }
}
