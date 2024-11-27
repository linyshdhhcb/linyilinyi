package com.linyilinyi.user.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.model.entity.user.UserRole;
import com.linyilinyi.model.vo.user.RoleQueryVo;
import com.linyilinyi.user.mapper.RoleMapper;
import com.linyilinyi.user.mapper.UserRoleMapper;
import com.linyilinyi.user.service.RoleService;
import com.linyilinyi.user.service.UserRoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@SuppressWarnings({"unchecked", "rawtypes"})
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public void addRole(String name, String code) {
        Role role = new Role();
        role.setName(name);
        role.setCode(code);
        role.setCreateTime(LocalDateTime.now());
        int insert = roleMapper.insert(role);
        if (insert != 1) {
            log.error("新增角色失败");
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
    }

    @Override
    public String deleteRoleById(List<Long> ids) {
        List<Long> collect = new ArrayList<>();
        ArrayList<Long> eList = new ArrayList<>();
        ArrayList<Long> sList = new ArrayList<>();
        ids.stream().filter(id -> id > 0).forEach(id -> {
            UserRole userRole = userRoleMapper.selectOne(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, id));
            if (Optional.ofNullable(userRole).isEmpty()) {
                collect.add(id);
            }
        });
        StringBuilder sb = new StringBuilder();
        if (collect.isEmpty()) {
            throw new LinyiException("请输入合法的id");
        } else {
            for (int i = 0; i < collect.size(); i++) {
                int i1 = roleMapper.deleteById(collect.get(i));
                if (i1 != 1) {
                    eList.add(collect.get(i));
                    continue;
                }
                sList.add(collect.get(i));
            }
            sb.append(JSON.toJSONString(sList)).append(sList.size()).append("条数据删除成功。");
            if (eList.size() != 0) {
                sb.append(JSON.toJSONString(eList)).append(eList.size()).append("条数据删除失败。");
            }
            return sb.toString();
        }
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
        role.setUpdateTime(LocalDateTime.now());
        int i = roleMapper.updateById(role);
        if (i != 1) {
            throw new LinyiException(ResultCodeEnum.UPDATE_FAIL);
        }
    }

    @Override
    public List<Role> getList() {
        return roleMapper.selectList(null);
    }

}
