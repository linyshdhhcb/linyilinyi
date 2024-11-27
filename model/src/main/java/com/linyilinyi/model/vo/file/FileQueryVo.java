package com.linyilinyi.model.vo.file;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.linyilinyi.common.model.FrameTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/18
 * @ClassName: FileQueryVo
 */
@Data
@Schema(name = "FileQueryVo 文件分页查询参数")
public class FileQueryVo extends FrameTime {

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

    @Schema(description = "文件大小（单位：KB）")
    private Double fileSize;

    @Schema(description = "唯一标示md5")
    private String fileMd5;

    @Schema(description = "上传者")
    private Integer userId;

    @Schema(description = "状态")
    private Integer start;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Min(0)
    @Schema(description = "最小")
    private Double min;

    @Max(1024001)
    @Schema(description = "最大")
    private Double max;

    @Schema(description = "逻辑删除")
    //@TableLogic
    private Integer isDelete;

}
