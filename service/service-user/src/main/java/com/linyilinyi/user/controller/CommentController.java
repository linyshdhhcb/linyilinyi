package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.comment.Comment;
import com.linyilinyi.user.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Tag(name = "评论管理模块")
@RestController
@RequestMapping("comment")
@SuppressWarnings({"unchecked", "rawtypes"})
public class CommentController {

    @Resource
    private CommentService commentService;

    @Operation(summary = "根据对象id获取评论")
    @GetMapping("/getTargetCommentList/{target}/{targetType}")
    public Result<List<Comment>> getTargetCommentList(@NotNull(message = "targetId不能为空") @PathVariable Integer targetId, @NotNull(message = "targetType不能为空") @PathVariable Integer targetType) {
        return Result.ok(commentService.getTargetCommentList(targetId,targetType));
    }
}
