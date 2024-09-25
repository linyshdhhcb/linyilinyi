package com.linyilinyi.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.model.entity.comment.Comment;
import com.linyilinyi.model.vo.comment.CommentAddVo;
import com.linyilinyi.model.vo.comment.CommentVo;
import com.linyilinyi.model.vo.comment.CommentsVo;

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


    CommentsVo addComment(CommentAddVo commentAddVo);

    PageResult<CommentsVo> getCommentList(Integer targetId, Integer targetType, long pageNo, long pageSize);

}
