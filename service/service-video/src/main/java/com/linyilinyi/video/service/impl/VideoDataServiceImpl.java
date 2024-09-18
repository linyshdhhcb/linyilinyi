package com.linyilinyi.video.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.model.entity.video.VideoData;
import com.linyilinyi.video.mapper.VideoDataMapper;
import com.linyilinyi.video.service.VideoDataService;
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

}
