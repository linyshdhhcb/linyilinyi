package com.linyilinyi.model.vo.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/25
 * @ClassName: CommentAddVO
 */
@Data
public class CommentAddVo {

    @NotNull(message = "评论所在的对象ID不能为空")
    @Schema(description = "评论所在的对象ID")
    private Integer targetId;

    @NotNull(message = "评论类型不能为空")
    @Schema(description = "评论类型")
    private Integer targetType;

    @NotNull(message = "发表评论的用户ID不能为空）")
    @Schema(description = "父级评论ID")
    private Integer parentId;

    @NotNull(message = "顶层评论ID不能为空（顶层评论默认：0）")
    @Schema(description = "顶层评论ID")
    private Integer topId;

    @NotBlank(message = "评论内容不能为空")
    @Schema(description = "评论内容")
    private String content;
}
