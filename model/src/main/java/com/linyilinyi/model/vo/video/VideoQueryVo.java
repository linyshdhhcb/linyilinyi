package com.linyilinyi.model.vo.video;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.linyilinyi.common.model.FrameTime;
import com.linyilinyi.model.entity.video.Video;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/14
 * @ClassName: VideoQueryVo
 */
@Data
@Schema(name = "VideoQueryVo 视频信息分页查询", description = "视频信息分页查询表")
public class VideoQueryVo extends FrameTime {

    @Schema(description = "视频时长")
    private Long length;

    @Schema(description = "视频名称")
    private String name;

    @Schema(description = "视频介绍")
    private String intro;

    @Schema(description = "视频标签")
    private String tag;

    @Schema(description = "作者ID")
    private Integer userId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "用户账号")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "组合搜索（name,nickname,tag）")
    private String combined_fields;


    @Schema(description = "视频审核状态")
    private Integer videoStart;
}
