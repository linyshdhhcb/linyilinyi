package com.linyilinyi.model.entity.likes;

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
 * 视频、评论、文章点赞表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "Likes", description = "视频、评论、文章点赞表")
public class Likes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "点赞者用户ID")
    private Integer userId;

    @Schema(description = "点赞对象的ID")
    private Integer targetId;

    @Schema(description = "点赞对象类型")
    private Integer targetType;

    @Schema(description = "点赞创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "点赞修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;


}
