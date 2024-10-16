package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.model.entity.user.RoleMenu;
import com.linyilinyi.user.mapper.RoleMenuMapper;
import com.linyilinyi.user.service.RoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
