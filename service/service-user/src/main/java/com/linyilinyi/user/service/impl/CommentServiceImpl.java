package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.comment.Comment;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.model.vo.comment.CommentAddVo;
import com.linyilinyi.model.vo.comment.CommentVo;
import com.linyilinyi.model.vo.comment.CommentsVo;
import com.linyilinyi.user.mapper.CommentMapper;
import com.linyilinyi.user.service.CommentService;
import com.linyilinyi.user.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
    @Override
    public CommentsVo addComment(CommentAddVo commentAddVo) {
        CommentsVo commentsVo = new CommentsVo();
        User user = userService.getUserById(AuthContextUser.getUserId());
        BeanUtils.copyProperties(commentAddVo, commentsVo);
        commentsVo.setUserId(AuthContextUser.getUserId());
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
    public PageResult<CommentsVo> getCommentList(Integer targetId, Integer targetType, long pageNo, long pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<Comment>().eq(Comment::getTargetId, targetId).eq(Comment::getTargetType, targetType).orderByDesc(Comment::getCreateTime);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        ArrayList<CommentsVo> commentsVoArrayList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentsVo commentsVo = new CommentsVo();
            BeanUtils.copyProperties(comment, commentsVo);
            User user = userService.getUserById(comment.getUserId());

            commentsVo.setNickName(user.getNickname());
            commentsVo.setImage(user.getImage());
            commentsVoArrayList.add(commentsVo);
        }
        //获取顶层评论
        List<CommentsVo> topList = new ArrayList<>();
        for (CommentsVo commentsVo : commentsVoArrayList) {
            if (commentsVo.getParentId() == 0 && commentsVo.getTopId() == 0) {
                topList.add(commentsVo);
            }
        }
        int total = topList.size();
        //获取子评论
        topList.stream().forEach(e -> {
            e.setChildren(commentsVoArrayList.stream().filter(f -> f.getTopId().equals(e.getId())).collect(Collectors.toList()));
        });
        List<CommentsVo> collect = topList.stream().skip((pageNo - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        return new PageResult<>(collect, total, pageNo, pageSize);

    }
}
