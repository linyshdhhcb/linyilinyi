//package com.linyilinyi.gateway.filter;
//
//import com.github.xiaoymin.knife4j.spring.gateway.Knife4jGatewayProperties;
//import com.github.xiaoymin.knife4j.spring.gateway.discover.spi.GatewayServiceExcludeService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//import java.util.Set;
//import java.util.TreeSet;
//
//@Slf4j
//@Component
//public class MyExcludeService implements GatewayServiceExcludeService {
//    @Override
//    public Set<String> exclude(Environment environment, Knife4jGatewayProperties properties, List<String> services) {
//        log.info("自定义过滤器.");
//        if (!CollectionUtils.isEmpty(services)){
//            //return services.stream().filter(s -> s.contains("order")).collect(Collectors.toSet());
//            log.info("11");
//        }
//        return new TreeSet<>();
//    }
//}
