package com.linyilinyi.model.entity.other;

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
 * 弹幕表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "Danmaku", description = "弹幕表")
public class Danmaku implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "所在视频ID")
    private Integer videoId;

    @Schema(description = "发表弹幕的用户ID")
    private Integer userId;

    @Schema(description = "弹幕内容")
    private String content;

    @Schema(description = "弹幕创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "弹幕修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "弹幕所处视频中的位置")
    private Integer place;

    @Schema(description = "弹幕显现方式（由左至右还是直接显现）")
    private Integer type;

    @Schema(description = "弹幕颜色")
    private Integer color;

    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;


}
