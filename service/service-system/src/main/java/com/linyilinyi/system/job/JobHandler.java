package com.linyilinyi.system.job;

import com.linyilinyi.system.service.LeaderboardService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 定时任务处理器（mysql和es数据同步）
 * @Author linyi
 * @Date 2024/10/24
 * @ClassName: JobHandler
 */
@Slf4j
@Component
public class JobHandler {

    private final LeaderboardService leaderboardService;

    private final RedisTemplate redisTemplate;

    public JobHandler(LeaderboardService leaderboardService, RedisTemplate redisTemplate) {
        this.leaderboardService = leaderboardService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 普通任务
     */
    @XxlJob("HotJob")
    public void HotJob() {
        try {
            log.info("HotJob执行时间：{}。定时任务：从Redis获取计算热度相关数据进行计算", LocalDateTime.now());
            List list = (List) redisTemplate.opsForValue().get("hot");
            list.stream().forEach(e->{

            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}