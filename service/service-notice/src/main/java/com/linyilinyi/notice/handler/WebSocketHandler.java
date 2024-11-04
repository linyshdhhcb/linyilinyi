package com.linyilinyi.notice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/4
 * @ClassName: WebSocketHandler
 */
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    // 正则表达式，用于从 URL 中提取 userId
    private static final Pattern USER_ID_PATTERN = Pattern.compile("/websocket/(.*)");

    // 用于存储 userId 和对应的 WebSocketSession 的映射
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 从 URL 中提取 userId
        String uri = session.getUri().toString();
        Matcher matcher = USER_ID_PATTERN.matcher(uri);
        if (matcher.find()) {
            String userId = matcher.group(1);
            sessions.put(userId, session); // 将会话存储到映射中
            log.info("用户{}的连接建立",userId);
        }
    }

    /**
     * 处理接收到的文本消息
     * 当通过WebSocket接收到文本消息时，此方法将被调用
     * 它提取发送者的userId，并记录收到的消息
     *
     * @param session 代表与客户端的WebSocket会话
     * @param message 包含接收到的文本消息
     * @throws Exception 如果处理消息时发生错误
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 从 URL 中提取 userId，并从映射中移除会话
        String uri = session.getUri().toString();
        Matcher matcher = USER_ID_PATTERN.matcher(uri);
        if (matcher.find()) {
            String userId = matcher.group(1);
            // 处理收到的消息（如有需要）
            log.info("用户{}发送信息：{}",userId, message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 从 URL 中提取 userId，并从映射中移除会话
        String uri = session.getUri().toString();
        Matcher matcher = USER_ID_PATTERN.matcher(uri);
        if (matcher.find()) {
            String userId = matcher.group(1);
            sessions.remove(userId);
            log.info("用户 {} 的连接已关闭", userId);
        }
    }

    // 方法：单点发送消息
    public void sendMessageToUser(String userId, String message) throws Exception {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }

    // 方法：群发消息
    public void sendMessageToAll(String message) throws Exception {
        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }

    // 方法：向多个用户发送消息
    public void sendMessageToUsers(List<String> userIds, String message) throws Exception {
        for (String userId : userIds) {
            WebSocketSession session = sessions.get(userId);
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }
}
