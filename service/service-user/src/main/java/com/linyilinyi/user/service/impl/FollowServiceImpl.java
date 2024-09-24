package com.linyilinyi.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.model.entity.other.Follow;
import com.linyilinyi.user.mapper.FollowMapper;
import com.linyilinyi.user.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 粉丝表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

}
