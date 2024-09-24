package com.linyilinyi.model.entity.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 文章数据统计表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "ArticleData", description = "文章数据统计表")
public class ArticleData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "文章ID")
    private Integer articleId;

    @Schema(description = "该文章阅读量")
    private Integer readCount;

    @Schema(description = "该文章评论数")
    private Integer commentCount;

    @Schema(description = "点赞量")
    private Integer likeCount;

    @Schema(description = "收藏量")
    private Integer collectCount;

    @Schema(description = "分享数量")
    private Integer shareCount;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;


}
