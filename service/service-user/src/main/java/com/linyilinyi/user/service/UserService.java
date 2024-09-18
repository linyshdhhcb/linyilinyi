package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.model.entity.user.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-13
 */
public interface UserService extends IService<User> {

    User getUserById(Integer id);
}
