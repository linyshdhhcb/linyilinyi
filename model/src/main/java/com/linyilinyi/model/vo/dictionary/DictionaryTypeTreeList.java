package com.linyilinyi.model.vo.dictionary;

import com.baomidou.mybatisplus.annotation.TableField;
import com.linyilinyi.model.entity.dictionary.DictionaryLabel;
import com.linyilinyi.model.entity.dictionary.DictionaryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/21
 * @ClassName: DictionaryTypeTreeList
 */
@Data
public class DictionaryTypeTreeList extends DictionaryType {


    @TableField(exist = false)
    @Schema(description = "字典内容子")
    private List<DictionaryLabel> dictionaryLabelChild;
}
