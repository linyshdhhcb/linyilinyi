package com.linyilinyi.model.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 用户主页表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "UserHome", description = "用户主页表")
public class UserHome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户id")
    private Integer userId;

    @Schema(description = "收藏夹是否开放")
    private Integer collectGroup;

    @Schema(description = "最近点赞是否开放")
    private Integer remotelyLike;

    @Schema(description = "粉丝列表")
    private Integer fansList;

    @Schema(description = "关注列表")
    private Integer idolList;


}
