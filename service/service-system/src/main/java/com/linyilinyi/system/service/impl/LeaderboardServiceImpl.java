package com.linyilinyi.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.model.entity.other.Leaderboard;
import com.linyilinyi.system.mapper.LeaderboardMapper;
import com.linyilinyi.system.service.LeaderboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 排行榜表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class LeaderboardServiceImpl extends ServiceImpl<LeaderboardMapper, Leaderboard> implements LeaderboardService {

}
