package com.linyilinyi.user.service.impl;


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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public String deleteRoleById(List<Integer> ids) {
         ids = ids.stream().filter(id -> id <= 0).collect(Collectors.toList());
        int i = roleMapper.deleteBatchIds(ids);
        return i + "条数据删除成功";
    }

    @Override
    public PageResult<PageResult<Role>> getRoleList(long pageNo, long pageSize, RoleQueryVo roleQueryVo) {
        return null;
    }
}
