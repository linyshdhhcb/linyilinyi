package com.linyilinyi.model.vo.user;

import com.linyilinyi.model.vo.code.Code;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/29
 * @ClassName: LoginVo
 */
@Data
public class LoginVo /*extends Code*/ {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;
}
