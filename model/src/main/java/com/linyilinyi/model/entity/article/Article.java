package com.linyilinyi.model.entity.article;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 文章信息表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "Article", description = "文章信息表")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.NONE)
    private Integer id;

    @NotBlank(message = "文章标题不能为空")
    @Schema(description = "文章标题")
    private String title;

    @NotBlank(message = "文章内容不能为空")
    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "封面url")
    private String url;


    @Schema(description = "文章类型")
    private Integer type;

    @NotNull(message = "作者ID不能为空")
    @Schema(description = "作者ID")
    private Integer userId;

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "用户昵称不能为空")
    @Schema(description = "用户昵称")
    private String nickname;


    @Schema(description = "封面MD5")
    private String imageMd5;

    @Schema(description = "图片审核状态")
    private Integer imageStatus;

    @Schema(description = "文章审核状态")
    private Integer articleStatus;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private Integer target_type;


}

