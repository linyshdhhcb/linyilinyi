package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.model.entity.comment.Comment;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-24
 */
public interface CommentService extends IService<Comment> {

    List<Comment> getTargetCommentList(Integer targetId, Integer targetType);
}
