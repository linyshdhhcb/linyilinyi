package com.linyilinyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.reviewer.Review;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/18
 * @ClassName: ReviewService
 */
public interface ReviewService extends IService<Review> {
    String video(Integer videoId, Integer status, String reason);

    String article(Integer articleId, Integer status, String reason);


    PageResult<?> getUnreviewedList(long pageNo, long pageSize, Integer mediaType, Integer status);
}
