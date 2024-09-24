package com.linyilinyi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.model.entity.collect.CollectGroup;
import com.linyilinyi.user.mapper.CollectGroupMapper;
import com.linyilinyi.user.service.CollectGroupService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收藏夹表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CollectGroupServiceImpl extends ServiceImpl<CollectGroupMapper, CollectGroup> implements CollectGroupService {

    @Resource
    private CollectGroupMapper collectGroupMapper;
    @Override
    public List<CollectGroup> getCollectGroupList() {
        LambdaQueryWrapper<CollectGroup> queryWrapper = new LambdaQueryWrapper<>();
        return collectGroupMapper.selectList(queryWrapper.orderByDesc(CollectGroup::getCreateTime));
    }
}
