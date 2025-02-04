package com.linyilinyi.common.utils;

import com.linyilinyi.common.exception.LinyiException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * 获取ip地址
 */
@Slf4j
public class IpUtil {

    private static final String DB_PATH = "ipdb/ip2region.xdb";

    /**
     * 获取用户的真实IP地址，避免通过代理或负载均衡影响IP获取
     *
     * @param request HttpServletRequest对象，用于获取请求头信息
     * @return 用户的真实IP地址，如果无法获取，则返回空字符串
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            log.info("HttpServletRequest对象为空，无法获取用户真实IP地址");
            return "为空";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : getMultistageReverseProxyIp(ip);

    }

    /**
     * 从多级反向代理中获得第一个非unknown IP地址
     *
     * @param ip 获得的IP地址
     * @return 第一个非unknown IP地址
     */
    public static String getMultistageReverseProxyIp(String ip) {
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                if (false == isUnknown(subIp)) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 检测给定字符串是否为未知，多用于检测HTTP请求相关
     *
     * @param checkString 被检测的字符串
     * @return 是否未知
     */
    public static boolean isUnknown(String checkString) {
        return StringUtils.isBlank(checkString) || "unknown".equalsIgnoreCase(checkString);
    }

    /**
     * 获取ip的地理位置
     * 该方法通过查询ip地址库来获取ip对应的地理位置信息
     *
     * @param ip 需要查询地理位置的ip地址
     * @return 返回ip地址对应的地理位置信息，如果查询失败则返回"未知地址"
     */
//    public static String getIpLocation(String ip) {
//        if (ip == null || ip.trim().isEmpty()) {
//            log.info("IP地址为空，无法查询地理位置");
//            return "未知地址";
//        }
//
//        //String dbip = "ipdb/ip2region.xdb";
//        String dbip = "C:/Intellij_IDEA/a/linyilinyi/common/src/main/resources/ipdb/ip2region.xdb";
//        Searcher searcher = null;
//        //缓存整个 xdb 数据
//        // 1、从 dbPath 加载整个 xdb 到内存。
//        byte[] cBuff;
//        try {
//            // 从文件中加载整个 xdb 到内存。classpath
//            cBuff = Searcher.loadContentFromFile(dbip);
//
//        } catch (Exception e) {
//            System.out.printf("加载内容失败 `%s`: %s\n", dbip, e);
//            return e.getMessage();
//        }
//
//        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
//        try {
//            searcher = Searcher.newWithBuffer(cBuff);
//        } catch (Exception e) {
//            log.error("创建内容缓存搜索器失败:{}", e.getMessage());
//            return e.getMessage();
//        }
//        String region=null;
//        // 3、查询
//        try {
//            long sTime = System.nanoTime();
//             region = searcher.search(ip);
//            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
//            log.info("{region: {}, ioCount: {}, took: {} μs}", region, searcher.getIOCount(), cost);
//        } catch (Exception e) {
//            log.error("ip:{}-->搜索失败:{}", ip,e.getMessage());
//        }
//
//        // 4、关闭资源 - 该 searcher 对象可以安全用于并发，等整个服务关闭的时候再关闭 searcher
//        // searcher.close();
//
//        // 备注：并发使用，用整个 xdb 数据缓存创建的查询对象可以安全的用于并发，也就是你可以把这个 searcher 对象做成全局对象去跨线程访问。
//        return region;
//    }
    public static String getIpLocation(String ip) {
        if (ip == null || ip.trim().isEmpty()) {
            log.info("IP地址为空，无法查询地理位置");
            return "未知地址";
        }

        String dbPath = "ipdb/ip2region.xdb";
        Searcher searcher = null;
        byte[] cBuff;
        try {
            // 从类路径中加载整个 xdb 到内存
            Resource resource = new ClassPathResource(dbPath);
            InputStream inputStream = resource.getInputStream();
            cBuff = toByteArray(inputStream);
            inputStream.close();
        } catch (IOException e) {
            log.error("加载内容失败: {}", e.getMessage());
            return "未知地址";
        }

        // 使用上述的 cBuff 创建一个完全基于内存的查询对象
        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            log.error("创建内容缓存搜索器失败: {}", e.getMessage());
            return "未知地址";
        }

        String region = null;
        // 查询
        try {
            long sTime = System.nanoTime();
            region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - sTime);
            log.info("{region: {}, ioCount: {}, took: {} μs}", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            log.error("ip:{}-->搜索失败:{}", ip, e.getMessage());
        }

        // 备注：并发使用，用整个 xdb 数据缓存创建的查询对象可以安全的用于并发，也就是你可以把这个 searcher 对象做成全局对象去跨线程访问。
        return region;
    }

    /**
     * 将 InputStream 转换为 byte 数组
     *
     * @param input 输入流
     * @return 字节数组
     * @throws IOException 如果读取过程中发生错误
     */
    private static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }
    //地址显示
    public static String address(String ip) {
        if (StringUtils.isBlank(ip)) {
            return "未知地址";
        }
        String[] split = null;
        try {
            split = getIpLocation(ip).split("\\|");
        } catch (Exception e) {
            log.error("获取ip地址失败", e);
            throw new LinyiException("获取ip地址失败" + e.getMessage());
        }
        if (split[0] == "中国") {
            return split[2] + "-" + split[3];
        }else if (ip.contains("127.0.0.1") || ip.contains("192.168") || ip.contains("172.17") ){
            return "广东省-广州市";
        } else {
            return split[2] != "0" ? split[0] + "-" + split[2] : split[0];
        }
    }

}