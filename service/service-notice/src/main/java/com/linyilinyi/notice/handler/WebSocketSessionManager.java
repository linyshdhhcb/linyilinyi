package com.linyilinyi.notice.handler;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketSessionManager {
    // 存储用户ID和WebSocket会话之间的映射
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // 添加用户会话
    public static void addSession(String userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    // 移除用户会话
    public static void removeSession(String userId) {
        sessions.remove(userId);
    }

    // 获取用户会话
    public static WebSocketSession getSession(String userId) {
        return sessions.get(userId);
    }

    // 获取所有会话
    public static ConcurrentHashMap<String, WebSocketSession> getAllSessions() {
        return sessions;
    }
}