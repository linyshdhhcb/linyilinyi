package com.linyilinyi.model.vo.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/4
 * @ClassName: NoticeVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeVo {

    @Schema(description = "发送者用户ID")
    private Integer senderId;

    @Schema(description = "接收者用户ID")
    private Integer receiverId;

    @Schema(description = "信息类型")
    private Integer messageType;

    @Schema(description = "信息的内容")
    private String content;

    @Schema(description = "对象id")
    private Integer objectId;
}
