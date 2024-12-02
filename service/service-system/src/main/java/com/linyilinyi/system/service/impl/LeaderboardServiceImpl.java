package com.linyilinyi.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.other.Leaderboard;
import com.linyilinyi.model.vo.other.Hot;
import com.linyilinyi.model.vo.other.LeaderboardAddVo;
import com.linyilinyi.model.vo.other.LeaderboardQueryVo;
import com.linyilinyi.system.mapper.LeaderboardMapper;
import com.linyilinyi.system.service.LeaderboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public String addLeaderboard(LeaderboardAddVo leaderboardAddVo) {
        Leaderboard leaderboard = new Leaderboard();
        BeanUtils.copyProperties(leaderboardAddVo, leaderboard);
        leaderboard.setRecordDate(LocalDate.now());
        leaderboard.setCreatedAt(LocalDateTime.now());
        int i = leaderboardMapper.insert(leaderboard);
        if (i != 1) {
            throw new RuntimeException("添加失败");
        }
        return "添加成功";
    }

    @Override
    public String deleteLeaderboard(List<Long> ids) {
        ids = ids.stream().filter(id -> id > 0).collect(Collectors.toList());
        int deletedCount = leaderboardMapper.deleteBatchIds(ids);
        if (deletedCount <= 0) {
            throw new LinyiException("删除失败");
        }
        return String.format("成功删除 %d 条信息。删除失败 %d 条信息。", deletedCount, ids.size() - deletedCount);
    }

    @Override
    public String updateLeaderboard(Leaderboard leaderboard) {
        leaderboard.setUpdatedAt(LocalDateTime.now());
        int i = leaderboardMapper.updateById(leaderboard);
        if (i != 1) {
            throw new LinyiException("修改失败");
        }
        return "修改成功";
    }

    @Override
    public Integer calculateHot(Hot hot) {
        //浏览量 * 1 + 评论数 * 2 + 点赞数 * 3 + 收藏数 * 2 + 分享数 * 3
        return Optional.ofNullable(hot).map(h -> h.getViewCount() * 1 + h.getCommentCount() * 2 + h.getLikeCount() * 3 + h.getCollectCount() * 2 + h.getShareCount() * 3).orElse(null);
    }

    @Override
    public Leaderboard getLeaderboardByTargetIdAndLeaderboardType(Integer targetId, Integer leaderboardType) {
        LambdaQueryWrapper<Leaderboard> queryWrapper = new LambdaQueryWrapper<Leaderboard>().eq(Leaderboard::getTargetId, targetId).eq(Leaderboard::getLeaderboardType, leaderboardType);
        Leaderboard leaderboard = leaderboardMapper.selectOne(queryWrapper);
        return leaderboard;
    }


}
