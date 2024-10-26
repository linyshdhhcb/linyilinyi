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
 * @Date 2024/10/25
 * @ClassName: VideoEsVo
 */
@Data
@Schema(name = "VideoEsVo 视频信息索引", description = "视频信息索引")
public class VideoEsVo {


    @Schema(description = "主键ID")
    private Integer id;


    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "视频时长")
    private Long length;

    @Schema(description = "视频在MinIO中的存储路径")
    private String url;

    @Schema(description = "视频封面在MinIO中的存储路径")
    private String path;

    @Schema(description = "视频名称")
    private String name;

    @Schema(description = "视频介绍")
    private String intro;

    @Schema(description = "视频标签")
    private String tag;

    @Schema(description = "作者ID")
    private Integer userId;

    @Schema(description = "用户头像")
    private String image;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
