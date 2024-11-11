package com.linyilinyi.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.linyilinyi.model.entity.user.UserHome;
import com.linyilinyi.user.mapper.UserHomeMapper;
import com.linyilinyi.user.service.UserHomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户主页表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserHomeServiceImpl extends ServiceImpl<UserHomeMapper, UserHome> implements UserHomeService {

}
