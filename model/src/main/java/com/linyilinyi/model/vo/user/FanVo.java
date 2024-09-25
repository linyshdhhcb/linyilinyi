package com.linyilinyi.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/25
 * @ClassName: FanVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FanVo {

    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String image;
}
