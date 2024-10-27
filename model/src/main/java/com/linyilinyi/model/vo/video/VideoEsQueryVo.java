package com.linyilinyi.model.vo.video;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/25
 * @ClassName: VideoEsQueryVo
 */
@Data
@Schema(name = "VideoEsQueryVo 视频信息索引", description = "视频信息索引")
public class VideoEsQueryVo {

    @Schema(description = "视频时长")
    private Long length;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "组合搜索（name,nickname,tag）")
    private String combined_fields;
}
