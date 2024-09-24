package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.model.entity.collect.CollectGroup;

import java.util.List;

/**
 * <p>
 * 收藏夹表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-24
 */
public interface CollectGroupService extends IService<CollectGroup> {

    List<CollectGroup> getCollectGroupList();

}
