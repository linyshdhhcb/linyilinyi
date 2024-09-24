package com.linyilinyi.model.entity.article;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 阅读记录表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "Read", description = "阅读记录表")
public class Read implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    private Integer id;

    @Schema(description = "文章id")
    private Integer articleId;

    @Schema(description = "阅读文章用户id")
    private Integer userId;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除")
    @TableLogic
    private Integer isDelete;


}
