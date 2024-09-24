package com.linyilinyi.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
    import io.swagger.v3.oas.annotations.media.Schema;
    import lombok.Data;
    import lombok.AllArgsConstructor;
    import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 粉丝表
 * </p>
 *
 * @author linyi
 */
@Data
    @Schema(name="Follow",description = "粉丝表")
public class Follow implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

        @Schema(description = "关注者id")
    private Integer fansId;

        @Schema(description = "被关注者id")
    private Integer idolId;

        @Schema(description = "关注时间")
    private LocalDateTime createTime;

        @Schema(description = "修改时间")
    private LocalDateTime updateTime;

        @Schema(description = "逻辑删除标示（0：未删除; 1:删除)")
    @TableLogic
    private Integer isDelete;


}
