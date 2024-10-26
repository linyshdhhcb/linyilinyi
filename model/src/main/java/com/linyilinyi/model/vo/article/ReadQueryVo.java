package com.linyilinyi.model.vo.article;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.linyilinyi.common.model.FrameTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/22
 * @ClassName: ReadQueryVo
 */
@Data
public class ReadQueryVo extends FrameTime {

    @Schema(description = "文章id")
    private Integer articleId;

    @Schema(description = "阅读文章用户id")
    private Integer userId;
}
