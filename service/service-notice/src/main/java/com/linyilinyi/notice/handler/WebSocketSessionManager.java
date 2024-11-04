package com.linyilinyi.notice.handler;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketSessionManager {
    // 存储用户ID和WebSocket会话之间的映射
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // 添加用户会话
    public static void addSession(String username, WebSocketSession session) {
        sessions.put(username, session);
    }

    // 移除用户会话
    public static void removeSession(String username) {
        sessions.remove(username);
    }

    // 获取用户会话
    public static WebSocketSession getSession(String username) {
        return sessions.get(username);
    }

    // 获取所有会话
    public static ConcurrentHashMap<String, WebSocketSession> getAllSessions() {
        return sessions;
    }
}