package com.linyilinyi.model.entity.dictionary;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 数据字典内容表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "DictionaryLabel 数据字典内容表", description = "数据字典内容表")
public class DictionaryLabel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "内容")
    private String label;

    @Schema(description = "value")
    private Integer code;

    @Schema(description = "字典类型id")
    private Integer dictionaryId;

    @Schema(description = "创建人")
    private Integer createUserId;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "修改人")
    private Integer updateUserId;

    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableLogic
    private Integer isDelete;


}
