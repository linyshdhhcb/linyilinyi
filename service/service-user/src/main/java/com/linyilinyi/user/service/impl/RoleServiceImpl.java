package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.user.mapper.RoleMapper;
import com.linyilinyi.user.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
