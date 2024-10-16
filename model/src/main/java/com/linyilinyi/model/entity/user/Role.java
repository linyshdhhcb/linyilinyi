package com.linyilinyi.model.entity.user;

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
 * 角色表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "Role 角色表", description = "角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色主键ID")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色权限字符串")
    private String code;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;


}
