package com.linyilinyi.system.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.other.Leaderboard;
import com.linyilinyi.model.vo.other.Hot;
import com.linyilinyi.model.vo.other.LeaderboardAddVo;
import com.linyilinyi.model.vo.other.LeaderboardQueryVo;
import com.linyilinyi.system.service.LeaderboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

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

    @Autowired
    private LeaderboardService leaderboardService;


    @Operation(summary = "分页获取全部排行榜列表")
    @PostMapping("getLeaderboardList")
    public Result<PageResult<Leaderboard>> getLeaderboardList(@RequestParam(required = false, defaultValue = "1") long pageNo,
                                                              @RequestParam(required = false, defaultValue = "5") long pageSize,
                                                              @RequestBody LeaderboardQueryVo leaderboardQueryVo) {
        return Result.ok(leaderboardService.getLeaderboardList(pageNo, pageSize,leaderboardQueryVo));
    }

    @Operation(summary = "添加排行榜")
    @PostMapping("addLeaderboard")
    public Result<String> addLeaderboard(@RequestBody LeaderboardAddVo leaderboardAddVo) {
        return Result.ok(leaderboardService.addLeaderboard(leaderboardAddVo));
    }

    @Operation(summary = "删除排行榜")
    @PostMapping("deleteLeaderboard/{ids}")
    public Result<String> deleteLeaderboard(@PathVariable List<Long> ids) {
        return Result.ok(leaderboardService.deleteLeaderboard(ids));
    }

    @Operation(summary = "根据id获取排行榜（回显）")
    @GetMapping("getLeaderboard/{id}")
    public Result<Leaderboard> getLeaderboard(@PathVariable Long id) {
        return Result.ok(leaderboardService.getById(id));
    }

    @Operation(summary = "根据对象id和对象类型获取信息")
    @GetMapping("getLeaderboardByTargetIdAndLeaderboardType")
    public Result<Leaderboard> getLeaderboardByTargetIdAndLeaderboardType(@RequestParam Integer targetId,@RequestParam Integer leaderboardType) {
        return Result.ok(leaderboardService.getLeaderboardByTargetIdAndLeaderboardType(targetId,leaderboardType));
    }

    @Operation(summary = "更新排行榜")
    @PostMapping("updateLeaderboard")
    public Result<String> updateLeaderboard(@RequestBody Leaderboard leaderboard) {
        return Result.ok(leaderboardService.updateLeaderboard(leaderboard));
    }

    @Operation(summary = "计算热度")
    @PostMapping("calculateHot")
    public Result<Integer> calculateHot(@RequestBody Hot hot) {
        return Result.ok(leaderboardService.calculateHot(hot));
    }

}
