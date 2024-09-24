package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.collect.Collect;
import com.linyilinyi.model.entity.collect.CollectGroup;
import com.linyilinyi.model.vo.video.VideoVo;
import com.linyilinyi.user.mapper.CollectMapper;
import com.linyilinyi.user.service.CollectGroupService;
import com.linyilinyi.user.service.CollectService;
import com.linyilinyi.video.client.VideoClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 收藏表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Resource
    private CollectMapper collectMapper;
    @Resource
    private VideoClient videoClient;

    @Resource
    private CollectGroupService collectGroupService;

    @Override
    public List<VideoVo> getCollectList(Integer collectGroupId) {
        List<Collect> collectList = collectMapper.selectList(new LambdaQueryWrapper<Collect>().eq(Collect::getCollectGroupId, collectGroupId).orderByDesc(Collect::getCreateTime));
        ArrayList<VideoVo> videoVos = new ArrayList<>();
        for (Collect collect : collectList) {
            Result<VideoVo> videoById = videoClient.getVideoById(collect.getTargetId());
            if (Optional.ofNullable(videoById).isPresent()) {
                videoVos.add(videoById.getData());
            } else {
                videoVos.add(new VideoVo());
            }
        }
        return videoVos;
    }

    @Override
    public String addCollect(Integer collectGroupId, Integer targetId) {
        if (isCollect(targetId)) {
            throw new LinyiException(ResultCodeEnum.EXISTED);
        }
        Collect collect = new Collect();
        collect.setCollectGroupId(collectGroupId);
        collect.setTargetId(targetId);
        collect.setCreateTime(LocalDateTime.now());
        int insert = collectMapper.insert(collect);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        return "添加成功";
    }

    @Override
    public String deleteCollectList(List<Integer> ids) {
        for (Integer targetId : ids) {
            if (targetId <= 0) {
                throw new LinyiException(ResultCodeEnum.DATA_ERROR);
            }
        }
        int delete = collectMapper.deleteBatchIds(ids);
        if (delete <= 0){
            throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
        }else  if (delete != ids.size()) {
            return "输入的id有问题，删除部分成功";
        }
        return "删除成功";
    }

    @Override
    public String updateCollect(Collect collect) {
        collect.setUpdateTime(LocalDateTime.now());
        int i = collectMapper.updateById(collect);
        if (i != 1) {
            throw new LinyiException(ResultCodeEnum.UPDATE_ERROR);
        }
        return "修改成功";
    }

    /**
     * 判断某个目标是否已被收藏
     *
     * @param targetId 目标对象的ID
     * @return 如果目标对象已被收藏，则返回true；否则返回false
     */
    @Override
    public Boolean isCollect(Integer targetId) {
        // 构建查询条件
        List<Integer> collectGroupIds = collectGroupService.getCollectGroupList()
                .stream()
                .map(CollectGroup::getId)
                .collect(Collectors.toList());

        // 查询所有相关收藏记录
        Long num = collectMapper.selectCount(
                new LambdaQueryWrapper<Collect>()
                        .in(Collect::getCollectGroupId, collectGroupIds)
                        .eq(Collect::getTargetId, targetId)
        );

        // 如果存在收藏记录，则返回true
        return num > 0 ? true : false;
    }

}
