package com.linyilinyi.model.entity.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>
 * 视频信息表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "Video", description = "视频信息表")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键ID")
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "用户昵称不能为空")
    @Schema(description = "用户昵称")
    private String nickname;

    @NotNull(message = "视频时长不能为空")
    @Schema(description = "视频时长")
    private Long length;

    @NotBlank(message = "视频大小不能为空")
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

    @NotBlank(message = "作者ID不能为空")
    @Schema(description = "作者ID")
    private Integer userId;

    @NotBlank(message = "用户头像不能为空")
    @Schema(description = "用户头像")
    private String image;

    @NotBlank(message = "创建时间不能为空")
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @NotNull(message = "逻辑删除标识不能为空")
    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;


}
