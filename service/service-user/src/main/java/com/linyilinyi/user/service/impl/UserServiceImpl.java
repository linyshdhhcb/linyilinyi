package com.linyilinyi.user.service.impl;

import ch.qos.logback.core.testUtil.RandomUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.PasswordEncoder;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.model.vo.user.LoginVo;
import com.linyilinyi.model.vo.user.UserAddVo;
import com.linyilinyi.model.vo.user.UserQueryVo;
import com.linyilinyi.model.vo.user.UserUpdateVo;
import com.linyilinyi.user.mapper.UserMapper;
import com.linyilinyi.user.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public String deleteUserById(List<Integer> ids) {
        int i = userMapper.deleteBatchIds(ids);
        if(i!=ids.size()){
           throw new LinyiException("数据异常，没有全部删除成功，删除操作失败");
        }
        return "删除成功";
    }

    @Override
    public PageResult<User> getUserList(long pageNo, long pageSize, UserQueryVo userQueryVo) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(userQueryVo.getUsername()),User::getUsername,userQueryVo.getUsername());
        queryWrapper.eq(Optional.ofNullable(userQueryVo.getGender()).isPresent(),User::getGender,userQueryVo.getGender());
        queryWrapper.like(StringUtils.isNotBlank(userQueryVo.getNickname()),User::getNickname,userQueryVo.getNickname());
        queryWrapper.like(StringUtils.isNotBlank(userQueryVo.getIntro()),User::getIntro,userQueryVo.getIntro());
        queryWrapper.eq(StringUtils.isNotBlank(userQueryVo.getPhone()),User::getPhone,userQueryVo.getPhone());
        queryWrapper.eq(Optional.ofNullable(userQueryVo.getStatus()).isPresent(),User::getStatus,userQueryVo.getStatus());
        queryWrapper.eq(StringUtils.isNotBlank(userQueryVo.getMail()),User::getMail,userQueryVo.getMail());
        queryWrapper.gt(Optional.ofNullable(userQueryVo.getStartTime()).isPresent(),User::getCreateTime,userQueryVo.getStartTime());
        queryWrapper.lt(Optional.ofNullable(userQueryVo.getEndTime()).isPresent(),User::getCreateTime,userQueryVo.getEndTime());
        Page<User> userPage = new Page<>(pageNo,pageSize);
        Page<User> userPage1 = userMapper.selectPage(userPage, queryWrapper);
        return new PageResult<>(userPage1.getRecords(),userPage1.getTotal(),pageNo,pageSize);
    }

    @Override
    public String updateUser(UserUpdateVo user) {

        if (Optional.ofNullable(user).isEmpty()){
            log.error("修改用户信息参数user为{}",user);
            throw new LinyiException(ResultCodeEnum.DATA_ERROR);
        }
        User user1 = new User();
        BeanUtils.copyProperties(user,user1);
        int i = userMapper.updateById(user1);
        if (i!=1) {
            throw new LinyiException(ResultCodeEnum.UPDATE_ERROR);
        }
        return "修改成功";
    }

    @Override
    public String addUser(UserAddVo userAddVo) {
        userAddVo.setImage("http://192.168.85.129:9000/linyilinyi/image/moren/1111.webp");
        if (!userAddVo.getPassword().equals(userAddVo.getPasswords())){
            throw new LinyiException(ResultCodeEnum.PASSWORDS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddVo,user);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        String password = PasswordEncoder.encode(user.getPassword(), salt);
        user.setPassword(password);
        user.setSalt(salt);
        user.setCreateTime(LocalDateTime.now());
        user.setNickname("user_"+String.valueOf(System.currentTimeMillis())+RandomStringUtils.randomAlphanumeric(5));
        int insert = userMapper.insert(user);
        if (insert != 1){
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        return "添加成功";

    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getUsername,username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public String login(LoginVo loginVo) {
        String username = loginVo.getUsername();
        User byUsername = getByUsername(username);
        if (byUsername == null){
            throw new LinyiException(ResultCodeEnum.ACCOUNT_NULL);
        }

        if(PasswordEncoder.encode(loginVo.getPassword(), byUsername.getPassword(),byUsername.getSalt())){
            //satoken登录
            StpUtil.login(byUsername.getId());
            //获取登录信息
            String tokenValue = StpUtil.getTokenValue();
            return tokenValue;
        }
        throw new LinyiException(ResultCodeEnum.PASSWORD_ERROR);
    }
}
