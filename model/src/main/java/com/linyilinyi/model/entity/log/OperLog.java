package com.linyilinyi.model.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 操作日志记录
 * </p>
 *
 * @author linyi
 */
@Data
@Schema(name = "OperLog 操作日志记录", description = "操作日志记录")
public class OperLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "日志主键")
    private Long id;

    @Schema(description = "模块标题")
    private String title;

    @Schema(description = "日志内容")
    private String content;

    @Schema(description = "方法名称")
    private String method;

    @Schema(description = "请求方式")
    private String requestMethod;

    @Schema(description = "操作人员")
    private String operName;

    @Schema(description = "请求URL")
    private String requestUrl;

    @Schema(description = "请求IP地址")
    private String ip;

    @Schema(description = "IP归属地")
    private String ipLocation;

    @Schema(description = "请求参数")
    private String requestParam;

    @Schema(description = "方法响应参数")
    private String responseResult;

    @Schema(description = "操作状态（0正常 1异常）")
    private Integer status;

    @Schema(description = "错误消息")
    private String errorMsg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "操作时间")
    private LocalDateTime operTime;

    @Schema(description = "方法执行耗时（单位：毫秒）")
    private Long takeTime;


}
