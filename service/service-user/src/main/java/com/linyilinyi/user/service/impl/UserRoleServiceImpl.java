package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.model.entity.user.UserRole;
import com.linyilinyi.user.mapper.UserRoleMapper;
import com.linyilinyi.user.service.RoleService;
import com.linyilinyi.user.service.UserRoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleService roleService;

    @Resource
    private RedisTemplate redisTemplate;
    @Override
    public String addUserRole(Integer userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRole.setCreateTime(LocalDateTime.now());
        int i = userRoleMapper.insert(userRole);
        if (i != 1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        return "添加用户角色成功";
    }

    @Override
    public String deleteUserRoleById(List<Long> ids) {
        List<Long> list = ids.stream().filter(id -> id > 0).collect(Collectors.toList());
        int i = userRoleMapper.deleteBatchIds(list);
        return i+"个用户角色成功";
    }

    @Override
    public List<Role> getUserRoleList(Integer userId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        List<Long> roleIds = userRoleMapper.selectList(queryWrapper.eq(UserRole::getUserId, userId)).stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Role> roles = roleService.listByIds(roleIds);
        redisTemplate.opsForValue().set("user:role:"+userId+":", roles,1, TimeUnit.MINUTES);
        return roles;
    }
}
