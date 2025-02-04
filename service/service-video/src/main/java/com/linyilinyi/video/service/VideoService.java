package com.linyilinyi.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.video.Video;
import com.linyilinyi.model.vo.video.VideoAddVo;
import com.linyilinyi.model.vo.video.VideoQueryVo;
import com.linyilinyi.model.vo.video.VideoVo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * <p>
 * 视频信息表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-13
 */
public interface VideoService extends IService<Video> {


    PageResult getList(long pageNo, long pageSize, VideoQueryVo videoQueryVo);

    VideoVo getVideoById(Integer id);

    Video addVideo(VideoAddVo video);

    String deleteVideo(List<Integer> ids);

    String updateVideo(Video video);

    List<Video> getVideoListByUserId(Integer userId);

    Integer getByToken(HttpServletRequest request);
}
