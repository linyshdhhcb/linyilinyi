package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.model.entity.collect.Collect;
import com.linyilinyi.model.vo.video.VideoVo;

import java.util.List;

/**
 * <p>
 * 收藏表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-24
 */
public interface CollectService extends IService<Collect> {

    List<VideoVo> getCollectList(Integer collectGroupId);

    String addCollect(Integer collectGroupId, Integer targetId);


    String deleteCollectList(List<Integer> targetIdList, Integer collectGroupId);

    String updateCollect(Collect collect);

}
