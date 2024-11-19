package com.linyilinyi.system.service;

import com.linyilinyi.common.model.PageResult;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/18
 * @ClassName: ReviewService
 */
public interface ReviewService {
    String video(Integer videoId, Integer status, String reason);

    String article(Integer articleId, Integer status, String reason);


    PageResult<?> getUnreviewedList(long pageNo, long pageSize, Integer mediaType, Integer status);
}
