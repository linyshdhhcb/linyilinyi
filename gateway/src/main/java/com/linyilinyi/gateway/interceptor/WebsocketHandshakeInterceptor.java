package com.linyilinyi.gateway.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @Description 需要登录才允许连接WebSocket
 * @Author linyi
 * @Date 2024/11/5
 * @ClassName: WebsocketHandshakeInterceptor
 */
@Slf4j
@Component
public class WebsocketHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
       //判断用户是否登录
        boolean login = StpUtil.isLogin();
        if (login){
            log.info("用户登录，建立websocket连接");
            return true;
        }else {
            log.info("用户未登录，不能建立websocket连接");
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
