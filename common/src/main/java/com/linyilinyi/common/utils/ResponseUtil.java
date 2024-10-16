package com.linyilinyi.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linyilinyi.common.model.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class ResponseUtil {

    /**
     * 将结果对象写入HTTP响应中
     *
     * 此方法用于将一个结果对象（Result）转换为JSON格式，并写入到HTTP响应中
     * 它主要用于向客户端返回API调用的结果，包括状态码和响应体内容
     *
     * @param response HTTP响应对象，用于获取响应输出流并设置响应头信息
     * @param r 结果对象，包含要返回给客户端的数据
     */
    public static void out(HttpServletResponse response, Result r) {
        // 创建ObjectMapper实例，用于将Java对象转换为JSON格式
        ObjectMapper mapper = new ObjectMapper();
        // 设置HTTP响应状态码为200，表示请求成功
        response.setStatus(HttpStatus.OK.value());
        // 设置响应内容类型为UTF-8编码的JSON格式
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 获取响应输出流，并将结果对象转换为JSON格式后写入响应中
        try {
            mapper.writeValue(response.getWriter(), r);
        } catch (IOException e) {
            // 如果在写入过程中发生IO异常，则打印异常堆栈跟踪信息
            e.printStackTrace();
        }
    }

}
