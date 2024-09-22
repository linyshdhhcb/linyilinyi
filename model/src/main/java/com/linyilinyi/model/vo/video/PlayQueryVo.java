package com.linyilinyi.model.vo.video;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.linyilinyi.common.model.FrameTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/22
 * @ClassName: PlayQueryVo
 */
@Data
public class PlayQueryVo extends FrameTime {


    @Schema(description = "播放的视频ID")
    private Integer videoId;

    @Schema(description = "播放视频的用户ID")
    private Integer userId;

}
