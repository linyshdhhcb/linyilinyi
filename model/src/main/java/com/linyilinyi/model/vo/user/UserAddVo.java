package com.linyilinyi.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/24
 * @ClassName: UserAddVo
 */
@Data
public class UserAddVo {

    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,26}$", message = "账号只能包含数字和字母，长度：6-26")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*()\\-_=+{};:,<.>?/]{6,26}$", message = "允许字母、数字和指定的特殊字符，长度：6-26")
    @Schema(description = "密码")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*()\\-_=+{};:,<.>?/]{6,26}$", message = "允许字母、数字和指定的特殊字符，长度：6-26")
    @Schema(description = "密码")
    private String passwords;

    @Schema(description = "头像")
    private String image;

    @Schema(description = "手机号")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    @Schema(description = "邮箱")
    private String mail;

}
