package com.linyilinyi.model.entity.dictionary;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 数据字典类型表
 * </p>
 *
 * @author linyi
 */
@Data
    @Schema(name="DictionaryType",description = "数据字典类型表")
public class DictionaryType implements Serializable {

    private static final long serialVersionUID = 1L;

        @Schema(description = "主键")
    private Integer id;

        @Schema(description = "字典类型")
    private String type;

        @Schema(description = "字典类型名称")
    private String name;

        @Schema(description = "创建人")
    private Integer createUserId;

        @Schema(description = "创建时间")
    private LocalDateTime createTime;

        @Schema(description = "修改人")
    private Integer updateUserId;

        @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @TableLogic
    private Integer isDelete;


}
