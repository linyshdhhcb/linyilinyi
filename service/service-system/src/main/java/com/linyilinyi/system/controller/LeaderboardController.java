package com.linyilinyi.system.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.other.Leaderboard;
import com.linyilinyi.model.vo.other.LeaderboardQueryVo;
import com.linyilinyi.system.service.LeaderboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * <p>
 * 排行榜表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Tag(name = "排行榜管理")
@Slf4j
@RestController
@RequestMapping("leaderboard")
@SuppressWarnings({"unchecked", "rawtypes"})
public class LeaderboardController {

    @Resource
    private LeaderboardService leaderboardService;


    @Operation(summary = "分页获取全部排行榜列表")
    @PostMapping("getLeaderboardList")
    public Result<PageResult<Leaderboard>> getLeaderboardList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                              @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                              @RequestBody LeaderboardQueryVo leaderboardQueryVo) {
        return Result.ok(leaderboardService.getLeaderboardList(pageNo, pageSize,leaderboardQueryVo));
    }

    @Operation(summary = "添加排行榜")



}
