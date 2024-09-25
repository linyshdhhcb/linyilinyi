package com.linyilinyi.model.vo.comment;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.linyilinyi.model.entity.comment.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/25
 * @ClassName: CommentVo
 */
@Data
public class CommentVo extends Comment {

    @Schema(description = "评论所在的对象ID")
    private Integer targetId;

    @Schema(description = "发表评论的用户ID")
    private Integer userId;

    @Schema(description = "评论人昵称")
    private String nickName;

    @Schema(description = "评论人头像")
    private String image;

    @Schema(description = "被评论人url")
    private String url;

    @Schema(description = "评论类型")
    private Integer targetType;

    @Schema(description = "父级评论ID")
    private Integer parentId;

    @Schema(description = "顶层评论ID(顶层评论默认：0)")
    private Integer topId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "评论创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;



}
