package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.model.entity.user.Menu;
import com.linyilinyi.model.entity.user.RoleMenu;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-10-16
 */
public interface RoleMenuService extends IService<RoleMenu> {

    String addRoleMenu(Long roleId, List<Long> menuId);

    String deleteRoleMenu(List<Long> ids);
}
