package com.linyilinyi.model.entity.other;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 排行榜表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "Leaderboard 排行榜表", description = "排行榜表")
public class Leaderboard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "对象ID")
    private Long targetId;

    @Schema(description = "得分")
    private Double score;

    @Schema(description = "排名")
    private Integer rank;

    @Schema(description = "排行榜类型")
    private Integer leaderboardType;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(description = "排行榜日期")
    private LocalDate recordDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;


}
