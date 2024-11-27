package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.other.Follow;
import com.linyilinyi.model.vo.user.FanVo;
import com.linyilinyi.model.vo.user.FollowVo;

import java.util.List;

/**
 * <p>
 * 粉丝表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-24
 */
public interface FollowService extends IService<Follow> {
    List<FanVo> getFansList();


    List<FollowVo> getFollowList();

    String addFollow(Integer id);

    Boolean isFollow(Integer fanId,Integer idolId);
}
