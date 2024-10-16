package com.linyilinyi.model.vo.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/29
 * @ClassName: Code
 */
@Data
public class Code {

    @Schema(description = "状态码")
    private Integer code;

    @Schema(description = "键")
    private String codeKey;
}
