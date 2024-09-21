package com.linyilinyi.model.vo.user;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.linyilinyi.common.model.FrameTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/20
 * @ClassName: UserQueryVo
 */
@Data
public class UserQueryVo extends FrameTime {

    @Schema(description = "用户名")
    private String username;

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
}
