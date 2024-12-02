package com.linyilinyi.model.vo.other;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/12/2
 * @ClassName: Hot
 */
@Data
public class Hot {

    @Schema(description = "浏览量")
    private Integer viewCount;

    @Schema(description = "评论数")
    private Integer commentCount;

    @Schema(description = "点赞量")
    private Integer likeCount;

    @Schema(description = "收藏量")
    private Integer collectCount;

    @Schema(description = "分享数量")
    private Integer shareCount;
}
