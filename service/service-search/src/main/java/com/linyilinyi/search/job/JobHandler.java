package com.linyilinyi.search.job;

import com.linyilinyi.search.service.SearchService;
import com.mysql.cj.log.Log;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
 
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 任务处理器
 */
@Slf4j
@Component
public class JobHandler {

    private final SearchService searchService;

    public JobHandler(SearchService searchService) {
        this.searchService = searchService;
    }

 
    /**
     * 普通任务
     */
    @XxlJob("videoJob")
    public void videoJob() throws Exception {
        log.info("videoJob执行时间：{}。定时任务：将mysql的video插入es", LocalDateTime.now());
    }

    @XxlJob("articleJob")
    public void articleJob() throws Exception {
        log.info("videoJob执行时间：{}。定时任务：将mysql的数据article插入es", LocalDateTime.now());
    }
}