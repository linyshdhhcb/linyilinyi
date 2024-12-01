package com.linyilinyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.other.Leaderboard;
import com.linyilinyi.model.vo.other.LeaderboardAddVo;
import com.linyilinyi.model.vo.other.LeaderboardQueryVo;

import java.util.List;

/**
 * <p>
 * 排行榜表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-12-01
 */
public interface LeaderboardService extends IService<Leaderboard> {

    PageResult<Leaderboard> getLeaderboardList(long pageNo, long pageSize, LeaderboardQueryVo leaderboardQueryVo);

    String addLeaderboard(LeaderboardAddVo leaderboardAddVo);

    String deleteLeaderboard(List<Long> ids);

    String updateLeaderboard(Leaderboard leaderboard);
}
