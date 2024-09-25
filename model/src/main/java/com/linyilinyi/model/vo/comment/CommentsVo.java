package com.linyilinyi.model.vo.comment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/25
 * @ClassName: CommentsVo
 */
@Data
public class CommentsVo extends CommentAddVo{

    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "发表评论的用户ID")
    private Integer userId;

    @Schema(description = "评论人昵称")
    private String nickName;

    @Schema(description = "评论人头像")
    private String image;

    @Schema(description = "评论创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "顶级评论下的子列表")
   private List<CommentsVo> children;
}
