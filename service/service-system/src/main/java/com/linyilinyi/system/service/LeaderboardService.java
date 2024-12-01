package com.linyilinyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.other.Leaderboard;
import com.linyilinyi.model.vo.other.LeaderboardQueryVo;

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
}
