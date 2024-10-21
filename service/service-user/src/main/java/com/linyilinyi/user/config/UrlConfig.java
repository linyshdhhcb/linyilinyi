package com.linyilinyi.user.config;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@Slf4j
public class UrlConfig {

    @Autowired
    private RequestMappingHandlerMapping mappingHandlerMapping;
    /**
     * 在Bean初始化完成后执行的方法
     * 该方法用于收集并打印所有请求映射信息，包括URL、处理方法和类名
     */
    @PostConstruct
    public void urlInfo() {
        // 记录日志，开始收集请求信息
        log.info("***********************************************************");
        // 创建一个列表，用于存储所有请求映射信息的字典
        List<Map<String, String>> map = new ArrayList<>();

        // 遍历所有请求映射信息和对应的处理方法
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : mappingHandlerMapping.getHandlerMethods().entrySet()) {
            // 创建一个字典，用于存储单个请求映射的信息
            Map<String, String> m = new HashMap<>();
            // 获取请求映射信息
            RequestMappingInfo requestMappingInfo = entry.getKey();
            // 获取所有URL模式
            Set<PathPattern> patterns = requestMappingInfo.getPathPatternsCondition().getPatterns();
            // 遍历所有URL模式并存储到字典中
            for (PathPattern pattern : patterns) {
                m.put("url", pattern.getPatternString());
            }
            // 获取处理方法信息
            HandlerMethod handlerMethod = entry.getValue();
            // 存储方法名到字典中
            m.put("method", handlerMethod.getMethod().getName());
            // 存储类名到字典中
            m.put("class", handlerMethod.getMethod().getDeclaringClass().getName());
            // 获取请求方法条件
            RequestMethodsRequestCondition methodsCondition = requestMappingInfo.getMethodsCondition();
            // 遍历所有请求方法并存储到字典中
            for (RequestMethod method : methodsCondition.getMethods()) {
                m.put("type", method.toString());
            }
            // 将包含请求映射信息的字典添加到列表中
            if (m.get("class").contains("com.linyilinyi")){
                //排序
                map.add(m);
                Collections.sort(map, Comparator.comparing(o -> o.get("class")));
            }
        }
        // 打印所有请求映射信息的JSON格式字符串
        System.out.println(JSON.toJSONString(map));
    }
}
