package com.linyilinyi.service.impl;

import com.linyilinyi.entity.po.UserHome;
import com.linyilinyi.mapper.UserHomeMapper;
import com.linyilinyi.service.UserHomeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 用户主页表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class UserHomeServiceImpl extends ServiceImpl<UserHomeMapper, UserHome> implements UserHomeService {

}
