package com.linyilinyi.model.vo.user;

import com.linyilinyi.model.vo.code.Code;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/6
 * @ClassName: ForgetPasswordVo
 */
@Data
public class ForgetPasswordVo extends Code {

    @Schema(description = "邮箱")
    private String mail;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "确认密码")
    private String passwords;
}
