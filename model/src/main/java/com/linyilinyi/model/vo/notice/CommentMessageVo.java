package com.linyilinyi.model.vo.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/4
 * @ClassName: CommentMessageVo
 */
@Data
public class CommentMessageVo {

    @Schema(description = "评论的用户ID")
    private Long senderId;

    @Schema(description = "接收者的用户ID")
    private Long receiverId;

    @Schema(description = "信息类型（2评论）")
    private Integer messageType;

    @Schema(description = "信息的内容")
    private String content;

    @Schema(description = "对象id")
    private Integer objectId;

}
