package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.model.entity.user.RoleMenu;
import com.linyilinyi.user.mapper.RoleMenuMapper;
import com.linyilinyi.user.service.MenuService;
import com.linyilinyi.user.service.RoleMenuService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuService menuService;
    @Override
    public String addRoleMenu(Long roleId, List<Long> menuId) {
        List<RoleMenu> roleMenus = new ArrayList<>();
        menuId.stream().map(item ->{
            return roleMenus.add(new RoleMenu(roleId, item, LocalDateTime.now(), LocalDateTime.now()));
        });
        boolean b = this.saveBatch(roleMenus);
        return b ? "添加成功" : "添加失败";

    }

    @Override
    public String deleteRoleMenu(List<Long> ids) {
         ids = ids.stream().filter(id -> id > 0).collect(Collectors.toList());
        int i = roleMenuMapper.deleteBatchIds(ids);
        return i > 0 ? i+"条数据删除成功" : "删除失败";
    }
}
