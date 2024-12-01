package com.linyilinyi.system.controller;

import com.linyilinyi.system.service.LeaderboardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 排行榜表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@RestController
@RequestMapping("leaderboard")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;
}
