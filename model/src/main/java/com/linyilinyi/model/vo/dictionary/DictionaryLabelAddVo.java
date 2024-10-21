package com.linyilinyi.model.vo.dictionary;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/21
 * @ClassName: DictionaryLabelAddVo
 */
@Data
public class DictionaryLabelAddVo {

    @Schema(description = "内容")
    private String label;

    @Schema(description = "value")
    private Integer code;

    @Schema(description = "字典类型id")
    private Integer dictionaryId;

}
