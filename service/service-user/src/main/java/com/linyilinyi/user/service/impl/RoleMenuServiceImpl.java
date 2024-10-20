package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.user.RoleMenu;
import com.linyilinyi.model.vo.user.AddRoleMenu;
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
    public String addRoleMenu(AddRoleMenu addRoleMenu) {
        Long roleId = addRoleMenu.getRoleId();
        List<Long> menuIds = addRoleMenu.getMenuIds();
        List<RoleMenu> roleMenus = new ArrayList<>();
        menuIds.stream().forEach(item ->
             roleMenus.add(new RoleMenu(roleId, item, LocalDateTime.now(), LocalDateTime.now()))
        );
        boolean b = this.saveBatch(roleMenus);
        if (!b) {
            throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
        }
        return "添加成功";

    }

    @Override
    public String deleteRoleMenu(List<Long> ids) {
         ids = ids.stream().filter(id -> id > 0).collect(Collectors.toList());
        int i = roleMenuMapper.deleteBatchIds(ids);
        if (i>0){
            return i+"条数据删除成功";
        }
       throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
    }
}
