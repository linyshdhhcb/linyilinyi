package com.linyilinyi.model.vo.dictionary;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/21
 * @ClassName: DictionaryLabelQueryVo
 */
@Data
public class DictionaryLabelQueryVo {

    @Schema(description = "内容")
    private String label;

    @Schema(description = "value")
    private Integer code;

    @Schema(description = "字典类型id")
    private Integer dictionaryId;

    @Schema(description = "创建人")
    private Integer createUserId;

    @Schema(description = "修改人")
    private Integer updateUserId;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "最后时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;


}
