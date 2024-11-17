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
 * 菜单表
 * </p>
 *
 * @author linyi
 */
@Data
@NoArgsConstructor
@Schema(name = "Menu 菜单表", description = "菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "菜单主键ID")
    private Long id;

    @Schema(description = "菜单名称")
    private String name;


    @Schema(description = "父菜单ID")
    private Long parentId;


    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "菜单类型（12001目录 12002菜单 12003按钮）")
    private Integer menuType;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;

    @Schema(description = "子菜单")
    @TableField(exist = false)
    private List<Menu> children;

    @TableField(exist = false)
    @Schema(description = "标签")
    private String tag;


    public Menu(String name, Integer menuType, String path, String perms, String remark, String tag,LocalDateTime createTime) {
        this.name = name;
        this.menuType = menuType;
        this.path = path;
        this.perms = perms;
        this.remark = remark;
        this.createTime = createTime;
        this.tag = tag;
    }
}
