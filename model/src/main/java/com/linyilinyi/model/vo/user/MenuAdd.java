package com.linyilinyi.model.vo.user;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/17
 * @ClassName: MenuAdd
 */

@Schema(name = "MenuAdd 菜单新增对象", description = "菜单新增对象")
@Data
public class MenuAdd {

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "父菜单ID")
    private Long parentId;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "菜单类型（M目录 C菜单 F按钮）")
    private Integer menuType;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "备注")
    private String remark;

}
