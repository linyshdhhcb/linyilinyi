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
@Schema(name = "Review 审核表", description = "审核表")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Integer reviewId;

    @Schema(description = "审核对象ID")
    private Integer mediaId;

    @Schema(description = "类型")
    private Integer mediaType;

    @Schema(description = "审核状态（待审核：10001，通过：10002，未通过：10003）")
    private Integer status;

    @Schema(description = "审核者")
    private String reviewer;

    @Schema(description = "审核时间，默认为当前时间")
    private LocalDateTime reviewDate;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "逻辑删除")
    @TableLogic
    private Integer isDelete;


}
