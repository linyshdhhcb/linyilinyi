package com.linyilinyi.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.other.Leaderboard;
import com.linyilinyi.model.vo.other.LeaderboardQueryVo;
import com.linyilinyi.system.mapper.LeaderboardMapper;
import com.linyilinyi.system.service.LeaderboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 排行榜表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class LeaderboardServiceImpl extends ServiceImpl<LeaderboardMapper, Leaderboard> implements LeaderboardService {

    @Resource
    private LeaderboardMapper leaderboardMapper;

    @Override
    public PageResult<Leaderboard> getLeaderboardList(long pageNo, long pageSize, LeaderboardQueryVo leaderboardQueryVo) {
        LambdaQueryWrapper<Leaderboard> queryWrapper = new LambdaQueryWrapper<>();

        if (leaderboardQueryVo.getTargetType() == 11201 || leaderboardQueryVo.getTargetType() == 11202) {
            queryWrapper.eq(leaderboardQueryVo.getTargetId() != null, Leaderboard::getTargetId, leaderboardQueryVo.getTargetId());
            queryWrapper.lt(leaderboardQueryVo.getScoreMax() != null, Leaderboard::getScore, leaderboardQueryVo.getScoreMax());
            queryWrapper.gt(leaderboardQueryVo.getScoreMin() != null, Leaderboard::getScore, leaderboardQueryVo.getScoreMin());
            queryWrapper.lt(leaderboardQueryVo.getRankMax() != null, Leaderboard::getRank, leaderboardQueryVo.getRankMax());
            queryWrapper.gt(leaderboardQueryVo.getRankMin() != null, Leaderboard::getRank, leaderboardQueryVo.getRankMin());
            queryWrapper.eq(leaderboardQueryVo.getLeaderboardType() != null, Leaderboard::getLeaderboardType, leaderboardQueryVo.getLeaderboardType());
            queryWrapper.gt(leaderboardQueryVo.getStartTime() != null, Leaderboard::getRecordDate, leaderboardQueryVo.getStartTime());
            queryWrapper.lt(leaderboardQueryVo.getEndTime() != null, Leaderboard::getRecordDate, leaderboardQueryVo.getEndTime());

            // 使用对象池复用 Page 对象
            Page<Leaderboard> leaderboardPage = Page.of(pageNo, pageSize);
            Page<Leaderboard> pageList = leaderboardMapper.selectPage(leaderboardPage, queryWrapper);
            return new PageResult<>(pageList.getRecords(), pageList.getTotal(), pageNo, pageSize);
        }
        return null;
    }

}
