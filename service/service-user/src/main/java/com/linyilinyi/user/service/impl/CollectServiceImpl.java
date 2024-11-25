package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.article.client.ArticleClient;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.entity.collect.Collect;
import com.linyilinyi.model.entity.collect.CollectGroup;
import com.linyilinyi.model.entity.video.Video;
import com.linyilinyi.model.vo.notice.NoticeSystemVo;
import com.linyilinyi.model.vo.notice.NoticeVo;
import com.linyilinyi.model.vo.video.VideoVo;
import com.linyilinyi.notice.client.NoticeClient;
import com.linyilinyi.user.mapper.CollectMapper;
import com.linyilinyi.user.service.CollectGroupService;
import com.linyilinyi.user.service.CollectService;
import com.linyilinyi.video.client.VideoClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 收藏表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Resource
    private CollectMapper collectMapper;
    @Resource
    private VideoClient videoClient;

    @Resource
    private ArticleClient articleClient;
    @Resource
    private NoticeClient noticeClient;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private HttpServletRequest request;

    @Override
    @Cacheable(cacheNames = "collectList", key = "#collectGroupId")
    public List<VideoVo> getCollectList(Integer collectGroupId) {
        List<Collect> collectList = collectMapper.selectList(new LambdaQueryWrapper<Collect>().eq(Collect::getCollectGroupId, collectGroupId).orderByDesc(Collect::getCreateTime));
        ArrayList<VideoVo> videoVos = new ArrayList<>();
        for (Collect collect : collectList) {
            Result<VideoVo> videoById = videoClient.getVideoById(collect.getTargetId());
            if (Optional.ofNullable(videoById).isPresent()) {
                videoVos.add(videoById.getData());
            } else {
                videoVos.add(new VideoVo());
            }
        }
        return videoVos;
    }

    @Override
    public String addCollect(Integer collectGroupId, Integer targetId, Integer targetType) {
        Integer receviceUserId = null;
        if (targetType == 11201) {
            Video video = videoClient.getVideoById(targetId).getData();
            if (video == null) {
                throw new LinyiException(ResultCodeEnum.DATA_NULL); // 视频数据为空时抛出异常
            }
            receviceUserId = video.getUserId();
        } else if (targetType == 11202) {
            Article article = articleClient.getArticleById(targetId).getData();
            if (article == null) {
                throw new LinyiException(ResultCodeEnum.DATA_NULL); // 文章数据为空时抛出异常
            }
            receviceUserId = article.getUserId();
        } else {
            throw new LinyiException("该类型不存在");
        }
        Integer i = (Integer) redisTemplate.opsForValue().get("collect:userId:" + targetType + ":" + targetId);
        if (i != null || isCollect(targetId, targetType)) {
            Integer id = collectMapper.selectOne(new LambdaQueryWrapper<Collect>().eq(Collect::getTargetId, targetId).eq(Collect::getTargetType, targetType)).getId();
            deleteCollectList(Arrays.asList(id));
            redisTemplate.delete("collect:userId:" + targetType + ":" + targetId);
            return "取消收藏成功";
        }
        Collect collect = new Collect();
        collect.setCollectGroupId(collectGroupId);
        collect.setTargetId(targetId);
        collect.setTargetType(targetType);
        collect.setCreateTime(LocalDateTime.now());
        int insert = collectMapper.insert(collect);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        redisTemplate.opsForValue().set("collect:userId:" + targetType + ":" + targetId, targetId);
        noticeClient.sendLikeNotice(new NoticeVo(Integer.parseInt(request.getHeader("userid").toString()), receviceUserId, 21003, "收藏", targetId, targetType));
        return "收藏成功";
    }

    @Override
    public String deleteCollectList(List<Integer> ids) {
        for (Integer targetId : ids) {
            if (targetId <= 0) {
                throw new LinyiException(ResultCodeEnum.DATA_ERROR);
            }
        }
        int delete = collectMapper.deleteBatchIds(ids);
        if (delete <= 0) {
            throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
        } else if (delete != ids.size()) {
            return "输入的id有问题，删除部分成功";
        }
        return "删除成功";
    }

    @Override
    public String updateCollect(Collect collect) {
        collect.setUpdateTime(LocalDateTime.now());
        int i = collectMapper.updateById(collect);
        if (i != 1) {
            throw new LinyiException(ResultCodeEnum.UPDATE_ERROR);
        }
        return "修改成功";
    }

    /**
     * 判断某个目标是否已被收藏
     *
     * @param targetId   目标对象的ID
     * @param targetType 目标对象的类型
     * @return 如果目标对象已被收藏，则返回true；否则返回false
     */
    @Override
    public Boolean isCollect(Integer targetId, Integer targetType) {
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<Collect>()
                .eq(Collect::getTargetId, targetId)
                .eq(Collect::getTargetType, targetType);
        List<Collect> collects = collectMapper.selectList(queryWrapper);
        return collects.size() > 0 ? true : false;
    }

}
