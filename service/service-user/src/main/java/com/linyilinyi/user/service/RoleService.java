package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.model.vo.user.RoleQueryVo;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-10-16
 */
public interface RoleService extends IService<Role> {

    void addRole(String name, String code);

    String deleteRoleById(List<Integer> ids);

    PageResult<Role> getRoleList(long pageNo, long pageSize, RoleQueryVo roleQueryVo);

    void updateRole(Role role);
}
