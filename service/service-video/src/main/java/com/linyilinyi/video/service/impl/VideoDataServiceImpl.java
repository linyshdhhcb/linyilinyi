package com.linyilinyi.video.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.video.VideoData;
import com.linyilinyi.video.mapper.VideoDataMapper;
import com.linyilinyi.video.service.VideoDataService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 视频数据统计表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class VideoDataServiceImpl extends ServiceImpl<VideoDataMapper, VideoData> implements VideoDataService {

    @Resource
    private VideoDataMapper videoDataMapper;

    @Override
    public VideoData getVideoDataById(Long videoId) {
        return videoDataMapper.selectOne(new LambdaQueryWrapper<VideoData>().eq(VideoData::getVideoId, videoId));
    }

    @Override
    public PageResult<VideoData> getVideoDataList(long pageNo, long pageSize) {
        Page<VideoData> videoDataPage = new Page<>(pageNo, pageSize);
        Page<VideoData> page = videoDataMapper.selectPage(videoDataPage, null);
        return new PageResult<>(page.getRecords(), page.getTotal(), pageNo, pageSize);
    }

    @Override
    public void addVideoData(VideoData videoData) {
        if (Optional.ofNullable(videoData).isEmpty()) {
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
        int insert = videoDataMapper.insert(videoData);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
    }

    @Override
    public String updateVideoData(VideoData videoData) {
        if (Optional.ofNullable(videoData).isEmpty()) {
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
        int update = videoDataMapper.updateById(videoData);
        if (update != 1) {
            throw new LinyiException(ResultCodeEnum.UPDATE_FAIL);
        }
        return "修改成功";
    }

    @Override
    public String addData(Integer id, Integer status) {
        LambdaQueryWrapper<VideoData> queryWrapper = new LambdaQueryWrapper<VideoData>().eq(VideoData::getVideoId, id);
        VideoData videoData = videoDataMapper.selectOne(queryWrapper);
        switch (status) {
            case 22301 -> {
                videoData.setCommentCount(videoData.getCommentCount()+1);
                videoDataMapper.updateById(videoData);
                return "评论数+1";
            }
            case 22302 -> {
                videoData.setPlayCount(videoData.getPlayCount()+1);
                videoDataMapper.updateById(videoData);
                return "播放数+1";
            }
            case 22303 -> {
                videoData.setLikeCount(videoData.getLikeCount()+1);
                videoDataMapper.updateById(videoData);
                return "点赞数+1";
            }
            case 22304 -> {
                videoData.setCollectCount(videoData.getCollectCount()+1);
                videoDataMapper.updateById(videoData);
                return "收藏数+1";
            }
            case 22305 -> {
                videoData.setShareCount(videoData.getShareCount()+1);
                videoDataMapper.updateById(videoData);
                return "分享数+1";
            }
            default -> throw new LinyiException("没有该类型");
        }
    }
}
