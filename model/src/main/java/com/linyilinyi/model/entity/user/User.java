package com.linyilinyi.model.entity.user;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "User", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "盐")
    private String salt;

    @Schema(description = "用户状态")
    private Integer status;

    @Schema(description = "头像")
    private String image;

    @Schema(description = "性别（1：男；2：女）")
    private Integer gender;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "简介")
    private String intro;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "邮箱")
    private String mail;

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
    @Schema(description = "用户权限列表")
    private List<String> userPermsList;


}
