package com.linyilinyi.video.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.common.utils.SensitiveWordsUtils;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.model.entity.video.Video;
import com.linyilinyi.model.entity.video.VideoData;
import com.linyilinyi.model.vo.video.VideoAddVo;
import com.linyilinyi.model.vo.video.VideoQueryVo;
import com.linyilinyi.model.vo.video.VideoVo;
import com.linyilinyi.user.client.UserClient;
import com.linyilinyi.video.mapper.VideoDataMapper;
import com.linyilinyi.video.mapper.VideoMapper;
import com.linyilinyi.video.service.VideoDataService;
import com.linyilinyi.video.service.VideoService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 视频信息表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private VideoDataService videoDataService;
    @Resource
    private UserClient userClient;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private VideoDataMapper videoDataMapper;

    @Resource
    private HttpServletRequest request;

    @Override
    public PageResult getList(long pageNo, long pageSize, VideoQueryVo videoQueryVo) {
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(videoQueryVo.getName()), Video::getName, videoQueryVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(videoQueryVo.getTag()), Video::getTag, videoQueryVo.getTag());
        queryWrapper.like(StringUtils.isNotBlank(videoQueryVo.getIntro()), Video::getIntro, videoQueryVo.getIntro());
        queryWrapper.eq(videoQueryVo.getUserId() != null, Video::getUserId, videoQueryVo.getUserId());
        queryWrapper.like(StringUtils.isNotBlank(videoQueryVo.getNickname()), Video::getNickname, videoQueryVo.getNickname());
        queryWrapper.like(StringUtils.isNotBlank(videoQueryVo.getUsername()), Video::getUsername, videoQueryVo.getUsername());
        queryWrapper.gt(videoQueryVo.getStartTime() != null, Video::getCreateTime, videoQueryVo.getStartTime());
        queryWrapper.lt(videoQueryVo.getEndTime() != null, Video::getCreateTime, videoQueryVo.getEndTime());
        Page<Video> videoPage = new Page<>(pageNo, pageSize);
        Page<Video> page = videoMapper.selectPage(videoPage, queryWrapper);
        List<Video> records = page.getRecords();
        long total = page.getTotal();
        return new PageResult<>(records, total, pageNo, pageSize);
    }

    @Override
    public VideoVo getVideoById(Integer id) {
        Video video = videoMapper.selectById(id);
        if (Optional.ofNullable(video).isEmpty()) {
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
        VideoVo videoVo = new VideoVo();
        BeanUtils.copyProperties(video, videoVo);
        return videoVo;
    }

    @Override
    @Transactional
    public Video addVideo(VideoAddVo video) {
        if (SensitiveWordsUtils.isSensitiveWords(video)){
            throw new LinyiException(ResultCodeEnum.SENSITIVE_WORDS);
        }
        Result<User> userById = userClient.getUserById(Integer.parseInt(request.getHeader("userid")));
        User user = userById.getData();
        if (Optional.ofNullable(user).isEmpty()) {
            throw new LinyiException(ResultCodeEnum.ACCOUNT_NULL);
        }
        Video videoNew = new Video();
        videoNew.setUserId(user.getId());
        videoNew.setNickname(user.getNickname());
        videoNew.setImage(user.getImage());
        videoNew.setUsername(user.getUsername());
        videoNew.setCreateTime(LocalDateTime.now());
        videoNew.setVideoStart(video.getVideoStart());
        videoNew.setVideoMd5(video.getVideoMd5());
        videoNew.setImageStart(video.getImageStart());
        videoNew.setImageMd5(video.getImageMd5());
        BeanUtils.copyProperties(video, videoNew);
        int insert = videoMapper.insert(videoNew);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.FAIL);
        }
//        Review reviewer = new Review();
//        reviewer.setTargetId(videoNew.getId());
//        reviewer.setType(11102);
//        reviewer.setStatus(10001);
//        reviewer.setCreateTime(LocalDateTime.now());

        VideoData videoData = new VideoData();
        videoData.setVideoId(videoNew.getId());
        videoData.setCreateTime(LocalDateTime.now());
        videoDataService.addVideoData(videoData);
        return videoNew;
    }

    @Override
    @Transactional
    public String deleteVideo(List<Integer> ids) {
        for (Integer id : ids) {
            if (id <= 0) {
                throw new LinyiException(ResultCodeEnum.DATA_ERROR);
            }
        }
        int i = videoMapper.deleteBatchIds(ids);
        int i1 = videoDataMapper.deleteBatchIds(ids);
        if (i != ids.size() && i1 != ids.size()) {
            throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
        }
        return "删除成功";
    }

    @Override
    public String updateVideo(Video video) {
        if (SensitiveWordsUtils.isSensitiveWords(video)){
            throw new LinyiException(ResultCodeEnum.SENSITIVE_WORDS);
        }
        if (Optional.ofNullable(video).isEmpty()) {
            throw new LinyiException(ResultCodeEnum.DATA_ERROR);
        }
        video.setUpdateTime(LocalDateTime.now());
        int i = videoMapper.updateById(video);
        if (i != 1) {
            throw new LinyiException(ResultCodeEnum.UPDATE_FAIL);
        }
        return "修改成功";
    }

    @Override
    public List<Video> getVideoListByUserId(Integer userId) {
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<Video>().eq(Video::getUserId, userId).eq(Video::getVideoStart, 10002).eq(Video::getIsDelete, 10002);
        return videoMapper.selectList(queryWrapper);
    }

    @Override
    public Integer getByToken(HttpServletRequest request) {
        Object o = redisTemplate.opsForValue().get("satoken:login:token:" + request.getCookies()[1].getValue());
        return Integer.parseInt(String.valueOf(o));
    }
}
