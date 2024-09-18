package com.linyilinyi.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/18
 * @ClassName: SizeRange
 */
@Data
public class SizeRange {

    @Schema(description = "最小")
    private Double min;

    @Schema(description = "最大")
    private Double max;
}
