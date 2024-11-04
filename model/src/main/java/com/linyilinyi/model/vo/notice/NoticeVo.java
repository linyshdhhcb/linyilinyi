package com.linyilinyi.model.vo.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/4
 * @ClassName: NoticeVo
 */
@Data
public class NoticeVo {

    @Schema(description = "收藏的用户ID")
    private Integer senderId;

    @Schema(description = "接收者的用户ID")
    private Integer receiverId;

    @Schema(description = "信息类型（收藏）")
    private Integer messageType;

    @Schema(description = "信息的内容")
    private String content;

    @Schema(description = "对象id")
    private Integer objectId;
}
