package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.article.client.ArticleClient;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.entity.likes.Likes;
import com.linyilinyi.model.entity.video.Video;
import com.linyilinyi.model.vo.notice.NoticeVo;
import com.linyilinyi.notice.client.NoticeClient;
import com.linyilinyi.user.mapper.LikesMapper;
import com.linyilinyi.user.service.LikesService;
import com.linyilinyi.video.client.VideoClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 点赞表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements LikesService {

    @Resource
    private LikesMapper likesMapper;

    @Resource
    private ArticleClient articleClient;

    @Resource
    private VideoClient videoClient;

    @Resource
    private NoticeClient noticeClient;

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

//    @Override
//    public String addLikes(Integer id, Integer targetType) {
//        // 步骤 1: 验证输入并获取 receiverId（接收者ID）
//        Integer receiverId = null;
//        switch (targetType) {
//            case 11201 -> {
//                Video video = videoClient.getVideoById(id).getData();
//                if (video == null) {
//                    throw new LinyiException(ResultCodeEnum.DATA_NULL); // 视频数据为空时抛出异常
//                }
//                receiverId = video.getUserId(); // 设置接收者ID为视频发布者的用户ID
//            }
//            case 11202 -> {
//                Article article = articleClient.getArticleById(id).getData();
//                if (article == null) {
//                    throw new LinyiException(ResultCodeEnum.DATA_NULL); // 文章数据为空时抛出异常
//                }
//                receiverId = article.getUserId(); // 设置接收者ID为文章发布者的用户ID
//            }
//            case 11203 -> {
//
//            }
//            default -> throw new LinyiException("该类型不存在");
//        }
//
//        // 步骤 2: 判断是否已点赞
//        boolean alreadyLiked = isLikes(id, targetType);
//        String userId = request.getHeader("userid"); // 从请求头中获取用户ID
//        if (userId == null) {
//            throw new LinyiException(ResultCodeEnum.UNAUTHORIZED); // 用户未登录时抛出异常
//        }
//        Integer parsedUserId = Integer.parseInt(userId);
//
//        if (alreadyLiked) {
//            // 已经点赞，执行取消点赞逻辑
//            LambdaQueryWrapper<Likes> queryWrapper = new LambdaQueryWrapper<Likes>()
//                    .eq(Likes::getTargetId, id)
//                    .eq(Likes::getTargetType, targetType);
//            int delete = likesMapper.delete(queryWrapper);
//            if (delete != 1) {
//                throw new RuntimeException("取消点赞失败"); // 删除数据库记录失败时抛出异常
//            }
//            // 删除 Redis 中的缓存
//            redisTemplate.delete("isLikes:userId:" + userId + ":" + id + ":" + targetType);
//            return "取消点赞成功";
//        } else {
//            // 未点赞，执行新增点赞逻辑
//            Likes likes = new Likes();
//            likes.setCreateTime(LocalDateTime.now());
//            likes.setTargetId(id);
//            likes.setTargetType(targetType);
//            likes.setUserId(parsedUserId);
//
//            int insert = likesMapper.insert(likes);
//            if (insert != 1) {
//                throw new LinyiException(ResultCodeEnum.INSERT_FAIL); // 数据库插入失败时抛出异常
//            }
//            // 更新 Redis 缓存
//            redisTemplate.opsForValue().set("isLikes:userId:" + userId + ":" + targetType, id);
//
//            // 发送点赞通知（异步调用）
//            try {
//                noticeClient.sendLikeNotice(new NoticeVo(parsedUserId, receiverId, 21001, "点赞", id));
//            } catch (Exception e) {
//                // 记录通知发送失败的日志（可以进一步处理通知失败的逻辑）
//                log.error("发送点赞通知失败", e);
//            }
//            return "点赞成功";
//        }
//    }

    @Override
    public String addLikes(Integer id, Integer targetType) {
        // 提前提取用户 ID
        String userId = request.getHeader("userid");
        if (userId == null) {
            throw new LinyiException(ResultCodeEnum.UNAUTHORIZED); // 用户未登录时抛出异常
        }
        Integer parsedUserId = Integer.parseInt(userId);

        // Step 1: 异步获取接收者ID
        CompletableFuture<Integer> receiverIdFuture = CompletableFuture.supplyAsync(() -> {
            return switch (targetType) {
                case 11201 -> {
                    Video video = videoClient.getVideoById(id).getData();
                    if (video == null) {
                        throw new LinyiException(ResultCodeEnum.DATA_NULL); // 视频数据为空时抛出异常
                    }
                    yield video.getUserId();
                }
                case 11202 -> {
                    Article article = articleClient.getArticleById(id).getData();
                    if (article == null) {
                        throw new LinyiException(ResultCodeEnum.DATA_NULL); // 文章数据为空时抛出异常
                    }
                    yield article.getUserId();
                }
                case 11203 -> {
                    throw new LinyiException("该类型不存在");
                }
                default -> throw new LinyiException("该类型不存在");
            };
        });

        // Step 2: 异步判断是否已点赞
        CompletableFuture<Boolean> isLikedFuture = CompletableFuture.supplyAsync(() -> isLikes(id, targetType, parsedUserId));

        // Step 3: 等待所有任务完成后，根据结果执行点赞或取消点赞逻辑
        return CompletableFuture.allOf(receiverIdFuture, isLikedFuture)
                .thenApplyAsync(ignored -> {
                    try {
                        Integer receiverId = receiverIdFuture.get(); // 获取接收者ID
                        boolean alreadyLiked = isLikedFuture.get(); // 获取是否已点赞状态

                        if (alreadyLiked) {
                            // 取消点赞
                            LambdaQueryWrapper<Likes> queryWrapper = new LambdaQueryWrapper<Likes>()
                                    .eq(Likes::getTargetId, id)
                                    .eq(Likes::getTargetType, targetType);
                            int delete = likesMapper.delete(queryWrapper);
                            if (delete != 1) {
                                throw new RuntimeException("取消点赞失败"); // 删除数据库记录失败时抛出异常
                            }
                            // 删除 Redis 缓存
                            redisTemplate.delete("isLikes:userId:" + userId + ":" + id + ":" + targetType);
                            return "取消点赞成功";
                        } else {
                            // 新增点赞
                            Likes likes = new Likes();
                            likes.setCreateTime(LocalDateTime.now());
                            likes.setTargetId(id);
                            likes.setTargetType(targetType);
                            likes.setUserId(parsedUserId);

                            int insert = likesMapper.insert(likes);
                            if (insert != 1) {
                                throw new LinyiException(ResultCodeEnum.INSERT_FAIL); // 插入数据库失败时抛出异常
                            }
                            // 更新 Redis 缓存
                            redisTemplate.opsForValue().set(
                                    "isLikes:userId:" + userId + ":" + targetType, id);

                            // 异步发送通知
                            CompletableFuture.runAsync(() -> {
                                try {
                                    noticeClient.sendLikeNotice(new NoticeVo(parsedUserId, receiverId, 21001, "点赞", id, targetType));
                                } catch (Exception e) {
                                    log.error("发送点赞通知失败", e); // 异常时记录日志
                                }
                            });
                            return "点赞成功";
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("异步操作失败", e); // 捕获异常并重新抛出
                    }
                }).join(); // 阻塞获取最终结果
    }
    @Override
    public Boolean isLikes(Integer id, Integer targetType, Integer userId) {
        // 从 Redis 检查是否已点赞
        Integer cachedId = redisTemplate.opsForValue().get("isLikes:userId:" + userId + ":" + id + ":" + targetType);
        if (cachedId != null && cachedId.equals(id)) {
            return true;
        }

        // 从数据库检查是否已点赞
        LambdaQueryWrapper<Likes> queryWrapper = new LambdaQueryWrapper<Likes>()
                .eq(Likes::getTargetId, id)
                .eq(Likes::getTargetType, targetType)
                .eq(Likes::getUserId, userId);
        Likes likes = likesMapper.selectOne(queryWrapper);
        return likes != null;
    }

}
