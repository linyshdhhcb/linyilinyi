//package com.linyilinyi.user.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import javax.annotation.PostConstruct;
//import java.util.Map;
//import java.util.Set;
//
//@Component
//public class PathMappingUtils {
//
//    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
//
//    public PathMappingUtils(RequestMappingHandlerMapping requestMappingHandlerMapping) {
//        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
//    }
//    @PostConstruct
//    public void printAllMappings() {
//        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
//        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
//            RequestMappingInfo info = entry.getKey();
//            HandlerMethod method = entry.getValue();
//
//            // 获取控制器类的基路径
//            String controllerBasePath = getControllerBasePath(method);
//            // 获取方法的路径
//            PatternsRequestCondition patternsCondition = info.getPatternsCondition();
//            Set<String> methodPatterns = patternsCondition != null ? patternsCondition.getPatterns() : null;
//
//            // 过滤掉 Spring Boot 自动配置的控制器类
//            if (isCustomController(method)) {
//                if (methodPatterns != null) {
//                    for (String pattern : methodPatterns) {
//                        String fullRequestPath = controllerBasePath + pattern;
//                        System.out.println("Controller: " + method.getBeanType().getName());
//                        System.out.println("Method: " + method.getMethod().getName());
//                        System.out.println("Full Request Path: " + fullRequestPath);
//                        System.out.println("--------------------------------------------------");
//                    }
//                } else {
//                    System.out.println("Controller: " + method.getBeanType().getName());
//                    System.out.println("Method: " + method.getMethod().getName());
//                    System.out.println("No patterns found for this method.");
//                    System.out.println("--------------------------------------------------");
//                }
//            }
//        }
//    }
//
//    private boolean isCustomController(HandlerMethod handlerMethod) {
//        // 这里可以根据你的包结构来判断是否是自定义的控制器类
//        // 例如，假设你的自定义控制器类都在 com.linyilinyi.user.controller 包下
//        return handlerMethod.getBeanType().getPackage().getName().startsWith("com.linyilinyi.user.controller");
//    }
//
//    private String getControllerBasePath(HandlerMethod handlerMethod) {
//        Class<?> controllerClass = handlerMethod.getBeanType();
//        RequestMapping classAnnotation = controllerClass.getAnnotation(RequestMapping.class);
//        if (classAnnotation != null && classAnnotation.value().length > 0) {
//            return classAnnotation.value()[0];
//        }
//        return "";
//    }
//
//
//
//}
