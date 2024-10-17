package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.user.Menu;
import com.linyilinyi.model.vo.user.MenuAdd;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-10-16
 */
public interface MenuService extends IService<Menu> {

    void addMenu(MenuAdd menuAdd);

    String deleteMenu(List<Long> ids);

    Menu getMenuById(Integer id);

    void updateMenu(Menu menu);

    List<Menu> getMenuList();

    List<Menu> getMenuListByRoleId(Long roleId);

}
