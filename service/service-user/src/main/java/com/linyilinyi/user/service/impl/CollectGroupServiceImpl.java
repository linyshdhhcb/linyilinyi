package com.linyilinyi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.collect.CollectGroup;
import com.linyilinyi.user.mapper.CollectGroupMapper;
import com.linyilinyi.user.service.CollectGroupService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @Cacheable(cacheNames = "collectGroupList")
    public List<CollectGroup> getCollectGroupList() {
        LambdaQueryWrapper<CollectGroup> queryWrapper = new LambdaQueryWrapper<>();
        return collectGroupMapper.selectList(queryWrapper.orderByDesc(CollectGroup::getCreateTime));
    }

    @Override
    public String addCollectGroup(String name, Integer status) {
        CollectGroup collectGroup = new CollectGroup();
        collectGroup.setName(name);
        collectGroup.setCreateTime(LocalDateTime.now());
        collectGroup.setUserId(AuthContextUser.getUserId());
        if (Optional.ofNullable(status).isPresent()){
            collectGroup.setStatus(status);
        }
        int insert = collectGroupMapper.insert(collectGroup);
        if (insert!=1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        return "添加成功";
    }

    @Override
    @Transactional
    public String deleteCollectGroup(Integer id) {
        // TODO 2024/9/24 远程调用，根据id删除文件夹中所有的收藏视频
        int i = collectGroupMapper.deleteById(id);
        if (i!=1){
            throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
        }
        return "删除成功";
    }

    @Override
    public String updateCollectGroup(CollectGroup collectGroup) {
        collectGroup.setUpdateTime(LocalDateTime.now());
        int i = collectGroupMapper.updateById(collectGroup);
        if (i!=1){
            throw new LinyiException(ResultCodeEnum.UPDATE_ERROR);
        }
        return "修改成功";
    }
}
