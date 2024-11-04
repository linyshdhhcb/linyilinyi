package com.linyilinyi.model.vo.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/4
 * @ClassName: NoticeSystemVo
 */
@Data
public class NoticeSystemVo extends NoticeVo{

    @Schema(description = "标题")
    private String announcementTitle;
}
