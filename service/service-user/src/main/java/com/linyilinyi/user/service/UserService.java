package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.model.vo.code.Code;
import com.linyilinyi.model.vo.user.*;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-13
 */
public interface UserService extends IService<User> {

    User getUserById(Integer id);

    String deleteUserById(List<Integer> ids);

    PageResult<User> getUserList(long pageNo, long pageSize, UserQueryVo userQueryVo);

    String updateUser(UserUpdateVo user);

    String addUser(UserAddVo userAddVo);

    User getByUsername(String username);

    String login(LoginVo loginVo);

    String register(UserRegisterVo userRegisterVo);

    Code getRegisterCode(String mail);
}
