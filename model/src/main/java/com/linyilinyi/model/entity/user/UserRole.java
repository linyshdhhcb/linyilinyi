package com.linyilinyi.model.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

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
 * 用户角色表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "UserRole", description = "用户角色表")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户角色主键ID")
    private Long id;

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;


}
