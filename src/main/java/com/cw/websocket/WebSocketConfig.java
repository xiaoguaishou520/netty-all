package com.cw.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * @Author 小怪兽
 * @Date 2021-03-26
 */
@Configuration
public class WebSocketConfig {

    @Autowired
    private WebSocketServer webSocketServer;

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    public static void main(String[] args) throws IOException, EncodeException {

    }
}
