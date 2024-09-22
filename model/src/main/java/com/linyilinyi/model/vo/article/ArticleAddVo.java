package com.linyilinyi.model.vo.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/22
 * @ClassName: ArticleAddVo
 */
@Data
public class ArticleAddVo {

    @NotBlank(message = "title不能为空")
    @Schema(description = "文章标题")
    private String title;

    @NotBlank(message = "content不能为空")
    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "封面url")
    private String url;


    @Schema(description = "文章类型")
    private Integer type;


    @Schema(description = "封面MD5")
    private String imageMd5;

    @Schema(description = "图片审核状态")
    private Integer imageStatus;


    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
