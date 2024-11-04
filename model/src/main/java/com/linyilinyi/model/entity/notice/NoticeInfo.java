package com.linyilinyi.model.entity.notice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.data.annotation.Id;

/**
 * <p>
 * 存储各种信息的通用表，包括私信、通知、公告和客服消息
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "NoticeInfo 存储各种信息的通用表，包括私信、通知、公告和客服消息", description = "存储各种信息的通用表，包括私信、通知、公告和客服消息")
public class NoticeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "发送者的用户ID")
    private Integer senderId;

    @Schema(description = "接收者的用户ID")
    private Integer receiverId;

    @Schema(description = "信息类型（1点赞 2评论 3收藏 4关注 5私信 6通知 7客服）")
    private Integer messageType;

    @Schema(description = "信息的内容")
    private String content;

    @Schema(description = "标记信息是否已读(已读：1，未读：0)")
    private Boolean isRead;

    @Schema(description = "信息的创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "信息的阅读时间")
    private LocalDateTime readTime;

    @Schema(description = "标题")
    private String announcementTitle;

    @Schema(description = "对象id")
    private Integer objectId;


}
