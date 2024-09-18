package com.linyilinyi.entity.po;

import java.time.LocalDateTime;
import java.io.Serializable;
    import io.swagger.v3.oas.annotations.media.Schema;
    import lombok.Data;
    import lombok.AllArgsConstructor;
    import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author linyi
 */
@Data
    @Schema(name="File",description = "文件表")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

        @Schema(description = "主键id")
    private Integer id;

        @Schema(description = "文件名称")
    private String fileName;

        @Schema(description = "文件类型")
    private String fileType;

        @Schema(description = "存储目录（桶）")
    private String bucket;

        @Schema(description = "文件url")
    private String fileUrl;

        @Schema(description = "文件储存地址")
    private String filePath;

        @Schema(description = "唯一标示md5")
    private String fileMd5;

        @Schema(description = "上传者")
    private Integer userId;

        @Schema(description = "状态")
    private Integer start;

        @Schema(description = "创建时间")
    private LocalDateTime createTime;

        @Schema(description = "修改时间")
    private LocalDateTime updateTime;

        @Schema(description = "逻辑删除")
    private Long idDelete;


}
