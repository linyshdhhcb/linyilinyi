package com.linyilinyi.model.vo.other;

import com.linyilinyi.common.model.FrameTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/12/1
 * @ClassName: LeaderboardQueryVo
 */
@Data
public class LeaderboardQueryVo extends FrameTime {

    @Schema(description = "对象ID")
    private Long targetId;

    @Schema(description = "对象类型")
    private Integer targetType;

    @Schema(description = "最大得分")
    private Double scoreMax;

    @Schema(description = "最小得分")
    private Double scoreMin;

    @Schema(description = "最高排名")
    private Integer rankMax;

    @Schema(description = "最低排名")
    private Integer rankMin;

    @Schema(description = "排行榜类型")
    private Integer leaderboardType;
}
