package com.linyilinyi.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/18
 * @ClassName: AddRoleMenu
 */
@Data
@Schema(name = "AddRoleMenu 添加角色菜单对象类", description = "添加角色菜单对象类")
public class AddRoleMenu {

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "菜单ID集合")
    private List<Long> menuIds;
}
