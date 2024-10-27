package com.linyilinyi.model.vo.article;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.linyilinyi.common.model.FrameTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/26
 * @ClassName: ArticleEsQueryVo
 */
@Data
@Schema(name = "ArticleEsQueryVo 文章信息索引查询条件", description = "文章信息索引查询条件")
public class ArticleEsQueryVo extends FrameTime {

    @Schema(description = "文章类型")
    private Integer type;

    @Schema(description = "组合搜索（title,nickname）")
    private String combined_fields;


}
