package com.linyilinyi.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyilinyi.model.entity.comment.Comment;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author linyi
 */
public interface CommentMapper extends BaseMapper<Comment> {


    List<Comment> getCommentChild(Integer targetId, Integer targetType, Integer topId);
}
