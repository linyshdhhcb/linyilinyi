package com.linyilinyi.model.entity.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "Comment", description = "评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "评论所在的对象ID（1.视频 2.文章）")
    private Integer targetId;

    @Schema(description = "发表评论的用户ID")
    private Integer userId;

    @Schema(description = "父级评论ID")
    private Integer parentId;

    @Schema(description = "顶层评论ID")
    private Integer topId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "评论创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "评论修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;


}
