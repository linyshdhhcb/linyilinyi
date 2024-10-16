package com.linyilinyi.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/16
 * @ClassName: RoleQueryVo
 */
@Data
@Schema(name="RoleQueryVo",description = "角色分页查询对象")
public class RoleQueryVo {

    @Schema(description = "角色主键ID")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色权限字符串")
    private String code;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "最后时间")
    private LocalDateTime endTime;


}
