package com.linyilinyi.system.service.impl;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.system.service.ReviewService;
import com.linyilinyi.video.client.VideoClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/18
 * @ClassName: ReviewServiceImpl
 */
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class ReviewServiceImpl implements ReviewService {

    @Resource
    private VideoClient videoClient;
    @Override
    public Result<String> video(String videoId, Integer status, String reason) {
        return null;

    }
}
