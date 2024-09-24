package com.linyilinyi.model.entity.reviewer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 审核表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "reviewer", description = "审核表")
public class Reviewer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "审核对象id")
    private Integer targetId;

    @Schema(description = "审核对象类型")
    private Integer type;

    @Schema(description = "审核状态")
    private Integer status;

    @Schema(description = "审核者id")
    private Integer reviewerId;

    @Schema(description = "审核评论")
    private String reviewerComment;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除")
    @TableLogic
    private Integer isDelete;


}
