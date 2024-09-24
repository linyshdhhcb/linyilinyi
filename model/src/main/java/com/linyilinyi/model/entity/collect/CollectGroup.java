package com.linyilinyi.model.entity.collect;

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
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 收藏夹表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "CollectGroup", description = "收藏夹表")
public class CollectGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private Integer id;

    @NotBlank(message = "收藏夹名称不能为空")
    @Schema(description = "收藏夹名称")
    private String name;

    @NotNull(message = "收藏夹所属用户ID不能为空")
    @Schema(description = "收藏夹所属用户ID")
    private Integer userId;

    @Schema(description = "收藏夹状态")
    private Integer status;

    @Schema(description = "收藏夹创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "收藏夹修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;


}
