package com.linyilinyi.model.vo.video;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/22
 * @ClassName: PlayAddVo
 */
@Data
public class PlayAddVo {

    @Schema(description = "播放的视频ID")
    private Integer videoId;

    @Schema(description = "播放视频的用户ID")
    private Integer userId;


}
