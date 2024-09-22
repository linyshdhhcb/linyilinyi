package com.linyilinyi.video.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.video.VideoData;
import com.linyilinyi.video.mapper.VideoDataMapper;
import com.linyilinyi.video.service.VideoDataService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频数据统计表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class VideoDataServiceImpl extends ServiceImpl<VideoDataMapper, VideoData> implements VideoDataService {

    @Resource
    private VideoDataMapper videoDataMapper;
    @Override
    public VideoData getVideoDataById(Long videoId) {
        return videoDataMapper.selectOne(new LambdaQueryWrapper<VideoData>().eq(VideoData::getVideoId,videoId));
    }
}
