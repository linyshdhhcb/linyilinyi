package com.linyilinyi.model.vo.article;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ArticleEsQueryVo {

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章类型")
    private Integer type;

    @Schema(description = "作者ID")
    private Integer userId;
    
    private String imageMd5;

    @Schema(description = "图片审核状态")
    private Integer imageStatus;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除标识（0：未删除；1：已删除）")
    @TableLogic
    private Integer isDelete;

}
