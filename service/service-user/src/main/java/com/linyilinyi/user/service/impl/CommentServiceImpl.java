package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.comment.Comment;
import com.linyilinyi.model.entity.likes.Likes;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.model.vo.comment.CommentAddVo;
import com.linyilinyi.model.vo.comment.CommentsVo;
import com.linyilinyi.user.mapper.CommentMapper;
import com.linyilinyi.user.mapper.LikesMapper;
import com.linyilinyi.user.service.CommentService;
import com.linyilinyi.user.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserService userService;
    @Resource
    private LikesMapper likesMapper;

    @Resource
    private HttpServletRequest request;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public CommentsVo addComment(CommentAddVo commentAddVo) {
        CommentsVo commentsVo = new CommentsVo();
        User user = userService.getUserById(Integer.parseInt(request.getHeader("userid")));
        BeanUtils.copyProperties(commentAddVo, commentsVo);
        commentsVo.setUserId(Integer.parseInt(request.getHeader("userid")));
        commentsVo.setImage(user.getImage());
        commentsVo.setCreateTime(LocalDateTime.now());
        commentsVo.setNickName(user.getNickname());
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentsVo, comment);
        int insert = commentMapper.insert(comment);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        commentsVo.setId(comment.getId());
        return commentsVo;
    }

    @Override
    public PageResult<CommentsVo> getCommentList(Integer targetId, Integer targetType, Integer topId, long pageNo, long pageSize) {
        List<Comment> children = commentMapper.getCommentChild(targetId, targetType, topId);
        children.add(commentMapper.selectOne(new LambdaQueryWrapper<Comment>().eq(Comment::getId, topId)));
        //使用HashSet去重
        List<Comment> comments = new ArrayList<>(new HashSet<>(children));
        List<CommentsVo> commentsVoArrayList = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment != null) {
                CommentsVo commentsVo = new CommentsVo();
                BeanUtils.copyProperties(comment, commentsVo);
                //获取用户信息
                User user = userService.getUserById(comment.getUserId());
                commentsVo.setNickName(user.getNickname());
                commentsVo.setImage(user.getImage());
                //获取评论点赞数
                Long l = likesMapper.selectCount(new LambdaQueryWrapper<Likes>().eq(Likes::getTargetId, 11203).eq(Likes::getId, comment.getId()));
                commentsVo.setLikesCount(l);
                commentsVoArrayList.add(commentsVo);
            }
        }

        //将list转map，以备使用
        Map<Integer, CommentsVo> commentsVoMap = commentsVoArrayList.stream().collect(Collectors.toMap(k -> k.getId(), v -> v, (k1, k2) -> k2));
        //最终返回list
        List<CommentsVo> topList = new ArrayList<>();

        Integer finalTopId = topId;
        commentsVoArrayList.stream().forEach(e -> {
            if (e.getId().equals(finalTopId) || finalTopId == 0) {
                topList.add(e);
            }
            CommentsVo commentsVoParent = commentsVoMap.get(e.getParentId());
            if (commentsVoParent != null) {
                if (commentsVoParent.getChildren() == null) {
                    commentsVoParent.setChildren(new ArrayList<CommentsVo>());
                }
                commentsVoParent.getChildren().add(e);
            }
        });

        List<CommentsVo> collect = topList.stream().skip((pageNo - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        return new PageResult<>(collect, commentsVoArrayList.size(), pageNo, pageSize);

    }

    @Override
    @Transactional
    public String deleteComment(Integer id) {
        Comment comment = getComment(id);
        if (Optional.ofNullable(comment).isEmpty()) {
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
        if (comment.getTopId() == 0) {
            // 防止commentList为null的情况
            List<CommentsVo> commentList = getCommentList(comment.getTargetId(), comment.getTargetType(), id, 1L, 100L).getItems();
            if (commentList == null) {
                return "删除失败";
            }
            deleteTree(commentList);
            return "删除成功";
        } else {
            int i = commentMapper.deleteById(id);
            //删除评论点赞信息
            deleteCommentLikes(comment.getId());
            if (i != 1) {
                throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
            }
            return "删除成功";
        }

    }

    private void deleteCommentLikes(Integer id) {
        List<Likes> likes = likesMapper.selectList(new LambdaQueryWrapper<Likes>().eq(Likes::getTargetId, 11203).eq(Likes::getId, id));
        int i = likesMapper.deleteBatchIds(likes.stream().map(Likes::getId).collect(Collectors.toList()));
        if (i != likes.size()) {
            throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
        }
    }

    private void deleteTree(List<CommentsVo> commentList) {
        for (CommentsVo commentsVo : commentList) {
            if (commentsVo.getChildren() != null) {
                deleteTree(commentsVo.getChildren());
                int i = commentMapper.deleteById(commentsVo.getId());
                if (i != 1) {
                    throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
                }
                //删除评论点赞
                deleteCommentLikes(commentsVo.getId());
            } else {
                int i = commentMapper.deleteById(commentsVo.getId());
                if (i != 1) {
                    throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
                }
                //删除评论点赞
                deleteCommentLikes(commentsVo.getId());
            }
        }
    }


    @Override
    public Comment getComment(Integer id) {
        return commentMapper.selectOne(new LambdaQueryWrapper<Comment>().eq(Comment::getId, id));
    }


}
