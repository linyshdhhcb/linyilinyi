package com.linyilinyi.user.controller;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.comment.Comment;
import com.linyilinyi.model.vo.comment.CommentAddVo;
import com.linyilinyi.model.vo.comment.CommentVo;
import com.linyilinyi.model.vo.comment.CommentsVo;
import com.linyilinyi.user.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
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

    @Operation(summary = "发布评论")
    @PostMapping("/addComment")
    public Result<CommentsVo> addComment(@Valid @RequestBody CommentAddVo commentAddVo) {
        return Result.ok(commentService.addComment(commentAddVo));
    }

    @Operation(summary = "获取评论列表")
    @PostMapping("/getCommentList")
    public Result<PageResult<CommentsVo>> getCommentList(@NotNull(message = "targetId不能为空") @RequestParam Integer targetId,
                                                         @NotNull(message = "targetType不能为空") @RequestParam Integer targetType,
                                                         @RequestParam(required = false, defaultValue = "0") Integer topId,
                                                         @RequestParam(required = false, defaultValue = "1") long pageNo,
                                                         @RequestParam(required = false, defaultValue = "50") long pageSize) {
        return Result.ok(commentService.getCommentList(targetId, targetType, topId, pageNo, pageSize));
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/deleteComment/{id}")
    public Result<String> deleteComment(@PathVariable Integer id) {
        return Result.ok(commentService.deleteComment(id));
    }

    @Operation(summary = "根据id获取评论信息")
    @GetMapping("/getComment/{id}")
    public Result<Comment> getComment(@NotNull(message = "id不能为空")@PathVariable Integer id) {
        return Result.ok(commentService.getComment(id));
    }
}
