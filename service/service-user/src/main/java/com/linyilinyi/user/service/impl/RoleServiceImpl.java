package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.model.vo.user.RoleQueryVo;
import com.linyilinyi.user.mapper.RoleMapper;
import com.linyilinyi.user.service.RoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Resource
    private RoleMapper roleMapper;

    @Override
    public void addRole(String name, String code) {
        Role role = new Role();
        role.setName(name);
        role.setCode(code);
        role.setCreateTime(LocalDateTime.now());
        int insert = roleMapper.insert(role);
        if (insert != 1){
            log.error("新增角色失败");
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
    }

    @Override
    public String deleteRoleById(List<Long> ids) {
        List<Long> collect = ids.stream().filter(id -> id > 0).collect(Collectors.toList());
        if (collect.isEmpty()){
            throw new LinyiException("请输入合法的id");
        }
        int i = roleMapper.deleteBatchIds(collect);
        return i + "条数据删除成功";
    }

    @Override
    public PageResult<Role> getRoleList(long pageNo, long pageSize, RoleQueryVo roleQueryVo) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Optional.ofNullable(roleQueryVo.getId()).isPresent(), Role::getId, roleQueryVo.getId());
        queryWrapper.eq(StringUtils.isNotBlank(roleQueryVo.getName()), Role::getName, roleQueryVo.getName());
        queryWrapper.eq(StringUtils.isNotBlank(roleQueryVo.getCode()), Role::getCode, roleQueryVo.getCode());
        queryWrapper.gt(Optional.ofNullable(roleQueryVo.getStartTime()).isPresent(), Role::getCreateTime, roleQueryVo.getStartTime());
        queryWrapper.lt(Optional.ofNullable(roleQueryVo.getEndTime()).isPresent(), Role::getCreateTime, roleQueryVo.getEndTime());
        Page<Role> rolePage = new Page<>(pageNo, pageSize);
        Page<Role> rolePage1 = roleMapper.selectPage(rolePage, queryWrapper);
        return new PageResult<>(rolePage1.getRecords(), rolePage1.getTotal(), pageNo, pageSize);
    }

    @Override
    public void updateRole(Role role) {
        int i = roleMapper.updateById(role);
        if (i != 1){
            throw new LinyiException(ResultCodeEnum.UPDATE_FAIL);
        }

    }

    @Override
    public List<Role> getList() {
        return roleMapper.selectList(null);
    }
}
