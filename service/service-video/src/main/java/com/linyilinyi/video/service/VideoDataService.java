package com.linyilinyi.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.video.VideoData;

/**
 * <p>
 * 视频数据统计表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-13
 */
public interface VideoDataService extends IService<VideoData> {

    VideoData getVideoDataById(Long videoId);

    PageResult<VideoData> getVideoDataList(long pageNo, long pageSize);

    void addVideoData(VideoData videoData);

    String updateVideoData(VideoData videoData);
}
