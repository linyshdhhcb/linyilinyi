//package com.linyilinyi.user.config;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.linyilinyi.model.entity.user.Menu;
//import com.linyilinyi.model.vo.user.MenuAdd;
//import com.linyilinyi.user.service.MenuService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//
//import javax.annotation.PostConstruct;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.concurrent.CompletableFuture;
//
//import static com.linyilinyi.common.constant.SystemConstant.DOMAIN;
//
///**
// * @Description 获取knife4j的API文档，并插入菜单表数据库
// * @Author linyi
// * @Date 2024/10/20
// * @ClassName: UrlConfig
// */
//@Configuration
//@Slf4j
//@EnableScheduling
//public class UrlConfig {
//
//    @Autowired
//    private ThreadPoolTaskScheduler taskScheduler;
//
//    @Autowired
//    private MenuService menuService;
//@Autowired
//private RedisTemplate redisTemplate;
//
//    @Bean
//    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
//        return new ThreadPoolTaskScheduler();
//    }
//
//    private HashMap<String, Long> getUrlInfoMap() {
//        HashMap<String, Long> map = new HashMap<>();
//        //视频模块
//        map.put(DOMAIN + "8601/v3/api-docs/default", 1L);
//        //用户模块
//        map.put(DOMAIN + "8602/v3/api-docs/default", 2L);
//        //文章模块
//        map.put(DOMAIN + "8603/v3/api-docs/default", 3L);
//        //系统模块
//        map.put(DOMAIN + "8604/v3/api-docs/default", 4L);
//        //搜索模块
//        map.put(DOMAIN + "8605/v3/api-docs/default", 5L);
//        //通知模块
//        map.put(DOMAIN + "8606/v3/api-docs/default", 6L);
//        return map;
//    }
//
//    @PostConstruct
//    public void init() {
//        redisTemplate.delete("role:menu");
//        //删除（除12001）的全部菜单
//        menuService.deleteMenuType(12001);
//
//        taskScheduler.schedule(() -> {
//            getUrlInfoMap().forEach((k, v) -> {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                urlInfo(k, v);
//            });
//        }, new Date(System.currentTimeMillis() + 20000));
//    }
//
//    public void urlInfo(String url, Long n) {
//        getMenu(url, n);
//    }
//
//    private void getMenu(String urlString, Long n) {
//
//        long l = System.currentTimeMillis();
//
//        List<String> list = Arrays.asList("get", "put", "post", "delete");
//        List<Menu> menus = new ArrayList<>();
//        TreeSet<String> tagSet = new TreeSet<>();
//        try {
//
//            // 发送 HTTP GET 请求
//            URL url = new URL(urlString);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Content-Type", "application/json");
//
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                // 读取响应内容
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String inputLine;
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                // 解析 JSON
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode rootNode = objectMapper.readTree(response.toString());
//                JsonNode pathsNode = rootNode.path("paths");
//
//                // 遍历路径
//                Iterator<String> fieldNames = pathsNode.fieldNames();
//
//                while (fieldNames.hasNext()) {
//                    String path = fieldNames.next();
//                    JsonNode pathNode = pathsNode.get(path);
//
//                    // 提前计算路径相关的字符串
//                    String processedPath = path;
//                    if (path.contains("{")) {
//                        processedPath = new StringBuilder().append("/*/*").append(path.substring(0, path.indexOf("{"))).append("**").toString();
//                    } else {
//                        processedPath = new StringBuilder().append("/*/*").append(path).toString();
//                    }
//                    String perms = processedPath.substring(1).replace("/", ":").substring(4);
//
//                    for (String method : list) {
//                        if (pathNode.has(method)) {
//                            JsonNode putNode = pathNode.get(method);
//                            JsonNode tagsNode = putNode.get("tags");
//                            JsonNode summaryNode = putNode.get("summary");
//
//                            if (tagsNode != null && summaryNode != null) {
//                                String tag = tagsNode.toString().replaceAll("[\\[\\]\"]", "");
//                                String summary = summaryNode.asText();
//                                tagSet.add(tag);
//                                menus.add(new Menu(summary, 12003, processedPath, perms, summary + "按钮", tag, LocalDateTime.now()));
//                            }
//                        }
//                    }
//                }
//            } else {
//                System.out.println("检索数据失败。处理步骤HTTP错误码: " + responseCode);
//            }
//            connection.disconnect();
//
//            CompletableFuture.runAsync(() -> {
//                try {
//                    tagSet.stream().forEach(tag -> {
//                        Menu menu = new Menu();
//                        menu.setName(tag);
//                        menu.setMenuType(12002);
//                        menu.setParentId(n);
//                        menu.setCreateTime(LocalDateTime.now());
//                        menu.setRemark(tag + "菜单");
//                        MenuAdd menuAdd = new MenuAdd();
//                        BeanUtils.copyProperties(menu, menuAdd);
//                        menuService.addMenu(menuAdd);
//
//                    });
//                } catch (Exception e) {
//                    log.error("插入菜单失败", e.getMessage());
//                }
//            });
//            Thread.sleep(2000);
//            //异步操作插入-300ms上下
//            CompletableFuture.runAsync(() -> {
//                try {
//                    menus.stream().forEach(menu -> {
//                        Long id = menuService.getMenuByName(menu.getTag()).getId();
//                        MenuAdd menuAdd = new MenuAdd();
//                        BeanUtils.copyProperties(menu, menuAdd);
//                        if (id != null) {
//                            menuAdd.setParentId(id);
//                        }
//                        menuService.addMenu(menuAdd);
//                    });
//                } catch (Exception e) {
//                    log.error("插入菜单失败", e.getMessage());
//                }
//            });
//
//            //同步插入--900ms上下  提升600ms
////            menus.stream().forEach(menu -> {
////                MenuAdd menuAdd = new MenuAdd();
////                BeanUtils.copyProperties(menu, menuAdd);
////                userClient.addMenu(menuAdd);
////            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//
//            System.out.println("********************************" + (System.currentTimeMillis() - l));
//        }
//    }
//
//    /**
//     * 获取管理类的所有接口API
//     */
//
////    @Autowired
////    private RequestMappingHandlerMapping mappingHandlerMapping;
////    /**
////     * 在Bean初始化完成后执行的方法
////     * 该方法用于收集并打印所有请求映射信息，包括URL、处理方法和类名
////     */
////    @PostConstruct
////    public void urlInfo() {
////        // 记录日志，开始收集请求信息
////        log.info("***********************************************************");
////        // 创建一个列表，用于存储所有请求映射信息的字典
////        List<Map<String, String>> map = new ArrayList<>();
////
////        // 遍历所有请求映射信息和对应的处理方法
////        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : mappingHandlerMapping.getHandlerMethods().entrySet()) {
////            // 创建一个字典，用于存储单个请求映射的信息
////            Map<String, String> m = new HashMap<>();
////            // 获取请求映射信息
////            RequestMappingInfo requestMappingInfo = entry.getKey();
////            // 获取所有URL模式
////            Set<PathPattern> patterns = requestMappingInfo.getPathPatternsCondition().getPatterns();
////            // 遍历所有URL模式并存储到字典中
////            for (PathPattern pattern : patterns) {
////                m.put("url", pattern.getPatternString());
////            }
////            // 获取处理方法信息
////            HandlerMethod handlerMethod = entry.getValue();
////            // 存储方法名到字典中
////            m.put("method", handlerMethod.getMethod().getName());
////            // 存储类名到字典中
////            m.put("class", handlerMethod.getMethod().getDeclaringClass().getName());
////            // 获取请求方法条件
////            RequestMethodsRequestCondition methodsCondition = requestMappingInfo.getMethodsCondition();
////            // 遍历所有请求方法并存储到字典中
////            for (RequestMethod method : methodsCondition.getMethods()) {
////                m.put("type", method.toString());
////            }
////            // 将包含请求映射信息的字典添加到列表中
////            if (m.get("class").contains("com.linyilinyi")){
////                //排序
////                map.add(m);
////                Collections.sort(map, Comparator.comparing(o -> o.get("class")));
////            }
////        }
////        // 打印所有请求映射信息的JSON格式字符串
////        System.out.println(JSON.toJSONString(map));
////    }
//}
