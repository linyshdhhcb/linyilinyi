package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.model.entity.user.UserRole;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-10-16
 */
public interface UserRoleService extends IService<UserRole> {


    String addUserRole(Long userId, Long roleId);

    String deleteUserRoleById(List<Long> ids);

    List<Role> getUserRoleList(Long userId);
}
