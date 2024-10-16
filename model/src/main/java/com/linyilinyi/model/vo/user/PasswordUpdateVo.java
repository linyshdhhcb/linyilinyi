package com.linyilinyi.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/29
 * @ClassName: PasswordUpdateVo
 */
@Data
public class PasswordUpdateVo {

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码")
    private String password;

    @NotBlank(message = "新密码不能为空")
    @Schema(description = "新密码")
    private String newPassword;

    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认密码")
    private String confirmPassword;
}
