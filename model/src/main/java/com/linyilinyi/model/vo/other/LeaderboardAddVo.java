package com.linyilinyi.model.vo.other;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Description
 * @Author linyi
 * @Date 2024/12/1
 * @ClassName: LeaderboardAddVo
 */
@Data
public class LeaderboardAddVo {

    @Schema(description = "对象ID")
    private Long targetId;

    @Schema(description = "对象类型")
    private Integer targetType;

    @Schema(description = "得分")
    private Double score;

    @Schema(description = "排名")
    private Integer rank;

    @Schema(description = "排行榜类型")
    private Integer leaderboardType;

}
