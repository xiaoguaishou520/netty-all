package com.cw.websocket;

import com.cw.netty.Person;
import io.netty.util.internal.ConcurrentSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author 小怪兽
 * @Date 2021-03-26
 */
@ServerEndpoint("/ws")
@Component
@Slf4j
public class WebSocketServer {

    /**
     * 在线人数
     */
    private static AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * 用来保存每个客户端的WebSocketServer实例
     */
    private static ConcurrentSet<WebSocketServer> webSocketServers = new ConcurrentSet<>();

    /**
     * 每个客户端对应的连接会话，通过它主动给客户端发消息
     */
    private Session session;

    /**
     * 建立连接成功，触发方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketServers.add(this);
        atomicInteger.incrementAndGet();
        log.info("新用户上线，当前在线人数为：{}",atomicInteger.get());
    }

    /**
     * 关闭连接，触发方法
     */
    @OnClose
    public void onClose() {
        log.info("用户{}下线，当前在线人数：{}",this.session.getId(),atomicInteger.get());
        webSocketServers.remove(this);
        atomicInteger.decrementAndGet();
    }

    /**
     * 收到消息触发
     * @param message
     * @param session
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        log.info("收到客户端{}的消息：{}",session.getId(),message);
        for (WebSocketServer webSocketServer : webSocketServers) {
            webSocketServer.session.getBasicRemote().sendText(session.getId() + "说：" + message);
        }
    }

    /**
     * 广播功能
     * @param msg
     * @throws IOException
     */
    public static void broadcast(String msg) throws IOException {
        for (WebSocketServer webSocketServer : webSocketServers) {
            webSocketServer.session.getBasicRemote().sendText(msg);
        }
    }
}
