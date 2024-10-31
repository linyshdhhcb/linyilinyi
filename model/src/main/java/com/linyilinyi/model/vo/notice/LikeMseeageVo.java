package com.linyilinyi.model.vo.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/31
 * @ClassName: LikeMseeageVo
 */
@Data
@Accessors(chain = true)
public class LikeMseeageVo {

    @Schema(description = "点赞者的用户ID")
    private Long senderId;

    @Schema(description = "点赞者昵称")
    private String senderNickname;

    @Schema(description = "接收者的用户ID")
    private Long receiverId;

    @Schema(description = "信息类型（点赞）")
    private Integer messageType;

    @Schema(description = "信息的内容")
    private String content;

    @Schema(description = "对象id")
    private Integer objectId;
}
