package com.linyilinyi.model.entity.video;

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
 * 历史播放表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "Play", description = "历史播放表")
public class Play implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "播放的视频ID")
    private Integer videoId;

    @Schema(description = "播放视频的用户ID")
    private Integer userId;

    @Schema(description = "播放的创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "播放的修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;


}
