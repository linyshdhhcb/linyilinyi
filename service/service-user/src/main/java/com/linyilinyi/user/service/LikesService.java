package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.model.entity.likes.Likes;

/**
 * <p>
 * 点赞表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-24
 */
public interface LikesService extends IService<Likes> {


    String addLikes(Integer id, Integer targetType);

    Boolean isLikes(Integer id, Integer targetType, Integer userId);
}
