package com.linyilinyi.user.service.impl;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.user.Menu;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.model.entity.user.RoleMenu;
import com.linyilinyi.model.vo.user.MenuAdd;
import com.linyilinyi.user.client.UserClient;
import com.linyilinyi.user.mapper.MenuMapper;
import com.linyilinyi.user.mapper.RoleMapper;
import com.linyilinyi.user.mapper.RoleMenuMapper;
import com.linyilinyi.user.service.MenuService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public void addMenu(MenuAdd menuAdd) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuAdd,menu);
        menu.setCreateTime(LocalDateTime.now());
        int insert = menuMapper.insert(menu);
        if (insert != 1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
    }

    @Override
    public String deleteMenu(List<Long> ids) {
        ids = ids.stream().filter(id -> id > 0).collect(Collectors.toList());
        int i = menuMapper.deleteBatchIds(ids);
        return i+"条数据删除成功";
    }

    @Override
    public Menu getMenuById(Integer id) {
        Menu menu = menuMapper.selectById(id);
        if (Optional.ofNullable(menu).isEmpty()){
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
        return menu;
    }

    @Override
    public void updateMenu(Menu menu) {
        menu.setUpdateTime(LocalDateTime.now());
        int update = menuMapper.updateById(menu);
        if (update != 1){
            throw new LinyiException(ResultCodeEnum.UPDATE_FAIL);
        }
    }

    @Override
    public List<Menu> getMenuListTree() {
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getStatus,1));
        if (Optional.ofNullable(menus).isEmpty()){
            return null;
        }
        List<Menu> menus1 = buildTree(menus);

        return menus1;
    }

    @Override
    public List<Menu> getMenuListByRoleId(Long roleId) {
//        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getStatus,1));
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        List<Menu> menus = menuMapper.selectBatchIds(menuIds);
        return menus;
    }

    @Override
    public List<Menu> getMenuListByRoleCode(String roleCode) {
        Long id = roleMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getCode, roleCode)).getId();
        return getMenuListByRoleId(id);
    }

    /**
     * 使用递归方法构建菜单树
     * 该方法主要用于处理平面菜单列表，将其转换为树状结构，以便于前端展示或操作
     * 通过查找每个菜单项的子菜单，逐层构建起完整的菜单树
     *
     * @param sysMenuList 包含所有菜单项的列表，包括父菜单和子菜单
     * @return 返回构建好的菜单树，根菜单在列表的开始位置
     */
    public static List<Menu> buildTree(List<Menu> sysMenuList) {
        List<Menu> trees = new ArrayList<>();
        for (Menu sysMenu : sysMenuList) {
            // 当菜单项的父ID为0时，标识该菜单项为根菜单
            if (sysMenu.getParentId().longValue() == 0) {
                // 递归查找该根菜单下的所有子菜单，构建子树
                trees.add(findChildren(sysMenu, sysMenuList));
            }
        }
        // 返回构建好的菜单树
        return trees;
    }

    /**
     * 递归查找子节点
     * 该方法用于在给定的树节点列表中，递归地查找并设置指定节点的所有子节点
     * @param sysMenu 当前正在查找子节点的菜单对象，即父节点
     * @param treeNodes 包含所有菜单的列表，从这个列表中查找子节点
     * @return 返回包含所有子节点的菜单对象
     */
    public static Menu findChildren(Menu sysMenu, List<Menu> treeNodes) {
        // 初始化当前节点的子节点列表
        sysMenu.setChildren(new ArrayList<Menu>());

        // 遍历所有节点，查找当前节点的子节点
        for (Menu it : treeNodes) {
            // 如果当前节点的ID与遍历节点的父节点ID相等，则说明遍历节点是当前节点的子节点
            if(sysMenu.getId().longValue() == it.getParentId().longValue()) {
                // 如果当前节点的子节点列表为空，则初始化
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                // 递归调用自身，为找到的子节点继续查找其子节点，并将其添加到当前节点的子节点列表中
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        // 返回包含所有子节点的菜单对象
        return sysMenu;
    }

    @Override
    public Menu getMenuByName(String name) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        Menu menu = menuMapper.selectOne(queryWrapper.eq(Menu::getName, name));
        return menu;
    }

    @Override
    public void deleteMenuType(Integer i) {
        //删除出i以外的全部数据
        menuMapper.deleteNeMenuType(i);
    }

    @Override
    public List<Menu> getMenuList() {
        return menuMapper.selectList(null);
    }
}
