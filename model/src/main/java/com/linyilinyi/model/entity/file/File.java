package com.linyilinyi.model.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "File", description = "文件表")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "文件名称不能为空")
    @Schema(description = "文件名称")
    private String fileName;

    @NotBlank(message = "文件类型不能为空")
    @Schema(description = "文件类型")
    private String fileType;

    @NotBlank(message = "存储目录（桶）不能为空")
    @Schema(description = "存储目录（桶）")
    private String bucket;

    @NotBlank(message = "文件url不能为空")
    @Schema(description = "文件url")
    private String fileUrl;

    @NotBlank(message = "文件储存地址不能为空")
    @Schema(description = "文件储存地址")
    private String filePath;

    @NotNull(message = "文件大小不能为空")
    @Schema(description = "文件大小（单位：KB）")
    private Double fileSize;

    @NotBlank(message = "文件md5不能为空")
    @Schema(description = "唯一标示md5")
    private String fileMd5;

    @NotBlank(message = "上传者不能为空")
    @Schema(description = "上传者")
    private Integer userId;

    @Schema(description = "状态")
    private Integer start;

    @NotBlank(message = "创建时间不能为空")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除")
    //@TableLogic
    private Integer isDelete;


}
