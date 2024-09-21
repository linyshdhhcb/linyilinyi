package com.linyilinyi.model.vo.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/17
 * @ClassName: VideoAddVo
 */
@Data
public class VideoAddVo {

    @NotNull(message = "视频时长不能为空")
    @Schema(description = "视频时长")
    private Long length;

    @NotNull(message = "视频大小不能为空")
    @Schema(description = "视频大小")
    private Long size;

    @NotBlank(message = "视频URL不能为空")
    @Schema(description = "视频在MinIO中的存储路径")
    private String url;

    @NotBlank(message = "视频封面URL不能为空")
    @Schema(description = "视频封面在MinIO中的存储路径")
    private String path;

    @NotBlank(message = "视频名称不能为空")
    @Schema(description = "视频名称")
    private String name;

    @Schema(description = "视频介绍")
    private String intro;

    @Schema(description = "视频标签")
    private String tag;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @NotNull(message = "视频审核状态不能为空")
    @Schema(description = "视频审核状态")
    private Integer videoStart;

    @NotNull(message = "图片审核状态不能为空")
    @Schema(description = "视频审核状态")
    private Integer imageStart;

    @NotBlank(message = "视频MD5不能为空")
    @Schema(description = "视频MD5")
    private String videoMd5;

    @NotBlank(message = "图片MD5不能为空")
    @Schema(description = "图片MD5")
    private String imageMd5;

}
