package com.linyilinyi.search.config;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;
 
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 任务处理器
 */
@Component
public class JobHandler {
 
    private List<Integer> dataList = Arrays.asList(1, 2, 3, 4, 5);
 
    /**
     * 普通任务
     */
    @XxlJob("shardingJob")
    public void firstJob() throws Exception {
        System.out.println("firstJob执行了.... " + LocalDateTime.now());
        for (Integer data : dataList) {
            System.out.println("data= {}" + data);
            Thread.sleep(new Random().nextInt(100)+101);
        }
        System.out.println("firstJob执行结束了.... " + LocalDateTime.now());
    }
}