package com.linyilinyi.model.vo.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.linyilinyi.common.model.FrameTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/8
 * @ClassName: OperLogQueryVo
 */
@Data
public class OperLogQueryVo extends FrameTime {

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


    @Schema(description = "操作状态（0正常 1异常）")
    private Integer status;

    @Schema(description = "错误消息")
    private String errorMsg;

    @Schema(description = "最大方法执行耗时（单位：毫秒）")
    private Long maxTakeTime;

    @Schema(description = "最小方法执行耗时（单位：毫秒）")
    private Long minTakeTime;


}
