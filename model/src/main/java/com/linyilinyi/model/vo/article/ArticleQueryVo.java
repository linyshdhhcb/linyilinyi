package com.linyilinyi.model.vo.article;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.linyilinyi.common.model.FrameTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/22
 * @ClassName: ArticleQueryVo
 */
@Data
public class ArticleQueryVo extends FrameTime {

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章类型")
    private Integer type;

    @Schema(description = "作者ID")
    private Integer userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "封面MD5")
    private String imageMd5;

    @Schema(description = "图片审核状态")
    private Integer articleStatus;
}
