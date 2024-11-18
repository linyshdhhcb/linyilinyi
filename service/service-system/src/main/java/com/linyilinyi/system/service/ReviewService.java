package com.linyilinyi.system.service;

import com.linyilinyi.common.model.Result;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/18
 * @ClassName: ReviewService
 */
public interface ReviewService {
    Result<String> video(String videoId, Integer status, String reason);
}
