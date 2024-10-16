package com.linyilinyi.model.vo.user;

import com.linyilinyi.model.vo.code.Code;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/29
 * @ClassName: Register
 */
@Data
public class Register extends Code {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认密码")
    private String confirmPassword;

    @NotBlank(message = "邮箱不能为空")
    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像")
    private String image;

}
